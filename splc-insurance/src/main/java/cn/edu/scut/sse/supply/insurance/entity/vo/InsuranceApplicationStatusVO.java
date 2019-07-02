package cn.edu.scut.sse.supply.insurance.entity.vo;

/**
 * @author Yukino Yukinoshita
 */

public class InsuranceApplicationStatusVO {

    private String receiverSignature;
    private String status;

    public String getReceiverSignature() {
        return receiverSignature;
    }

    public void setReceiverSignature(String receiverSignature) {
        this.receiverSignature = receiverSignature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InsuranceApplicationStatusVO{" +
                "receiverSignature='" + receiverSignature + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
