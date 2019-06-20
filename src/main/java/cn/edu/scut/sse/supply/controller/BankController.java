package cn.edu.scut.sse.supply.controller;

import cn.edu.scut.sse.supply.pojo.vo.ResponseResult;
import cn.edu.scut.sse.supply.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping("/bank")
@Controller
public class BankController {

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

    @RequestMapping("/contract/detail")
    public @ResponseBody
    ResponseResult getContract(@RequestHeader("authorization") String token, @RequestParam int fid) {
        return bankService.getContract(token, fid);
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
