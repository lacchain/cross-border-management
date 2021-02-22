pragma solidity ^0.5.0;

interface IHoldable {
    enum HoldStatusCode {
        Nonexistent,
        Ordered,
        Executed,
        ExecutedAndKeptOpen,
        ReleasedByNotary,
        ReleasedByPayee,
        ReleasedOnExpiration
    }
    
    struct Hold {
        address issuer;
        address origin;
        address target;
        address exchange;
        uint256 value;
        HoldStatusCode status;
    }

    function hold(
        string calldata operationId,
        address to,
        address from,
        address notary,
        uint256 value
    ) external returns (bool);
    
    function releaseHold(string calldata operationId) external returns (bool);
    function executeHold(string calldata operationId, uint256 value) external returns (bool);
    function balanceOnHold(address account) external view returns (uint256);
    function netBalanceOf(address account) external view returns (uint256);
    function totalSupplyOnHold() external view returns (uint256);

    event HoldCreated(
        address indexed holdIssuer,
        string  operationId,
        address from,
        address to,
        address indexed notary,
        uint256 value
    );
    event HoldReleased(address indexed holdIssuer, string operationId, HoldStatusCode status);
    event HoldExecuted(address indexed holdIssuer, string operationId, address indexed notary, uint256 heldValue, uint256 transferredValue);
    
}