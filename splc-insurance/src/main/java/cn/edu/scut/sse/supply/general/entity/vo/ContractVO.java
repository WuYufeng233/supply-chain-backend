package cn.edu.scut.sse.supply.general.entity.vo;

/**
 * @author Yukino Yukinoshita
 */

public class ContractVO {

    private Integer fid;
    private String hash;
    private String sponsor;
    private String receiver;
    private Long startDate;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "ContractVO{" +
                "fid=" + fid +
                ", hash='" + hash + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", receiver='" + receiver + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
