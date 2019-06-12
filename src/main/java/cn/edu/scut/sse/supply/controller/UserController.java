package cn.edu.scut.sse.supply.controller;

import cn.edu.scut.sse.supply.pojo.ResponseResult;
import cn.edu.scut.sse.supply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Yukino Yukinoshita
 */

@RequestMapping(value = "/user")
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ResponseResult login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ResponseResult register(@RequestParam String username, @RequestParam String password) {
        return userService.register(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change_password")
    public @ResponseBody
    ResponseResult changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }

}
