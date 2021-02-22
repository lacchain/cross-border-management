pragma solidity ^0.5.0;

interface ICrossBorderPayment {
    enum TransferStatusCode {
        Nonexistent,
        Ordered,
        OnHold,
        Approved,
        FeeSet,
        SendedToMarket,
        Exchanged,
        Executed,
        Rejected,
        Cancelled
    }

    struct Transfer {
        address issuer;
        address origin;
        address target;
        address operatorExchange;
        uint256 value;
        uint256 fee;
        uint256 rate;
        uint256 estimatedRate;
        uint256 valueExchanged;
        TransferStatusCode status;
    }

    function orderTransfer(string calldata operationId, address to, address operator, uint256 value, uint256 rate) external returns (bool);
    function cancelTransfer(string calldata operationId) external returns (bool);
    function approveTransfer(string calldata operationId) external returns (bool);
    function retrieveTransferData(string calldata operationId) external view returns (
        address origin,
        address to,
        address operator,
        uint256 value,
        uint256 fee,
        uint256 rate,
        uint256 estimatedRate,
        uint256 valueExchanged,
        TransferStatusCode status
    );

    event TransferOrdered(address indexed orderer, string operationId, address indexed to, address indexed operatorExchange, uint256 value, uint256 rate);
    event FeeRateSet(address indexed operator, string operationId, uint256 fee, uint256 rate);
    event TransferOnHold(address indexed issuer, string operationId);
    event TransferApproved(address indexed issuer, string operationId, uint256 amount);
    event SendedToMarket(address indexed issuer, string operationId);
    event Exchanged(address indexed issuer, string operationId);
    event TransferExecuted(address indexed issuer, address indexed orderer, string operationId, address indexed to, uint256 value);
    event TransferCancelled(address indexed issuer, string operationId);
}