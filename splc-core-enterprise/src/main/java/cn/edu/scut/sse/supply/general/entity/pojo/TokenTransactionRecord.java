package cn.edu.scut.sse.supply.general.entity.pojo;

import javax.persistence.*;

/**
 * @author Yukino Yukinoshita
 */

@Entity
@Table(name = "token_transaction_record", schema = "supply_chain", catalog = "")
public class TokenTransactionRecord {
    private Integer id;
    private String transactionHash;
    private Long value;
    private Integer payer;
    private Integer payee;
    private Integer bindingType;
    private Integer bindingId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "transaction_hash", nullable = true, length = 80)
    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    @Basic
    @Column(name = "value", nullable = true, precision = 0)
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Basic
    @Column(name = "payer", nullable = true)
    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    @Basic
    @Column(name = "payee", nullable = true)
    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    @Basic
    @Column(name = "binding_type", nullable = true)
    public Integer getBindingType() {
        return bindingType;
    }

    public void setBindingType(Integer bindingType) {
        this.bindingType = bindingType;
    }

    @Basic
    @Column(name = "binding_id", nullable = true)
    public Integer getBindingId() {
        return bindingId;
    }

    public void setBindingId(Integer bindingId) {
        this.bindingId = bindingId;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (transactionHash != null ? transactionHash.hashCode() : 0);
        result = 31 * result + (payer != null ? payer.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + (bindingType != null ? bindingType.hashCode() : 0);
        result = 31 * result + (bindingId != null ? bindingId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenTransactionRecord that = (TokenTransactionRecord) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (transactionHash != null ? !transactionHash.equals(that.transactionHash) : that.transactionHash != null)
            return false;
        if (payer != null ? !payer.equals(that.payer) : that.payer != null) return false;
        if (payee != null ? !payee.equals(that.payee) : that.payee != null) return false;
        if (bindingType != null ? !bindingType.equals(that.bindingType) : that.bindingType != null) return false;
        if (bindingId != null ? !bindingId.equals(that.bindingId) : that.bindingId != null) return false;

        return true;
    }
}
