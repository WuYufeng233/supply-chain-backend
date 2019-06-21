package cn.edu.scut.sse.supply.bank.controller;

import cn.edu.scut.sse.supply.bank.service.BankService;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping("/bank")
@Controller
public class BankController {

    private static final int ENTERPRISE_CODE = 1001;
    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ResponseResult login(@RequestParam String username, @RequestParam String password) {
        return bankService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ResponseResult register(@RequestParam String username, @RequestParam String password, @RequestParam String repeatPassword) {
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
        return bankService.contractLaunch(token, fid, hash, receiver);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/receive")
    public @ResponseBody
    ResponseResult receiveContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
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
        return bankService.updateContract(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/set-credit")
    public @ResponseBody
    ResponseResult setEnterpriseCredit(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger credit) {
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

    @RequestMapping(method = RequestMethod.POST, value = "/token/add")
    public @ResponseBody
    ResponseResult addEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val) {
        return bankService.addEnterpriseToken(token, code, val);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/sub")
    public @ResponseBody
    ResponseResult subEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val) {
        return bankService.subEnterpriseToken(token, code, val);
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

    @RequestMapping(method = RequestMethod.POST, value = "/token/pay")
    public @ResponseBody
    ResponseResult payEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val) {
        return bankService.payEnterpriseToken(token, code, val);
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
