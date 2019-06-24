package cn.edu.scut.sse.supply.general.controller;

import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.general.service.EnterpriseService;
import cn.edu.scut.sse.supply.util.ContractUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(EnterpriseController.class);
    private EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    /**
     * 获取企业列表
     * <p>
     * 包括企业代码、企业名称、企业API地址
     *
     * @return 返回企业列表
     */
    @RequestMapping(value = "/list")
    public @ResponseBody
    ResponseResult listEnterprise() {
        return enterpriseService.listEnterprise();
    }

    /**
     * 管理员使用，初始化企业Token账户
     * <p>
     * 首次部署时使用，对数据库已有的企业进行Token账号初始化
     *
     * @param token 管理员Token凭证，具体值见代码逻辑
     * @return 返回执行结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/token/init")
    public @ResponseBody
    ResponseResult initTokenAccount(@RequestHeader("authorization") String token) {
        logger.info("Initialize token account, token = {}", token);
        return enterpriseService.initTokenAccount();
    }

    /**
     * 管理员使用，添加企业Token账户
     *
     * @param token 管理员Token凭证，具体值见代码逻辑
     * @param code 企业代码
     * @param name 企业名
     * @return 返回执行结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/token/create")
    public @ResponseBody
    ResponseResult createTokenAccount(@RequestHeader("authorization") String token, int code, String name) {
        logger.info("Create token account, token = {}, code = {}, name = {}", token, code, name);
        if (!ContractUtil.ENTERPRISE_TOKEN_ADDRESS.equals(token)) {
            return new ResponseResult().setCode(-10).setMsg("权限不足");
        }
        return enterpriseService.createTokenAccount(code, name);
    }

    /**
     * 管理员使用，更新企业Token账户的企业名
     *
     * @param token 管理员Token凭证，具体值见代码逻辑
     * @param code 企业代码
     * @param name 企业名
     * @return 返回执行结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/token/update")
    public @ResponseBody
    ResponseResult updateTokenAccount(@RequestHeader("authorization") String token, int code, String name) {
        logger.info("Update token account, token = {}, code = {}, name = {}", token, code, name);
        if (!ContractUtil.ENTERPRISE_TOKEN_ADDRESS.equals(token)) {
            return new ResponseResult().setCode(-10).setMsg("权限不足");
        }
        return enterpriseService.updateTokenAccount(code, name);
    }

}
