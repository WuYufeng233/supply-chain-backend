package cn.edu.scut.sse.supply.general.entity.vo;

/**
 * @author Yukino Yukinoshita
 */

public class DetailContractVO {

    private Integer contractId;
    private String hash;
    private String sponsor;
    private String sponsorSignature;
    private Integer sponsorVerify;
    private String receiver;
    private String receiverSignature;
    private Integer receiverVerify;
    private String startDate;
    private String status;

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getSponsorSignature() {
        return sponsorSignature;
    }

    public void setSponsorSignature(String sponsorSignature) {
        this.sponsorSignature = sponsorSignature;
    }

    public Integer getSponsorVerify() {
        return sponsorVerify;
    }

    public void setSponsorVerify(Integer sponsorVerify) {
        this.sponsorVerify = sponsorVerify;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverSignature() {
        return receiverSignature;
    }

    public void setReceiverSignature(String receiverSignature) {
        this.receiverSignature = receiverSignature;
    }

    public Integer getReceiverVerify() {
        return receiverVerify;
    }

    public void setReceiverVerify(Integer receiverVerify) {
        this.receiverVerify = receiverVerify;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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
        return "DetailContractVO{" +
                "contractId=" + contractId +
                ", hash='" + hash + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", sponsorSignature='" + sponsorSignature + '\'' +
                ", sponsorVerify=" + sponsorVerify +
                ", receiver='" + receiver + '\'' +
                ", receiverSignature='" + receiverSignature + '\'' +
                ", receiverVerify=" + receiverVerify +
                ", startDate='" + startDate + '\'' +
                ", status=" + status +
                '}';
    }
}
