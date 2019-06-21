package cn.edu.scut.sse.supply.bank.entity.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

@Entity
@Table(name = "bank_application", schema = "supply_chain", catalog = "")
public class BankApplication {
    private Integer fid;
    private String content;
    private Integer sponsor;
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
    @Column(name = "content", nullable = true, length = 1024)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "sponsor", nullable = true)
    public Integer getSponsor() {
        return sponsor;
    }

    public void setSponsor(Integer sponsor) {
        this.sponsor = sponsor;
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
    public int hashCode() {
        int result = fid != null ? fid.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sponsor != null ? sponsor.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankApplication that = (BankApplication) o;

        if (fid != null ? !fid.equals(that.fid) : that.fid != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (sponsor != null ? !sponsor.equals(that.sponsor) : that.sponsor != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }
}
