package cn.edu.scut.sse.supply.bank.entity.vo;

import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

public class DetailBankApplicationVO {
    private Integer id;
    private String content;
    private Integer sponsor;
    private Integer receiver;
    private String sponsorSignature;
    private String receiverSignature;
    private Integer sponsorVerify;
    private Integer receiverVerify;
    private Integer applicationType;
    private Timestamp startTime;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSponsorSignature() {
        return sponsorSignature;
    }

    public void setSponsorSignature(String sponsorSignature) {
        this.sponsorSignature = sponsorSignature;
    }

    public String getReceiverSignature() {
        return receiverSignature;
    }

    public void setReceiverSignature(String receiverSignature) {
        this.receiverSignature = receiverSignature;
    }

    public Integer getSponsorVerify() {
        return sponsorVerify;
    }

    public void setSponsorVerify(Integer sponsorVerify) {
        this.sponsorVerify = sponsorVerify;
    }

    public Integer getReceiverVerify() {
        return receiverVerify;
    }

    public void setReceiverVerify(Integer receiverVerify) {
        this.receiverVerify = receiverVerify;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DetailBankApplicationVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sponsor=" + sponsor +
                ", receiver=" + receiver +
                ", sponsorSignature='" + sponsorSignature + '\'' +
                ", receiverSignature='" + receiverSignature + '\'' +
                ", applicationType=" + applicationType +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                '}';
    }
}
