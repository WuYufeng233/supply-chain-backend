package cn.edu.scut.sse.supply.bank.entity.pojo;

import javax.persistence.*;

/**
 * @author Yukino Yukinoshita
 */

@Entity
@Table(name = "bank_user", schema = "supply_chain", catalog = "")
public class BankUser {
    private Integer uid;
    private String username;
    private String password;
    private String token;

    @Id
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 256)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankUser bankUser = (BankUser) o;

        if (uid != null ? !uid.equals(bankUser.uid) : bankUser.uid != null) return false;
        if (username != null ? !username.equals(bankUser.username) : bankUser.username != null) return false;
        if (password != null ? !password.equals(bankUser.password) : bankUser.password != null) return false;
        if (token != null ? !token.equals(bankUser.token) : bankUser.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
