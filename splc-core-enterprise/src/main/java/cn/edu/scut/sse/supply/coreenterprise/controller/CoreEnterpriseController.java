package cn.edu.scut.sse.supply.coreenterprise.controller;

import cn.edu.scut.sse.supply.coreenterprise.service.CoreEnterpriseService;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.math.BigInteger;

/**
 * 核心企业接口Controller类
 * <p>
 * 核心企业大部分功能与银行类似
 * 包括用户管理、合同管理、Token管理等
 * <p>
 * 核心企业特有接口为入库信息管理，当货物抵达时可调用入库接口保存记录
 *
 * @author Yukino Yukinoshita
 */

@Controller
public class CoreEnterpriseController {

    private CoreEnterpriseService coreEnterpriseService;

    @Autowired
    public CoreEnterpriseController(CoreEnterpriseService coreEnterpriseService) {
        this.coreEnterpriseService = coreEnterpriseService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ResponseResult login(@RequestParam String username, @RequestParam String password) {
        return coreEnterpriseService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ResponseResult register(@RequestParam String username, @RequestParam String password, @RequestParam String repeatPassword) {
        if (checkRepeatPassword(password, repeatPassword)) {
            return coreEnterpriseService.register(username, password);
        }

        ResponseResult result = new ResponseResult();
        result.setCode(-1);
        result.setMsg("密码与重复密码不一致");
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change-password")
    public @ResponseBody
    ResponseResult changePassword(@RequestHeader(value = "authorization") String token, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String repeatPassword) {
        if (checkRepeatPassword(newPassword, repeatPassword)) {
            return coreEnterpriseService.changePassword(token, oldPassword, newPassword);
        }

        ResponseResult result = new ResponseResult();
        result.setCode(-1);
        result.setMsg("密码与重复密码不一致");
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/upload")
    public @ResponseBody
    ResponseResult contractUpload(@RequestHeader("authorization") String token, @RequestParam CommonsMultipartFile contract) {
        if (contract == null) {
            return new ResponseResult().setCode(-1).setMsg("文件为空");
        }
        return coreEnterpriseService.contractUpload(token, contract.getBytes());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/launch")
    public @ResponseBody
    ResponseResult contractLaunch(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String hash, @RequestParam int receiver) {
        return coreEnterpriseService.contractLaunch(token, fid, hash, receiver);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/receive")
    public @ResponseBody
    ResponseResult receiveContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return coreEnterpriseService.receiveContract(token, fid);
    }

    @RequestMapping("/contract/list")
    public @ResponseBody
    ResponseResult listContract(@RequestHeader("authorization") String token) {
        return coreEnterpriseService.listContract(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/detail")
    public @ResponseBody
    ResponseResult getContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return coreEnterpriseService.getContract(token, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/update")
    public @ResponseBody
    ResponseResult updateContract(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String status) {
        return coreEnterpriseService.updateContract(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get-credit")
    public @ResponseBody
    ResponseResult getEnterpriseCredit(@RequestHeader("authorization") String token) {
        return coreEnterpriseService.getEnterpriseCredit(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get")
    public @ResponseBody
    ResponseResult getEnterpriseToken(@RequestHeader("authorization") String token) {
        return coreEnterpriseService.getEnterpriseToken(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/pay")
    public @ResponseBody
    ResponseResult payEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer id) {
        return coreEnterpriseService.payEnterpriseToken(token, code, val, type, id);
    }

    @RequestMapping("/token/list-transaction")
    public @ResponseBody
    ResponseResult listTokenTransaction(@RequestHeader("authorization") String token) {
        return coreEnterpriseService.listTokenTransaction(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signature/text")
    public @ResponseBody
    ResponseResult getSignatureOfText(@RequestHeader("authorization") String token, @RequestParam String text) {
        return coreEnterpriseService.getSignatureOfText(token, text);
    }

    /**
     * 货物入库
     *
     * @param token 核心企业用户Token凭证
     * @param content 货物内容
     * @param consignor 寄货企业代码
     * @param contractId 可选，绑定合同ID
     * @param expressId 可选，绑定物流ID
     * @param insuranceId 可选，绑定货物保险ID
     * @return 返回执行结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cargo/save")
    public @ResponseBody
    ResponseResult saveCargo(@RequestHeader("authorization") String token, @RequestParam String content, @RequestParam int consignor,
                             @RequestParam(required = false) Integer contractId,
                             @RequestParam(required = false) Integer expressId,
                             @RequestParam(required = false) Integer insuranceId) {
        return coreEnterpriseService.saveCargo(token, content, consignor, contractId, expressId, insuranceId);
    }

    /**
     * 列出本地数据库中入库记录
     *
     * @param token 核心企业用户Token凭证
     * @return 返回入库记录列表
     */
    @RequestMapping("/cargo/list")
    public @ResponseBody
    ResponseResult listCargo(@RequestHeader("authorization") String token) {
        return coreEnterpriseService.listCargo(token);
    }

    /**
     * 获取区块链中入库记录
     *
     * @param token 核心企业用户Token凭证
     * @param id 入库记录ID
     * @return 返回入库记录
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cargo/get")
    public @ResponseBody
    ResponseResult getCargo(@RequestHeader("authorization") String token, @RequestParam int id) {
        return coreEnterpriseService.getCargo(token, id);
    }

    private boolean checkRepeatPassword(String s1, String s2) {
        if (s1 == null || "".equals(s1)) {
            return false;
        }
        if (s2 == null || "".equals(s2)) {
            return false;
        }
        return s1.trim().equals(s2.trim());
    }

}
