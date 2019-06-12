package cn.edu.scut.sse.supply.controller;

import cn.edu.scut.sse.supply.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Yukino Yukinoshita
 */

@Controller
public class MainController {

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

    @RequestMapping(value = "/helloworld/get")
    public @ResponseBody
    String get() {
        try {
            return mainService.get();
        } catch (Exception e) {
            return e.getMessage();
        }
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

    @RequestMapping(method = RequestMethod.POST, value = "/helloworld/set")
    public @ResponseBody
    String set(@RequestParam String val) {
        try {
            return mainService.set(val);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
