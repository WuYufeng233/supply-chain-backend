package cn.edu.scut.sse.supply.upstreamenterprise.entity.vo;

import java.sql.Timestamp;

/**
 * @author Yukino Yukinoshita
 */

public class DetailCargoVO {
    private Integer id;
    private String content;
    private Integer consignor;
    private Integer contractId;
    private Integer insuranceId;
    private Integer expressId;
    private Timestamp time;

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

    public Integer getConsignor() {
        return consignor;
    }

    public void setConsignor(Integer consignor) {
        this.consignor = consignor;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DetailCargoVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", consignor=" + consignor +
                ", contractId=" + contractId +
                ", insuranceId=" + insuranceId +
                ", expressId=" + expressId +
                ", time=" + time +
                '}';
    }
}
