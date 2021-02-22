pragma solidity ^0.5.0;

import "./eMoneyToken/EmoneyToken.sol";
import "./ICrossBorderPayment.sol";
import "./libraries/StringUtil.sol";
import "./permissions/TransferAgentRole.sol";
import "@openzeppelin/contracts/math/SafeMath.sol";

contract CrossBorderPayment is ICrossBorderPayment, TransferAgentRole{
    
    using StringUtil for string;
    using SafeMath for uint256;
    
    EmoneyToken internal eDollars;
    EmoneyToken internal ePesos;

    constructor(address _feeAgent, string memory name, string memory symbol) public {
        eDollars = new EmoneyToken(name, symbol);
        ePesos = new EmoneyToken(name, symbol);
        eDollars.addMinter(msg.sender);
        eDollars.addWhitelistAdmin(msg.sender);
        ePesos.addMinter(msg.sender);
        ePesos.addWhitelistAdmin(msg.sender);
        
        feeAgent = _feeAgent;
        emit CrossBorderCreated(address(eDollars),address(ePesos));
    }

    mapping(bytes32 => Transfer) private orderedTransfers;
    address public transferAgent;
    address public feeAgent;

    function orderTransfer(string calldata operationId, address to, address operatorExchange, uint256 value, uint rate) external returns (bool) {
        _checkTransfer(to);
        
        return _orderTransfer(
            msg.sender,
            operationId,
            to,
            operatorExchange,
            value,
            rate
        );
    }
    
    function setFeeRate(string calldata operationId, uint256 fee, uint256 rate) external returns (bool){
        require(msg.sender == feeAgent, "Set fee can only be executed by the fee agent");
        return _setFeeRate(operationId, fee, rate);
    }

    function cancelTransfer(string calldata operationId) external returns (bool) {
        bytes32 operationIdHash = operationId.toHash();

        Transfer storage cancelableTransfer = orderedTransfers[operationIdHash];

        require(cancelableTransfer.status == TransferStatusCode.Ordered, "A transfer can only be cancelled in status Ordered");
        require(
            msg.sender == cancelableTransfer.issuer || msg.sender == cancelableTransfer.origin,
            "A transfer can only be cancelled by the orderer or the walletToBePaidOut"
        );

        eDollars.releaseHold(operationId);

        cancelableTransfer.status = TransferStatusCode.Cancelled;

        emit TransferCancelled(
            cancelableTransfer.issuer,
            operationId
        );

        return true;
    }

    function approveTransfer(string calldata operationId) onlyTransferAgent external returns (bool) {
        return _approveTransfer(operationId);
    }
    
    function sendDollarsToExchange(string calldata operationId) onlyTransferAgent external returns (bool){
        return _sendDollarsToExchange(operationId);
    }
    
    function changeDollarsToPesos(string calldata operationId) onlyTransferAgent external returns (bool){
        return _changeDollarsToPesos(operationId);
    }
    
    function sendPesosToRecepient(string calldata operationId) onlyTransferAgent external returns (bool){
        return _sendPesosToRecepient(operationId);
    }

    function retrieveTransferData(string calldata operationId) external view returns (
        address origin,
        address to,
        address operatorExchange,
        uint256 value,
        uint256 fee,
        uint256 rate,
        uint256 estimatedRate,
        uint256 valueExchanged,
        TransferStatusCode status
    )
    {
        bytes32 operationIdHash = operationId.toHash();

        Transfer storage retrievedTransfer = orderedTransfers[operationIdHash];

        return (
            retrievedTransfer.origin,
            retrievedTransfer.target,
            retrievedTransfer.operatorExchange,
            retrievedTransfer.value,
            retrievedTransfer.fee,
            retrievedTransfer.rate,
            retrievedTransfer.estimatedRate,
            retrievedTransfer.valueExchanged,
            retrievedTransfer.status
        );
    }

    function _orderTransfer(
        address orderer,
        string memory operationId,
        address recipient,
        address operatorExchange,
        uint256 value,
        uint256 rate
    ) internal returns (bool)
    {
        Transfer storage newTransfer = orderedTransfers[operationId.toHash()];
        require(newTransfer.value == 0, "This operationId already exists");
        require(operatorExchange != address(0), "Operator address must not be zero address");
        require(value > 0, "Value must be greater than zero");

        require(eDollars.balanceOf(orderer)>value,"Account doesn't have founds to transfer");
        require(ePesos.isWhitelisted(recipient), "Destination Account isn't whitelisted");
        
        newTransfer.issuer = msg.sender;
        newTransfer.origin = orderer;
        newTransfer.target = recipient;
        newTransfer.operatorExchange = operatorExchange;
        newTransfer.value = value;
        newTransfer.estimatedRate = rate;
        newTransfer.status = TransferStatusCode.Ordered;
        
        require(eDollars.hold(operationId,newTransfer.origin,newTransfer.target,newTransfer.operatorExchange,newTransfer.value),"Error holding tokens");
    
        newTransfer.status = TransferStatusCode.OnHold;
        
        emit TransferOrdered(
            msg.sender,
            operationId,
            recipient,
            operatorExchange,
            value,
            rate
        );
    
        return true;
    }
    
    function _approveTransfer(string memory operationId) internal returns(bool) {
        bytes32 operationIdHash = operationId.toHash();

        Transfer storage transferOnHold = orderedTransfers[operationIdHash];
        require(transferOnHold.value != 0, "This operationId doesn't exist");
        require(transferOnHold.status == TransferStatusCode.OnHold, "A transfer can only be approved from status OnHold");
        transferOnHold.status = TransferStatusCode.Approved;
        
        emit TransferApproved(
            transferOnHold.issuer,
            operationId,
            transferOnHold.value
        );
        
        return true;
    }
    
    function _sendDollarsToExchange(string memory operationId) internal returns(bool){
        Transfer storage orderedTransfer = orderedTransfers[operationId.toHash()];
        require(orderedTransfer.value != 0, "This operationId doesn't exist");
        require(orderedTransfer.status == TransferStatusCode.FeeSet, "A transfer can only be executed from status FeeSet");
        eDollars.executeHold(operationId,orderedTransfer.value);
        orderedTransfer.status = TransferStatusCode.SendedToMarket;
        emit SendedToMarket(msg.sender, operationId);
        return true;
    }
    
    function _changeDollarsToPesos(string memory operationId) internal returns(bool){
        Transfer storage sendedMarketTransfer = orderedTransfers[operationId.toHash()];
        require(sendedMarketTransfer.value != 0, "This operationId doesn't exist");
        require(sendedMarketTransfer.status == TransferStatusCode.SendedToMarket, "A transfer can only be executed from status SendedToMarket");
    //    eDollars.burn(sendedMarketTransfer.operatorExchange,sendedMarketTransfer.value.sub(sendedMarketTransfer.fee));
        sendedMarketTransfer.valueExchanged = sendedMarketTransfer.value.sub(sendedMarketTransfer.fee).div(sendedMarketTransfer.rate).mul(10**uint256(ePesos.decimals()));
        ePesos.mint(sendedMarketTransfer.operatorExchange, sendedMarketTransfer.valueExchanged);
        sendedMarketTransfer.status = TransferStatusCode.Exchanged;
        emit Exchanged(msg.sender, operationId);
        return true;
    }
    
    function  _sendPesosToRecepient(string memory operationId) internal returns(bool){
        Transfer storage exchangedTransfer = orderedTransfers[operationId.toHash()];
        require(exchangedTransfer.value != 0, "This operationId doesn't exist");
        require(exchangedTransfer.status == TransferStatusCode.Exchanged, "A transfer can only be executed from status Exchanged");
    //    uint256 valueToTransfer = exchangedTransfer.value.sub(exchangedTransfer.fee);
        ePesos.transferFrom(exchangedTransfer.operatorExchange, exchangedTransfer.target, exchangedTransfer.valueExchanged);
        exchangedTransfer.status = TransferStatusCode.Executed;
        emit TransferExecuted(msg.sender, exchangedTransfer.issuer, operationId, exchangedTransfer.target, exchangedTransfer.valueExchanged);
        return true;
    }
    
    function _setFeeRate(string memory operationId, uint256 fee, uint256 rate) internal returns(bool){
        Transfer storage orderedTransfer = orderedTransfers[operationId.toHash()];
        require(orderedTransfer.value != 0, "This operationId doesn't exist");
        require(orderedTransfer.status == TransferStatusCode.Approved, "A transfer can only set fee from status Approved");
        require(fee >= 0, "Fee must be greater than zero");
        require(rate > 0, "Tasa must be greater than zero");
        orderedTransfer.fee = fee;
        orderedTransfer.rate = rate;
        orderedTransfer.status = TransferStatusCode.FeeSet;
        emit FeeRateSet(msg.sender, operationId, fee, rate);
        return true;
    }
    
    function _checkTransfer(address to) private pure {
        require(to != address(0), "Payee address must not be zero address");
    }

    function setFeeAgent(address newFeeAgent) external returns(bool){
        require(msg.sender == feeAgent, "Only the Fee Agent can give permissions");
        feeAgent = newFeeAgent;
        return true;
    }

    function getDollarAddress() external view returns(address){
        return address(eDollars);
    }

    function getPesosAddress() external view returns(address){
        return address(ePesos);
    }

    event CrossBorderCreated(address eDollars, address ePesos); 
}