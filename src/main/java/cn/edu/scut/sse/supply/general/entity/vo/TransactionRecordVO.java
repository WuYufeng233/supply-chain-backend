package cn.edu.scut.sse.supply.general.entity.vo;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

public class TransactionRecordVO {

    private Integer id;
    private String transactionHash;
    private String payer;
    private String payee;
    private BigInteger value;
    private String type;
    private Integer fid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "TransactionRecordVO{" +
                "id=" + id +
                ", transactionHash='" + transactionHash + '\'' +
                ", payer='" + payer + '\'' +
                ", payee='" + payee + '\'' +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", fid=" + fid +
                '}';
    }
}
