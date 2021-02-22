pragma solidity ^0.5.0;

library StringUtil {
    function toHash(string memory _s) internal pure returns (bytes32) {
        return keccak256(abi.encode(_s));
    }

    function isEmpty(string memory _s) internal pure returns (bool) {
        return bytes(_s).length == 0;
    }
}