package cn.edu.scut.sse.supply.bank.entity.vo;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

public class EnterpriseTokenVO {

    private Integer code;
    private String name;
    private BigInteger token;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getToken() {
        return token;
    }

    public void setToken(BigInteger token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "EnterpriseTokenVO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", token=" + token +
                '}';
    }
}
