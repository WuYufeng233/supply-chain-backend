package cn.edu.scut.sse.supply.insurance.controller;

import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.insurance.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping("/insurance")
@Controller
public class InsuranceController {

    private InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ResponseResult login(@RequestParam String username, @RequestParam String password) {
        return insuranceService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ResponseResult register(@RequestParam String username, @RequestParam String password, @RequestParam String repeatPassword) {
        if (checkRepeatPassword(password, repeatPassword)) {
            return insuranceService.register(username, password);
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
            return insuranceService.changePassword(token, oldPassword, newPassword);
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
        return insuranceService.contractUpload(token, contract.getBytes());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/launch")
    public @ResponseBody
    ResponseResult contractLaunch(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String hash, @RequestParam int receiver) {
        return insuranceService.contractLaunch(token, fid, hash, receiver);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/receive")
    public @ResponseBody
    ResponseResult receiveContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return insuranceService.receiveContract(token, fid);
    }

    @RequestMapping("/contract/list")
    public @ResponseBody
    ResponseResult listContract(@RequestHeader("authorization") String token) {
        return insuranceService.listContract(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/detail")
    public @ResponseBody
    ResponseResult getContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return insuranceService.getContract(token, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contract/update")
    public @ResponseBody
    ResponseResult updateContract(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String status) {
        return insuranceService.updateContract(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get-credit")
    public @ResponseBody
    ResponseResult getEnterpriseCredit(@RequestHeader("authorization") String token) {
        return insuranceService.getEnterpriseCredit(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/get")
    public @ResponseBody
    ResponseResult getEnterpriseToken(@RequestHeader("authorization") String token) {
        return insuranceService.getEnterpriseToken(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/pay")
    public @ResponseBody
    ResponseResult payEnterpriseToken(@RequestHeader("authorization") String token, @RequestParam int code, @RequestParam BigInteger val, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer id) {
        return insuranceService.payEnterpriseToken(token, code, val, type, id);
    }

    @RequestMapping("/token/list-transaction")
    public @ResponseBody
    ResponseResult listTokenTransaction(@RequestHeader("authorization") String token) {
        return insuranceService.listTokenTransaction(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signature/text")
    public @ResponseBody
    ResponseResult getSignatureOfText(@RequestHeader("authorization") String token, @RequestParam String text) {
        return insuranceService.getSignatureOfText(token, text);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/create")
    public @ResponseBody
    ResponseResult createApplication(@RequestParam String content, @RequestParam int type, @RequestParam int code, @RequestParam String signature) {
        return insuranceService.createInsuranceApplication(content, type, code, signature);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/receive")
    public @ResponseBody
    ResponseResult receiveApplication(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return insuranceService.receiveInsuranceApplication(token, fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/update")
    public @ResponseBody
    ResponseResult updateApplicationStatus(@RequestHeader("authorization") String token, @RequestParam int fid, @RequestParam String status) {
        return insuranceService.updateInsuranceApplicationStatus(token, fid, status);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/detail")
    public @ResponseBody
    ResponseResult getApplicationDetail(@RequestParam int fid) {
        return insuranceService.getInsuranceApplicationDetail(fid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application/list")
    public @ResponseBody
    ResponseResult listApplication(@RequestParam int code) {
        return insuranceService.listApplication(code);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/application/list")
    public @ResponseBody
    ResponseResult listApplication(@RequestHeader("authorization") String token) {
        return insuranceService.listApplication(token);
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
