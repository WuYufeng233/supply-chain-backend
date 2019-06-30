package cn.edu.scut.sse.supply.general.entity.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Yukino Yukinoshita
 */

@Entity
public class Keystore {
    private Integer code;
    private String publicKey;

    @Id
    @Column(name = "code", nullable = false)
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Basic
    @Column(name = "public_key", nullable = true, length = 800)
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (publicKey != null ? publicKey.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keystore keystore = (Keystore) o;

        if (code != null ? !code.equals(keystore.code) : keystore.code != null) return false;
        if (publicKey != null ? !publicKey.equals(keystore.publicKey) : keystore.publicKey != null) return false;

        return true;
    }
}
