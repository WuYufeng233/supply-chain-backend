package cn.edu.scut.sse.supply.service;

import cn.edu.scut.sse.supply.dao.InsuranceUserDAO;
import cn.edu.scut.sse.supply.pojo.InsuranceUser;
import cn.edu.scut.sse.supply.pojo.ResponseResult;
import cn.edu.scut.sse.supply.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class InsuranceService {

    private InsuranceUserDAO insuranceUserDAO;

    @Autowired
    public InsuranceService(InsuranceUserDAO insuranceUserDAO) {
        this.insuranceUserDAO = insuranceUserDAO;
    }

    public ResponseResult login(String username, String password) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        InsuranceUser user = insuranceUserDAO.getUserByName(username);
        if (user == null) {
            result.setCode(-1);
            result.setMsg("用户不存在或密码错误");
            return result;
        }
        if (!user.getPassword().equals(password)) {
            result.setCode(-1);
            result.setMsg("密码错误");
            return result;
        }
        result.setCode(0);
        result.setMsg("登录成功");
        result.setData(user);
        return result;
    }

    public ResponseResult register(String username, String password) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        if (insuranceUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        InsuranceUser user = new InsuranceUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(HashUtil.findToken(user));
        insuranceUserDAO.saveUser(user);

        result.setCode(0);
        result.setMsg("注册成功");
        result.setData(user);
        return result;
    }

    public ResponseResult changePassword(String token, String oldPassword, String newPassword) {
        ResponseResult result = new ResponseResult();
        if (token == null || "".equals(token.trim())
                || oldPassword == null || "".equals(oldPassword.trim())
                || newPassword == null || "".equals(newPassword.trim())) {
            result.setCode(-1);
            result.setMsg("密码为空");
            return result;
        }
        InsuranceUser user = insuranceUserDAO.getUserByToken(token);
        if (user == null) {
            result.setCode(-3);
            result.setMsg("用户不存在");
            return result;
        }
        if (!user.getPassword().equals(oldPassword)) {
            result.setCode(-1);
            result.setMsg("用户旧密码错误");
            return result;
        }
        user.setPassword(newPassword);
        user.setToken(HashUtil.findToken(user));
        insuranceUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

}
