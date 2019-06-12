package cn.edu.scut.sse.supply.service;

import cn.edu.scut.sse.supply.dao.UserDAO;
import cn.edu.scut.sse.supply.pojo.ResponseResult;
import cn.edu.scut.sse.supply.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static String getNewUserToken(User user) {
        String token = user.getUsername().concat(user.getPassword());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            SecureRandom random = new SecureRandom();
            int randomInt = random.nextInt(Math.abs(user.hashCode()));
            long l1 = (long) randomInt * (long) user.hashCode() * System.currentTimeMillis();
            token = byteArrayToHex(messageDigest.digest(String.valueOf(l1).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    public ResponseResult login(String username, String password) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        User user = userDAO.getUserByName(username);
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
        if (userDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(getNewUserToken(user));
        userDAO.saveUser(user);
        result.setCode(0);
        result.setMsg("注册成功");
        result.setData(user);
        return result;
    }

    public ResponseResult changePassword(String username, String oldPassword, String newPassword) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim())
                || oldPassword == null || "".equals(oldPassword.trim())
                || newPassword == null || "".equals(newPassword.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        User user = userDAO.getUserByName(username);
        if (user == null) {
            result.setCode(-3);
            result.setMsg("用户名不存在");
            return result;
        }
        if (!user.getPassword().equals(oldPassword)) {
            result.setCode(-1);
            result.setMsg("用户旧密码错误");
            return result;
        }
        user.setPassword(newPassword);
        user.setToken(getNewUserToken(user));
        userDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

}
