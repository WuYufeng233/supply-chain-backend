package cn.edu.scut.sse.supply.bank.entity.vo;

import cn.edu.scut.sse.supply.bank.entity.pojo.BankApplication;

import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

public class BankApplicationVO {
    private Integer fid;
    private String content;
    private Integer sponsor;
    private Integer receiver;
    private Integer type;
    private Timestamp startDate;
    private String status;

    public static BankApplicationVO from(BankApplication application) {
        BankApplicationVO vo = new BankApplicationVO();
        vo.setFid(application.getFid());
        vo.setContent(application.getContent());
        vo.setSponsor(application.getSponsor());
        vo.setReceiver(application.getReceiver());
        vo.setType(application.getType());
        vo.setStartDate(application.getStartDate());
        return vo;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSponsor() {
        return sponsor;
    }

    public void setSponsor(Integer sponsor) {
        this.sponsor = sponsor;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankApplicationVO{" +
                "fid=" + fid +
                ", content='" + content + '\'' +
                ", sponsor=" + sponsor +
                ", receiver=" + receiver +
                ", type=" + type +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                '}';
    }
}
