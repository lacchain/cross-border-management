pragma solidity ^0.5.0;

import "@openzeppelin/contracts/access/roles/MinterRole.sol";
import "@openzeppelin/contracts/access/roles/WhitelistedRole.sol";
import "../permissions/HolderOperatorRole.sol";
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "./IHoldable.sol";
import "../libraries/StringUtil.sol";

contract EmoneyToken is ERC20, IHoldable, MinterRole, HolderOperatorRole, WhitelistedRole{
    
    using StringUtil for string;
    
    mapping(bytes32 => Hold) internal holds;
    mapping(address => uint256) internal heldBalance;
    uint256 internal _totalHeldBalance;

    string private _name;
    string private _symbol;
    uint8 private _decimals;

    constructor (string memory name, string memory symbol) public {
        _name = name;
        _symbol = symbol;
        _decimals = 4;
    }
    
    /**
     * @dev Returns the name of the token.
     */
    function name() public view returns (string memory) {
        return _name;
    }

    /**
     * @dev Returns the symbol of the token, usually a shorter version of the
     * name.
     */
    function symbol() public view returns (string memory) {
        return _symbol;
    }

    /**
     * @dev Returns the number of decimals used to get its user representation.
     * For example, if `decimals` equals `2`, a balance of `505` tokens should
     * be displayed to a user as `5,05` (`505 / 10 ** 2`).
     *
     * Tokens usually opt for a value of 18, imitating the relationship between
     * Ether and Wei. This is the value {ERC20} uses, unless {_setupDecimals} is
     * called.
     *
     * NOTE: This information is only used for _display_ purposes: it in
     * no way affects any of the arithmetic of the contract, including
     * {IERC20-balanceOf} and {IERC20-transfer}.
     */
    function decimals() public view returns (uint8) {
        return _decimals;
    }

    function _setupDecimals(uint8 decimals_) internal {
        _decimals = decimals_;
    }

    function mint(address account, uint256 amount) public onlyMinter returns (bool) {
        require(super.isWhitelisted(account), "Account isn't whitelisted");
        super._mint(account, amount);
        return true;
    }
    
    function burn(address account, uint256 amount) public onlyMinter returns (bool) {
        require(balanceOf(account) >= amount, "Not enough available balance");
        super._burn(account, amount);
        return true;
    }
    
    function hold(
        string memory operationId,
        address from,
        address to,
        address notary,
        uint256 value
    ) public onlyHolder returns (bool)
    {
        _checkHold(to);

        return _hold(
            operationId,
            msg.sender,
            from,
            to,
            notary,
            value
        );
    }
    
    function releaseHold(string memory operationId) onlyHolder public returns (bool) {
        Hold storage releasableHold = holds[operationId.toHash()];

        return _releaseHold(releasableHold, operationId);
    }
    
    function executeHold(string memory operationId, uint256 value) onlyHolder public returns (bool) {
        return _executeHold(
            operationId,
            value
        );
    }
    
    /// @notice Retrieve the erc20.balanceOf(account) - heldBalance(account)
    function balanceOf(address account) public view returns (uint256) {
        return super.balanceOf(account).sub(heldBalance[account]);
    }
    
    function transfer(address _to, uint256 _value) onlyMinter public returns (bool) {
        require(balanceOf(msg.sender) >= _value, "Not enough available balance");
        return super.transfer(_to, _value);
    }
    
    function transferFrom(address _from, address _to, uint256 _value) onlyMinter public returns (bool) {
        require(balanceOf(_from) >= _value, "Not enough available balance");
        super._transfer(_from, _to, _value);
        return true;
    }
    
    function balanceOnHold(address account) public view returns (uint256) {
        return heldBalance[account];
    }

    function netBalanceOf(address account) public view returns (uint256) {
        return super.balanceOf(account);
    }

    function totalSupplyOnHold() public view returns (uint256) {
        return _totalHeldBalance;
    }
    
    function _hold(
        string memory operationId,
        address issuer,
        address from,
        address to,
        address exchange,
        uint256 value
    ) internal returns (bool)
    {
        Hold storage newHold = holds[operationId.toHash()];

        require(!operationId.isEmpty(), "Operation ID must not be empty");
        require(value > 0, "Value must be greater than zero");
        require(newHold.value == 0, "This operationId already exists");
        require(exchange != address(0), "Notary address must not be zero address");
        require(value <= balanceOf(from), "Amount of the hold can't be greater than the balance of the origin");

        newHold.issuer = issuer;
        newHold.origin = from;
        newHold.target = to;
        newHold.exchange = exchange;
        newHold.value = value;
        newHold.status = HoldStatusCode.Ordered;

        heldBalance[from] = heldBalance[from].add(value);
        _totalHeldBalance = _totalHeldBalance.add(value);

        emit HoldCreated(
            issuer,
            operationId,
            from,
            to,
            exchange,
            value
        );

        return true;
    }
    
    function _releaseHold(Hold storage releasableHold, string memory operationId) onlyHolder internal returns (bool) {
        require(
            releasableHold.status == HoldStatusCode.Ordered,
            "A hold can only be released in status Ordered"
        );
        require(
            (msg.sender == releasableHold.target),
            "A not expired hold can only be released by the notary or the payee"
        );

        if (releasableHold.exchange == msg.sender) {
            releasableHold.status = HoldStatusCode.ReleasedByNotary;
        } else {
            releasableHold.status = HoldStatusCode.ReleasedByPayee;
        }

        heldBalance[releasableHold.origin] = heldBalance[releasableHold.origin].sub(releasableHold.value);
        _totalHeldBalance = _totalHeldBalance.sub(releasableHold.value);

        emit HoldReleased(releasableHold.issuer, operationId, releasableHold.status);

        return true;
    }
    
    function _executeHold(
        string memory operationId,
        uint256 value
    ) internal returns (bool)
    {
        Hold storage executableHold = holds[operationId.toHash()];

        require(
            executableHold.status == HoldStatusCode.Ordered,
            "A hold can only be executed in status Ordered"
        );
        require(value > 0, "Value must be greater than zero");
        require(value <= executableHold.value, "The value should be equal or less than the held amount");

        heldBalance[executableHold.origin] = heldBalance[executableHold.origin].sub(executableHold.value);
        _totalHeldBalance = _totalHeldBalance.sub(executableHold.value);
        super._transfer(executableHold.origin, executableHold.exchange ,executableHold.value);
        executableHold.status=HoldStatusCode.Executed;

        return true;
    }
    
    function _checkHold(address to) private pure {
        require(to != address(0), "Payee address must not be zero address");
    }
    
}