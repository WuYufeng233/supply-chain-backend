package cn.edu.scut.sse.supply.general.controller;

import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.general.service.EnterpriseService;
import cn.edu.scut.sse.supply.util.ContractUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping(value = "/enterprise")
@Controller
public class EnterpriseController {

    private EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @RequestMapping(value = "/list")
    public @ResponseBody
    ResponseResult listEnterprise() {
        return enterpriseService.listEnterprise();
    }

    @RequestMapping(method = RequestMethod.POST, value = "token/create")
    public @ResponseBody
    ResponseResult createTokenAccount(@RequestHeader("authorization") String token, int code, String name) {
        if (!ContractUtil.ENTERPRISE_TOKEN_ADDRESS.equals(token)) {
            return new ResponseResult().setCode(-10).setMsg("权限不足");
        }
        return enterpriseService.createTokenAccount(code, name);
    }

    @RequestMapping(method = RequestMethod.POST, value = "token/update")
    public @ResponseBody
    ResponseResult updateTokenAccount(@RequestHeader("authorization") String token, int code, String name) {
        if (!ContractUtil.ENTERPRISE_TOKEN_ADDRESS.equals(token)) {
            return new ResponseResult().setCode(-10).setMsg("权限不足");
        }
        return enterpriseService.updateTokenAccount(code, name);
    }

}
