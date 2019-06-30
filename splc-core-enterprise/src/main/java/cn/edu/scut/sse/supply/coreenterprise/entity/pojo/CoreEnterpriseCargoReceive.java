package cn.edu.scut.sse.supply.coreenterprise.entity.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

@Entity
@Table(name = "core_enterprise_cargo_receive", schema = "supply_chain", catalog = "")
public class CoreEnterpriseCargoReceive {
    private Integer id;
    private String content;
    private Integer consignor;
    private Timestamp time;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 1024)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "consignor", nullable = true)
    public Integer getConsignor() {
        return consignor;
    }

    public void setConsignor(Integer consignor) {
        this.consignor = consignor;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (consignor != null ? consignor.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoreEnterpriseCargoReceive that = (CoreEnterpriseCargoReceive) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (consignor != null ? !consignor.equals(that.consignor) : that.consignor != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }
}
