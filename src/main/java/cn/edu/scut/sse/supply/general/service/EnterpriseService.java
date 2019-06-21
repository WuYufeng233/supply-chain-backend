package cn.edu.scut.sse.supply.general.service;

import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class EnterpriseService {

    private EnterpriseDAO enterpriseDAO;

    @Autowired
    public EnterpriseService(EnterpriseDAO enterpriseDAO) {
        this.enterpriseDAO = enterpriseDAO;
    }

    public ResponseResult listEnterprise() {
        List<Enterprise> enterprises = enterpriseDAO.listEnterprise();
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setMsg("获取企业列表成功");
        result.setData(enterprises);
        return result;
    }

    public ResponseResult createTokenAccount(int code, String name) {
        try {
            return enterpriseDAO.createTokenAccountToFisco(code, name);
        } catch (Exception e) {
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    public ResponseResult updateTokenAccount(int code, String name) {
        try {
            return enterpriseDAO.updateTokenAccountToFisco(code, name);
        } catch (Exception e) {
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

}
