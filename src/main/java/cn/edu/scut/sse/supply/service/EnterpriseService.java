package cn.edu.scut.sse.supply.service;

import cn.edu.scut.sse.supply.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.pojo.Enterprise;
import cn.edu.scut.sse.supply.pojo.vo.ResponseResult;
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

}
