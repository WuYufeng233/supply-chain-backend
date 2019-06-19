package cn.edu.scut.sse.supply.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

@Entity
@Table(name = "core_enterprise_contract", schema = "supply_chain", catalog = "")
public class CoreEnterpriseContract {
    private Integer fid;
    private String hash;
    private Integer receiver;
    private Timestamp startDate;

    @Id
    @Column(name = "fid", nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "hash", nullable = true, length = 255)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "receiver", nullable = true)
    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreEnterpriseContract that = (CoreEnterpriseContract) o;

        if (fid != null ? !fid.equals(that.fid) : that.fid != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fid != null ? fid.hashCode() : 0;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        return result;
    }

}
