package cn.edu.scut.sse.supply.general.controller;

import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.general.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping("/token")
@Controller
public class TokenController {

    private TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get")
    public @ResponseBody
    ResponseResult getToken(@RequestHeader(value = "authorization") String token) {
//        try {
//            return tokenService.getToken(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ResponseResult result = new ResponseResult();
//            result.setCode(-11);
//            result.setMsg("内部应用程序错误");
//            result.setData(e.getMessage());
//            return result;
//        }
        return new ResponseResult().setCode(-10).setMsg("开发中");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody
    ResponseResult addToken(@RequestHeader(value = "authorization") String token, @RequestParam String enterpriseCode, @RequestParam BigInteger value) {
//        try {
//            return tokenService.addToken(token, enterpriseCode, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ResponseResult result = new ResponseResult();
//            result.setCode(-11);
//            result.setMsg("内部应用程序错误");
//            result.setData(e.getMessage());
//            return result;
//        }
        return new ResponseResult().setCode(-10).setMsg("开发中");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sub")
    public @ResponseBody
    ResponseResult subToken(@RequestHeader(value = "authorization") String token, @RequestParam String enterpriseCode, @RequestParam BigInteger value) {
//        try {
//            return tokenService.subToken(token, enterpriseCode, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ResponseResult result = new ResponseResult();
//            result.setCode(-11);
//            result.setMsg("内部应用程序错误");
//            result.setData(e.getMessage());
//            return result;
//        }
        return new ResponseResult().setCode(-10).setMsg("开发中");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    public @ResponseBody
    ResponseResult payToken(@RequestHeader(value = "authorization") String token, @RequestParam String targetEnterpriseCode, @RequestParam BigInteger value) {
//        try {
//            return tokenService.payToken(token, targetEnterpriseCode, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ResponseResult result = new ResponseResult();
//            result.setCode(-11);
//            result.setMsg("内部应用程序错误");
//            result.setData(e.getMessage());
//            return result;
//        }
        return new ResponseResult().setCode(-10).setMsg("开发中");
    }

}
