package cn.edu.scut.sse.supply.pojo;

/**
 * @author Yukino Yukinoshita
 */

public class ContractUploadResultVO {

    private Integer fid;
    private String hash;

    public Integer getFid() {
        return fid;
    }

    public ContractUploadResultVO setFid(Integer fid) {
        this.fid = fid;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public ContractUploadResultVO setHash(String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return "ContractUploadResultVO{" +
                "fid=" + fid +
                ", hash='" + hash + '\'' +
                '}';
    }
}
