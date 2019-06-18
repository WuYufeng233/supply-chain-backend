pragma solidity ^0.4.0;

contract HashCalculator {

    function cal(bytes src) public pure returns (bytes32) {
        return keccak256(src);
    }

}