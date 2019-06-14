pragma solidity ^0.4.0;

contract EnterpriseToken {

    struct Enterprise {
        uint code;
        string name;
        uint token;
    }

    uint[] enterpriseCodeList;
    mapping(uint => Enterprise) enterpriseTokenMap;

    address public minter;

    event createEnterpriseEvent(int code, string msg);
    event addTokenEvent(int code, string msg);
    event subTokenEvent(int code, string msg);
    event payTokenEvent(int code, string msg);

    function EnterpriseToken(){
        minter = msg.sender;
    }

    function createEnterprise(uint code, string name) public {
        if (msg.sender != minter) {
            createEnterpriseEvent(- 10, "权限不足");
            return;
        }
        enterpriseTokenMap[code] = Enterprise(code, name, 0);
        enterpriseCodeList.push(code);
        createEnterpriseEvent(0, "创建成功");
    }

    function getToken(uint code) public view returns (uint){
        return enterpriseTokenMap[code].token;
    }

    function addToken(uint code, uint val) public {
        if (msg.sender != minter) {
            addTokenEvent(- 10, "权限不足");
            return;
        }
        enterpriseTokenMap[code].token += val;
        addTokenEvent(0, "Token增加成功");
    }

    function subToken(uint code, uint val) public {
        if (msg.sender != minter) {
            subTokenEvent(- 10, "权限不足");
            return;
        }
        if (enterpriseTokenMap[code].token < val) {
            subTokenEvent(- 5, "Token余额不足");
            return;
        }
        enterpriseTokenMap[code].token -= val;
        subTokenEvent(0, "Token减少成功");
    }

    function payToken(uint source, uint target, uint val) public {
        if (enterpriseTokenMap[source].token < val) {
            payTokenEvent(- 5, "Token余额不足");
            return;
        }
        for (uint i = 0; i < enterpriseCodeList.length; ++i) {
            if (enterpriseCodeList[i] == target) {
                break;
            }
            if (i == enterpriseCodeList.length - 1) {
                payTokenEvent(- 1, "不存在的目标公司");
                return;
            }
        }

        enterpriseTokenMap[source].token -= val;
        enterpriseTokenMap[target].token += val;
        payTokenEvent(0, "支付成功");
    }

}
