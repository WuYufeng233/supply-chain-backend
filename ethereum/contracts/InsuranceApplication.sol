pragma solidity ^0.4.0;

contract InsuranceApplication {

    struct Application {
        int id;
        string content;
        uint sponsor;
        uint receiver;
        string sponsorSignature;
        string receiverSignature;
        int applicationType; // 5 - 运输保险, 6 - 货物保险, 7 - 预留
        uint startTime;
        string status;
    }

    mapping(int => Application) applications;

    event createApplicationEvent(int code, string msg);
    event receiveApplicationEvent(int code, string msg);
    event updateApplicationStatusEvent(int code, string msg);
    event getApplicationCallback(string content, uint sponsor, uint receiver, string sponsorSignature, string receiverSignature, int applicationType, uint startTime, string status);
    event getApplicationStatusCallback(string receiverSignature, string status);

    function createApplication(int id, string content, uint sponsor, uint receiver, string sponsorSignature, int applicationType) public {
        if (applications[id].startTime != 0) {
            emit createApplicationEvent(- 1, "申请已存在");
            return;
        }
        applications[id] = Application(id, content, sponsor, receiver, sponsorSignature, "", applicationType, now, "已发起申请");
        emit createApplicationEvent(0, "申请成功");
    }

    function receiveApplication(int id, string receiverSignature) public {
        if (applications[id].startTime == 0) {
            emit receiveApplicationEvent(- 1, "申请不存在");
            return;
        }
        if (keccak256(abi.encodePacked(applications[id].receiverSignature)) != keccak256(abi.encodePacked(""))) {
            emit receiveApplicationEvent(- 2, "申请已签名");
            return;
        }
        applications[id].receiverSignature = receiverSignature;
        applications[id].status = "已处理";
        emit receiveApplicationEvent(0, "申请处理完成");
    }

    function updateApplicationStatus(int id, string status) public {
        if (applications[id].startTime == 0) {
            emit updateApplicationStatusEvent(- 1, "申请不存在");
            return;
        }
        applications[id].status = status;
        emit updateApplicationStatusEvent(0, "更新状态成功");
    }

    function getApplication(int id) public {
        Application application = applications[id];
        emit getApplicationCallback(application.content, application.sponsor, application.receiver, application.sponsorSignature, application.receiverSignature, application.applicationType, application.startTime, application.status);
    }

    function getApplicationStatus(int id) public {
        emit getApplicationStatusCallback(applications[id].receiverSignature, applications[id].status);
    }

}
