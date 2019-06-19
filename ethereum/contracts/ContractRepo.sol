pragma solidity ^0.4.0;

contract ContractRepo {

    struct Contract {
        int id;
        string hash;
        uint sponsor;
        uint receiver;
        string sponsorSignature;
        string receiverSignature;
        uint startTime;
        int status;
    }

    mapping(int => Contract) contracts;

    event launchContractEvent(int code, string msg);
    event receiveContractEvent(int code, string msg);
    event getContractCallback(string hash, uint sponsor, uint receiver, string sponsorSignature, string receiverSignature, uint startTime, int status);

    function launchContract(int id, string hash, uint sponsor, uint receiver, string sponsorSignature) public {
        if (contracts[id].startTime != 0) {
            emit launchContractEvent(- 1, "合同已存在");
            return;
        }
        contracts[id] = Contract(id, hash, sponsor, receiver, sponsorSignature, "", now, 2);
        emit launchContractEvent(0, "合同发起完成");
    }

    function receiveContract(int id, string receiverSignature) public {
        if (contracts[id].startTime == 0) {
            emit receiveContractEvent(- 1, "合同不存在");
            return;
        }
        if (keccak256(abi.encodePacked(contracts[id].receiverSignature)) != keccak256(abi.encodePacked(""))) {
            emit receiveContractEvent(- 2, "合同已签名");
            return;
        }
        contracts[id].receiverSignature = receiverSignature;
        contracts[id].status = 1;
        emit receiveContractEvent(0, "合同接受完成");
    }

    function getContract(int id) public view {
        emit getContractCallback(contracts[id].hash,
            contracts[id].sponsor, contracts[id].receiver,
            contracts[id].sponsorSignature, contracts[id].receiverSignature,
            contracts[id].startTime, contracts[id].status);
    }

}
