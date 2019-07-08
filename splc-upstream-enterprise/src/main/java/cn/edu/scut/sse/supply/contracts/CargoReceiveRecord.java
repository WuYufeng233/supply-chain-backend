package cn.edu.scut.sse.supply.contracts;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class CargoReceiveRecord extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061051b806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806303e9e6091461005157806308d236af1461007e575b600080fd5b34801561005d57600080fd5b5061007c60048036038101908080359060200190929190505050610119565b005b34801561008a57600080fd5b5061011760048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001909291908035906020019092919050505061027a565b005b7f7b20f02cedbee00c4cf06d59767dfcdbca48615c1230020488179ede872c4231600080838152602001908152602001600020600101600080848152602001908152602001600020600201546000808581526020019081526020016000206003015460008086815260200190815260200160002060040154600080878152602001908152602001600020600501546000808881526020019081526020016000206006015460405180806020018781526020018681526020018581526020018481526020018381526020018281038252888181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156102645780601f1061023957610100808354040283529160200191610264565b820191906000526020600020905b81548152906001019060200180831161024757829003601f168201915b505097505050505050505060405180910390a150565b60008060008881526020019081526020016000206006015414151561032e577f3f1eb09fce99ddded2f65b32290fa05662e1ed518f58f63a14b1027fdb214c2a7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6040518082815260200180602001828103825260158152602001807fe585a5e5ba93e8aeb0e5bd95e5b7b2e5ad98e59ca800000000000000000000008152506020019250505060405180910390a1610442565b60e0604051908101604052808781526020018681526020018581526020018481526020018381526020018281526020014281525060008088815260200190815260200160002060008201518160000155602082015181600101908051906020019061039a92919061044a565b5060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c082015181600601559050507f3f1eb09fce99ddded2f65b32290fa05662e1ed518f58f63a14b1027fdb214c2a60006040518082815260200180602001828103825260188152602001807fe5889be5bbbae585a5e5ba93e8aeb0e5bd95e68890e58a9f00000000000000008152506020019250505060405180910390a15b505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061048b57805160ff19168380011785556104b9565b828001600101855582156104b9579182015b828111156104b857825182559160200191906001019061049d565b5b5090506104c691906104ca565b5090565b6104ec91905b808211156104e85760008160009055506001016104d0565b5090565b905600a165627a7a723058208f7d630ee1eb2899c6231a84d961ced2b99309504cb935cf3093682275572ae70029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"}],\"name\":\"getRecord\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"},{\"name\":\"content\",\"type\":\"string\"},{\"name\":\"consignor\",\"type\":\"int256\"},{\"name\":\"contractId\",\"type\":\"uint256\"},{\"name\":\"insuranceId\",\"type\":\"uint256\"},{\"name\":\"expressId\",\"type\":\"uint256\"}],\"name\":\"createRecord\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"createRecordEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"content\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"consignor\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"contractId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"insuranceId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"expressId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"time\",\"type\":\"uint256\"}],\"name\":\"getRecordCallback\",\"type\":\"event\"}]";

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_CREATERECORD = "createRecord";

    public static final Event CREATERECORDEVENT_EVENT = new Event("createRecordEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

    public static final Event GETRECORDCALLBACK_EVENT = new Event("getRecordCallback",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Int256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    @Deprecated
    protected CargoReceiveRecord(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CargoReceiveRecord(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CargoReceiveRecord(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CargoReceiveRecord(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static CargoReceiveRecord load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CargoReceiveRecord(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CargoReceiveRecord load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CargoReceiveRecord(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CargoReceiveRecord load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CargoReceiveRecord(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CargoReceiveRecord load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CargoReceiveRecord(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CargoReceiveRecord> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CargoReceiveRecord.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CargoReceiveRecord> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CargoReceiveRecord.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public List<CreateRecordEventEventResponse> getCreateRecordEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATERECORDEVENT_EVENT, transactionReceipt);
        ArrayList<CreateRecordEventEventResponse> responses = new ArrayList<CreateRecordEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CreateRecordEventEventResponse typedResponse = new CreateRecordEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateRecordEventEventResponse> createRecordEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, CreateRecordEventEventResponse>() {
            @Override
            public CreateRecordEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CREATERECORDEVENT_EVENT, log);
                CreateRecordEventEventResponse typedResponse = new CreateRecordEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreateRecordEventEventResponse> createRecordEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATERECORDEVENT_EVENT));
        return createRecordEventEventFlowable(filter);
    }

    public List<GetRecordCallbackEventResponse> getGetRecordCallbackEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(GETRECORDCALLBACK_EVENT, transactionReceipt);
        ArrayList<GetRecordCallbackEventResponse> responses = new ArrayList<GetRecordCallbackEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            GetRecordCallbackEventResponse typedResponse = new GetRecordCallbackEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.content = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.consignor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.contractId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.insuranceId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.expressId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GetRecordCallbackEventResponse> getRecordCallbackEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, GetRecordCallbackEventResponse>() {
            @Override
            public GetRecordCallbackEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(GETRECORDCALLBACK_EVENT, log);
                GetRecordCallbackEventResponse typedResponse = new GetRecordCallbackEventResponse();
                typedResponse.log = log;
                typedResponse.content = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.consignor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.contractId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.insuranceId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.expressId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GetRecordCallbackEventResponse> getRecordCallbackEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GETRECORDCALLBACK_EVENT));
        return getRecordCallbackEventFlowable(filter);
    }

    public static RemoteCall<CargoReceiveRecord> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CargoReceiveRecord.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CargoReceiveRecord> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CargoReceiveRecord.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<TransactionReceipt> getRecord(BigInteger id) {
        final Function function = new Function(
                FUNC_GETRECORD,
                Arrays.<Type>asList(new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getRecord(BigInteger id, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETRECORD,
                Arrays.<Type>asList(new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getRecordSeq(BigInteger id) {
        final Function function = new Function(
                FUNC_GETRECORD,
                Arrays.<Type>asList(new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> createRecord(BigInteger id, String content, BigInteger consignor, BigInteger contractId, BigInteger insuranceId, BigInteger expressId) {
        final Function function = new Function(
                FUNC_CREATERECORD,
                Arrays.<Type>asList(new Uint256(id),
                        new Utf8String(content),
                        new Int256(consignor),
                        new Uint256(contractId),
                        new Uint256(insuranceId),
                        new Uint256(expressId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createRecord(BigInteger id, String content, BigInteger consignor, BigInteger contractId, BigInteger insuranceId, BigInteger expressId, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATERECORD,
                Arrays.<Type>asList(new Uint256(id),
                        new Utf8String(content),
                        new Int256(consignor),
                        new Uint256(contractId),
                        new Uint256(insuranceId),
                        new Uint256(expressId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String createRecordSeq(BigInteger id, String content, BigInteger consignor, BigInteger contractId, BigInteger insuranceId, BigInteger expressId) {
        final Function function = new Function(
                FUNC_CREATERECORD,
                Arrays.<Type>asList(new Uint256(id),
                        new Utf8String(content),
                        new Int256(consignor),
                        new Uint256(contractId),
                        new Uint256(insuranceId),
                        new Uint256(expressId)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public static class CreateRecordEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class GetRecordCallbackEventResponse {
        public Log log;

        public String content;

        public BigInteger consignor;

        public BigInteger contractId;

        public BigInteger insuranceId;

        public BigInteger expressId;

        public BigInteger time;
    }
}
