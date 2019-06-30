package cn.edu.scut.sse.supply.upstreamenterprise.dao;

import cn.edu.scut.sse.supply.contracts.CargoReceiveRecord;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.pojo.UpstreamEnterpriseCargoReceive;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.vo.DetailCargoVO;
import cn.edu.scut.sse.supply.util.ContractUtil;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import cn.edu.scut.sse.supply.util.Web3jUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class UpstreamEnterpriseCargoDAO {

    private static final int ENTERPRISE_CODE = 4002;
    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("db8bb163206bbb46faa66fb04310ea7ef034cd7fac35a92328db1e3b72f7836d");
    private String address = ContractUtil.CARGO_RECEIVE_ADDRESS;

    public void saveCargo(UpstreamEnterpriseCargoReceive cargo) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(cargo);
        session.getTransaction().commit();
        session.close();
    }

    public UpstreamEnterpriseCargoReceive getCargo(int id) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UpstreamEnterpriseCargoReceive> criteriaQuery = criteriaBuilder.createQuery(UpstreamEnterpriseCargoReceive.class);
        Root<UpstreamEnterpriseCargoReceive> root = criteriaQuery.from(UpstreamEnterpriseCargoReceive.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        UpstreamEnterpriseCargoReceive cargo = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return cargo;
    }

    public List<UpstreamEnterpriseCargoReceive> listCargo() {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UpstreamEnterpriseCargoReceive> criteriaQuery = criteriaBuilder.createQuery(UpstreamEnterpriseCargoReceive.class);
        Root<UpstreamEnterpriseCargoReceive> root = criteriaQuery.from(UpstreamEnterpriseCargoReceive.class);
        criteriaQuery.select(root);

        List<UpstreamEnterpriseCargoReceive> cargos = session.createQuery(criteriaQuery).list();

        session.close();
        return cargos;
    }

    public ResponseResult saveCargoToFisco(int id, String content, int consignor, Integer contractId, Integer insuranceId, Integer expressId) throws Exception {
        if (contractId == null) {
            contractId = 0;
        }
        if (insuranceId == null) {
            insuranceId = 0;
        }
        if (expressId == null) {
            expressId = 0;
        }
        BigInteger recordId = new BigInteger(String.valueOf(ENTERPRISE_CODE).concat(String.valueOf(id)));
        Web3j web3j = Web3jUtil.getWeb3j();
        CargoReceiveRecord cargoContract = CargoReceiveRecord.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = cargoContract.createRecord(recordId, content, BigInteger.valueOf(consignor), BigInteger.valueOf(contractId), BigInteger.valueOf(insuranceId), BigInteger.valueOf(expressId)).send();
        List<CargoReceiveRecord.CreateRecordEventEventResponse> responses = cargoContract.getCreateRecordEventEvents(receipt);
        if (responses.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        CargoReceiveRecord.CreateRecordEventEventResponse response = responses.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public DetailCargoVO getCargoFromFisco(int id) throws Exception {
        BigInteger recordId = new BigInteger(String.valueOf(ENTERPRISE_CODE).concat(String.valueOf(id)));
        Web3j web3j = Web3jUtil.getWeb3j();
        CargoReceiveRecord cargoContract = CargoReceiveRecord.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = cargoContract.getRecord(recordId).send();
        List<CargoReceiveRecord.GetRecordCallbackEventResponse> responses = cargoContract.getGetRecordCallbackEvents(receipt);
        if (responses.size() == 0) {
            return null;
        }
        CargoReceiveRecord.GetRecordCallbackEventResponse response = responses.get(0);
        DetailCargoVO vo = new DetailCargoVO();
        vo.setId(id);
        vo.setConsignor(response.consignor.intValue());
        vo.setContent(response.content);
        vo.setContractId(response.contractId.intValue());
        vo.setInsuranceId(response.insuranceId.intValue());
        vo.setExpressId(response.expressId.intValue());
        vo.setTime(new Timestamp(response.time.longValue()));
        return vo;

    }

}
