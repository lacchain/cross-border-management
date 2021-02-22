pragma solidity ^0.5.0;

import "@openzeppelin/contracts/GSN/Context.sol";
import "@openzeppelin/contracts/access/Roles.sol";

contract HolderOperatorRole is Context {
    using Roles for Roles.Role;

    event HolderAdded(address indexed account);
    event HolderRemoved(address indexed account);

    Roles.Role private _holders;

    constructor () internal {
        _addHolder(_msgSender());
    }

    modifier onlyHolder() {
        require(isHolder(_msgSender()), "HolderRole: caller does not have the Holder role");
        _;
    }

    function isHolder(address account) public view returns (bool) {
        return _holders.has(account);
    }

    function addHolder(address account) public onlyHolder {
        _addHolder(account);
    }

    function renounceHolder() public {
        _removeHolder(_msgSender());
    }

    function _addHolder(address account) internal {
        _holders.add(account);
        emit HolderAdded(account);
    }

    function _removeHolder(address account) internal {
        _holders.remove(account);
        emit HolderRemoved(account);
    }
}