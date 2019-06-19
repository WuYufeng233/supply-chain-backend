package cn.edu.scut.sse.supply.pojo.vo;

/**
 * @author Yukino Yukinoshita
 */

public class ContractVO {

    private Integer fid;
    private String hash;
    private String enterprise;
    private String startDate;

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

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "ContractVO{" +
                "fid=" + fid +
                ", hash='" + hash + '\'' +
                ", enterprise='" + enterprise + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
