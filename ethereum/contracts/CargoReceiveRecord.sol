pragma solidity ^0.4.0;

contract CargoReceiveRecord {

    struct Record {
        uint id;    // id由两部分拼接组成，前4位为企业code，后面为企业数据库中的记录id
        string content;
        int consignor;  // 寄货人
        uint contractId;    // 绑定合同
        uint insuranceId;   // 绑定保险
        uint expressId; // 绑定物流
        uint time;
    }

    mapping(uint => Record) records;

    event createRecordEvent(int code, string msg);
    event getRecordCallback(string content, int consignor, uint contractId, uint insuranceId, uint expressId, uint time);

    function createRecord(uint id, string content, int consignor, uint contractId, uint insuranceId, uint expressId) public {
        if (records[id].time != 0) {
            emit createRecordEvent(- 1, "入库记录已存在");
            return;
        }
        records[id] = Record(id, content, consignor, contractId, insuranceId, expressId, now);
        emit createRecordEvent(0, "创建入库记录成功");
    }

    function getRecord(uint id) public {
        emit getRecordCallback(records[id].content, records[id].consignor, records[id].contractId, records[id].insuranceId, records[id].expressId, records[id].time);
    }

}
