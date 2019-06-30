package cn.edu.scut.sse.supply.bank.entity.vo;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

public class EnterpriseCreditVO {

    private Integer code;
    private String name;
    private BigInteger credit;

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

    public BigInteger getCredit() {
        return credit;
    }

    public void setCredit(BigInteger credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "EnterpriseCreditVO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }
}
