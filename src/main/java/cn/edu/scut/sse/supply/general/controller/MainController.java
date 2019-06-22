package cn.edu.scut.sse.supply.general.controller;

import cn.edu.scut.sse.supply.general.entity.vo.KeyPairVO;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.general.service.MainService;
import cn.edu.scut.sse.supply.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.security.KeyPair;

/**
 * @author Yukino Yukinoshita
 */

@Controller
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = "/test")
    public @ResponseBody
    String test() {
        return "test success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test-post")
    public @ResponseBody
    String testPost(@RequestParam String param) {
        logger.debug("param = {}", param);
        return param;
    }

    @RequestMapping("/test/path")
    public @ResponseBody
    ResponseResult testPath(@RequestParam String path) {
        File file = new File(path);
        return new ResponseResult().setCode(0).setMsg(file.exists() + "-" + file.getAbsolutePath());
    }

    @RequestMapping(value = "/get/blocknumber")
    public @ResponseBody
    String getBlockNumber() {
        try {
            return mainService.getBlockNumber();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/key/generate")
    public @ResponseBody
    ResponseResult generateKeyPair() {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        KeyPairVO vo = new KeyPairVO();
        vo.setPrivateKey(RSAUtil.convertPrivateKey(keyPair.getPrivate()));
        vo.setPublicKey(RSAUtil.convertPublicKey(keyPair.getPublic()));
        return new ResponseResult().setCode(0).setMsg("生成成功").setData(vo);
    }

}
