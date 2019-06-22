pragma solidity ^0.4.0;

contract EnterpriseToken {

    struct Enterprise {
        uint code;
        string name;
        uint token;
        uint credit;
    }

    uint[] enterpriseCodeList;
    mapping(uint => Enterprise) enterpriseTokenMap;

    address public minter;

    event createEnterpriseEvent(int code, string msg);
    event updateEnterpriseEvent(int code, string msg);
    event setCreditEvent(int code, string msg);
    event addTokenEvent(int code, string msg);
    event subTokenEvent(int code, string msg);
    event payTokenEvent(int code, string msg);

    function EnterpriseToken(){
        minter = msg.sender;
    }

    // 由管理员调用，初始化企业
    function createEnterprise(uint code, string name) public {
        if (msg.sender != minter) {
            emit createEnterpriseEvent(- 10, "权限不足");
            return;
        }
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == code) {
                emit createEnterpriseEvent(- 2, "企业已存在");
                return;
            }
        }
        enterpriseTokenMap[code] = Enterprise(code, name, 0, 0);
        enterpriseCodeList.push(code);
        emit createEnterpriseEvent(0, "创建成功");
    }

    // 由管理员调用，修改企业名
    function updateEnterprise(uint code, string name) public {
        if (msg.sender != minter) {
            emit updateEnterpriseEvent(- 10, "权限不足");
            return;
        }
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == code) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                emit updateEnterpriseEvent(- 3, "企业不存在");
            }
        }
        enterpriseTokenMap[code].name = name;
        emit updateEnterpriseEvent(0, "修改成功");
    }

    // 银行授信
    function setCredit(uint code, uint credit) public {
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == code) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                emit setCreditEvent(- 3, "企业不存在");
            }
        }
        enterpriseTokenMap[code].credit = credit;
        emit setCreditEvent(0, "授信成功");
    }

    // 银行调用，增加企业的token
    function addToken(uint code, uint val) public {
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == code) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                emit setCreditEvent(- 3, "企业不存在");
            }
        }
        enterpriseTokenMap[code].token += val;
        emit addTokenEvent(0, "Token增加成功");
    }

    // 银行调用，减少企业的token
    function subToken(uint code, uint val) public {
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == code) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                emit setCreditEvent(- 3, "企业不存在");
            }
        }
        if (enterpriseTokenMap[code].token < val) {
            emit subTokenEvent(- 5, "Token余额不足");
            return;
        }
        enterpriseTokenMap[code].token -= val;
        emit subTokenEvent(0, "Token减少成功");
    }

    function getToken(uint code) public view returns (uint){
        return enterpriseTokenMap[code].token;
    }

    function getCredit(uint code) public view returns (uint){
        return enterpriseTokenMap[code].credit;
    }

    function payToken(uint source, uint target, uint val) public {
        if (enterpriseTokenMap[source].token < val) {
            emit payTokenEvent(- 5, "Token余额不足");
            return;
        }
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == target) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                emit payTokenEvent(- 1, "不存在的目标公司");
                return;
            }
        }

        enterpriseTokenMap[source].token -= val;
        enterpriseTokenMap[target].token += val;
        emit payTokenEvent(0, "支付成功");
    }

}
