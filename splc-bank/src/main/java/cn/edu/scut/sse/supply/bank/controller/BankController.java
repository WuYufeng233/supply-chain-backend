package cn.edu.scut.sse.supply.bank.controller;

import cn.edu.scut.sse.supply.bank.service.BankService;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.math.BigInteger;

/**
 * 银行接口Controller类
 * <p>
 * 银行在供应链中承担了现实货币与系统虚拟货币Token交换的责任。作为一般企业，
 * 它具有基础用户管理、企业间合同管理的功能。作为银行，它具有系统虚拟货币Token
 * 管理的功能，同时对外提供银行相关申请（贷款、赎回Token、兑付Token）的功能。
 * <p>
 * 详细功能如下：
 * 1.基础用户管理：用户登录、注册、修改密码
 * 2.合同管理：上传合同文本计算Hash、发起合同、接受合同、列出合同、查看合同详情、更新合同状态
 * 3.企业信用管理：授信（包括信用惩罚）、查看企业信用
 * 4.企业Token管理：发放Token、回收Token、查看企业Token、支付Token、查看Token交易
 * 5.对外提供银行申请管理：发起申请、查看申请
 * 6.银行申请管理：接受申请、修改申请状态
 * 6.杂项：对文本签名
 *
 * @author Yukino Yukinoshita
 */

@Controller
public class BankController {

    private static final int ENTERPRISE_CODE = 1001;
    private final Logger logger = LoggerFactory.getLogger(BankController.class);
    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ResponseResult login(@RequestParam String username, @RequestParam String password) {
        logger.info("Login with username = {}, password = {}", username, password);
        return bankService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ResponseResult register(@RequestParam String username, @RequestParam String password, @RequestParam String repeatPassword) {
        logger.info("Register with username = {}, password = {}, repeatPassword = {}", username, password, repeatPassword);
        if (checkRepeatPassword(password, repeatPassword)) {
            return bankService.register(username, password);
        }

        ResponseResult result = new ResponseResult();
        result.setCode(-1);
        result.setMsg("密码与重复密码不一致");
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change-password")
    public @ResponseBody
    ResponseResult changePassword(@RequestHeader(value = "authorization") String token, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String repeatPassword) {
        logger.info("Change password with token = {}, oldPassword = {}, newPassword = {}, repeatPassword = {}", token, oldPassword, newPassword, repeatPassword);
        if (checkRepeatPassword(newPassword, repeatPassword)) {
            return bankService.changePassword(token, oldPassword, newPassword);
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
        return bankService.contractUpload(token, contract.getBytes());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/launch")
    public @ResponseBody
    ResponseResult contractLaunch(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String hash, @RequestParam int receiver) {
        logger.info("Launch contract, token = {}, fid = {}, hash = {}, receiver = {}", token, fid, hash, receiver);
        return bankService.contractLaunch(token, fid, hash, receiver);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/receive")
    public @ResponseBody
    ResponseResult receiveContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        logger.info("Receive contract, token = {}, fid = {}", token, fid);
        return bankService.receiveContract(token, fid);
    }

    @RequestMapping("/contract/list")
    public @ResponseBody
    ResponseResult listContract(@RequestHeader("authorization") String token) {
        return bankService.listContract(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/detail")
    public @ResponseBody
    ResponseResult getContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return bankService.getContract(token, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/update")
    public @ResponseBody
    ResponseResult updateContract(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String status) {
        logger.info("Update contract, token = {}, fid = {}, status = {}", token, fid, status);
        return bankService.updateContract(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/set-credit")
    public @ResponseBody
    ResponseResult setEnterpriseCredit(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger credit) {
        logger.info("Set credit, token = {}, code = {}, credit = {}", token, code, credit);
        return bankService.setEnterpriseCredit(token, code, credit);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get-credit")
    public @ResponseBody
    ResponseResult getEnterpriseCredit(@RequestHeader("authorization") String token, @RequestParam(required = false) Integer code) {
        if (code == null) {
            return bankService.getEnterpriseCredit(token, ENTERPRISE_CODE);
        } else {
            return bankService.getEnterpriseCredit(token, code);
        }
    }

    @RequestMapping("/token/list-credit")
    public @ResponseBody
    ResponseResult listEnterpriseCredit(@RequestHeader("authorization") String token) {
        return bankService.listEnterpriseCredit(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/add")
    public @ResponseBody
    ResponseResult addEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer fid) {
        logger.info("Add token, token = {}, code = {}, val = {}, type = {}, fid = {}", token, code, val, type, fid);
        return bankService.addEnterpriseToken(token, code, val, type, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/sub")
    public @ResponseBody
    ResponseResult subEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer fid) {
        logger.info("Sub token, token = {}, code = {}, val = {}, type = {}, fid = {}", token, code, val, type, fid);
        return bankService.subEnterpriseToken(token, code, val, type, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get")
    public @ResponseBody
    ResponseResult getEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam(required = false) Integer code) {
        if (code == null) {
            return bankService.getEnterpriseToken(token, ENTERPRISE_CODE);
        } else {
            return bankService.getEnterpriseToken(token, code);
        }
    }

    @RequestMapping("/token/list")
    public @ResponseBody
    ResponseResult listEnterpriseToken(@RequestHeader("authorization") String token) {
        return bankService.listEnterpriseToken(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/pay")
    public @ResponseBody
    ResponseResult payEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer id) {
        logger.info("Pay token, token = {}, code = {}, val = {}, type = {}, id = {}", token, code, val, type, id);
        return bankService.payEnterpriseToken(token, code, val, type, id);
    }

    @RequestMapping("/token/list-transaction")
    public @ResponseBody
    ResponseResult listTokenTransaction(@RequestHeader("authorization") String token) {
        return bankService.listTokenTransaction(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signature/text")
    public @ResponseBody
    ResponseResult getSignatureOfText(@RequestHeader("authorization") String token, @RequestParam String text) {
        return bankService.getSignatureOfText(token, text);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/create")
    public @ResponseBody
    ResponseResult createApplication(@RequestParam String content, @RequestParam int type, @RequestParam int code, @RequestParam String signature) {
        return bankService.createBankApplication(content, type, code, signature);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/receive")
    public @ResponseBody
    ResponseResult receiveApplication(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return bankService.receiveBankApplication(token, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/update")
    public @ResponseBody
    ResponseResult updateApplicationStatus(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String status) {
        return bankService.updateBankApplicationStatus(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/detail")
    public @ResponseBody
    ResponseResult getApplicationDetail(@RequestParam int fid) {
        return bankService.getBankApplicationDetail(fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/list")
    public @ResponseBody
    ResponseResult listApplication(@RequestParam int code) {
        return bankService.listApplication(code);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/application/list")
    public @ResponseBody
    ResponseResult listApplication(@RequestHeader("authorization") String token) {
        return bankService.listApplication(token);
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
