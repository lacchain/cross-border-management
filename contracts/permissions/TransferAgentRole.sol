pragma solidity ^0.5.0;

import "@openzeppelin/contracts/GSN/Context.sol";
import "@openzeppelin/contracts/access/Roles.sol";

contract TransferAgentRole is Context {
    using Roles for Roles.Role;

    event TransferAgentAdded(address indexed account);
    event TransferAgentRemoved(address indexed account);

    Roles.Role private _transferAgents;

    constructor () internal {
        _addTransferAgent(_msgSender());
    }

    modifier onlyTransferAgent() {
        require(isTransferAgent(_msgSender()), "TransferAgentRole: caller does not have the Transfer Agent role");
        _;
    }

    function isTransferAgent(address account) public view returns (bool) {
        return _transferAgents.has(account);
    }

    function addTransferAgent(address account) public onlyTransferAgent {
        _addTransferAgent(account);
    }

    function renounceTransferAgent() public {
        _removeTransferAgent(_msgSender());
    }

    function _addTransferAgent(address account) internal {
        _transferAgents.add(account);
        emit TransferAgentAdded(account);
    }

    function _removeTransferAgent(address account) internal {
        _transferAgents.remove(account);
        emit TransferAgentRemoved(account);
    }
}