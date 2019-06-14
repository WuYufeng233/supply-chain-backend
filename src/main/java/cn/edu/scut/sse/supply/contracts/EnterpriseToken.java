package cn.edu.scut.sse.supply.contracts;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
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
public class EnterpriseToken extends Contract {
    public static final String FUNC_MINTER = "minter";
    public static final String FUNC_ADDTOKEN = "addToken";
    public static final String FUNC_CREATEENTERPRISE = "createEnterprise";
    public static final String FUNC_PAYTOKEN = "payToken";
    public static final String FUNC_GETTOKEN = "getToken";
    public static final String FUNC_SUBTOKEN = "subToken";
    public static final Event CREATEENTERPRISEEVENT_EVENT = new Event("createEnterpriseEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    public static final Event ADDTOKENEVENT_EVENT = new Event("addTokenEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event SUBTOKENEVENT_EVENT = new Event("subTokenEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event PAYTOKENEVENT_EVENT = new Event("payTokenEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    private static final String BINARY = "608060405234801561001057600080fd5b5033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610b5c806100616000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063075461721461007d57806317b9ebec146100d45780633f4dca591461010b578063ce404d2d1461017e578063e4b50cb8146101bf578063f61c55f014610200575b600080fd5b34801561008957600080fd5b50610092610237565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156100e057600080fd5b50610109600480360381019080803590602001909291908035906020019092919050505061025d565b005b34801561011757600080fd5b5061017c60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506103e3565b005b34801561018a57600080fd5b506101bd6004803603810190808035906020019092919080359060200190929190803590602001909291905050506105d5565b005b3480156101cb57600080fd5b506101ea60048036038101908080359060200190929190505050610832565b6040518082815260200191505060405180910390f35b34801561020c57600080fd5b506102356004803603810190808035906020019092919080359060200190929190505050610852565b005b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610349577f86a1fd4739ec1f40aa2adb89fd005fe52344286ad4b7341989109e936ff6c8f47ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff660405180828152602001806020018281038252600c8152602001807fe69d83e99990e4b88de8b6b300000000000000000000000000000000000000008152506020019250505060405180910390a16103df565b8060016000848152602001908152602001600020600201600082825401925050819055507f86a1fd4739ec1f40aa2adb89fd005fe52344286ad4b7341989109e936ff6c8f460006040518082815260200180602001828103825260118152602001807f546f6b656ee5a29ee58aa0e68890e58a9f0000000000000000000000000000008152506020019250505060405180910390a15b5050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156104cf577f6ed1237213d19af243be645115aca9751349fa743c4ccffe1bc5a40601356bd37ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff660405180828152602001806020018281038252600c8152602001807fe69d83e99990e4b88de8b6b300000000000000000000000000000000000000008152506020019250505060405180910390a16105d1565b606060405190810160405280838152602001828152602001600081525060016000848152602001908152602001600020600082015181600001556020820151816001019080519060200190610525929190610a8b565b506040820151816002015590505060008290806001815401808255809150509060018203906000526020600020016000909192909190915055507f6ed1237213d19af243be645115aca9751349fa743c4ccffe1bc5a40601356bd3600060405180828152602001806020018281038252600c8152602001807fe5889be5bbbae68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b5050565b6000816001600086815260200190815260200160002060020154101561068a577f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb347ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb6040518082815260200180602001828103825260118152602001807f546f6b656ee4bd99e9a29de4b88de8b6b30000000000000000000000000000008152506020019250505060405180910390a161082c565b600090505b60008054905081101561077257826000828154811015156106ac57fe5b906000526020600020015414156106c257610772565b600160008054905003811415610767577f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb347fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6040518082815260200180602001828103825260188152602001807fe4b88de5ad98e59ca8e79a84e79baee6a087e585ace58fb800000000000000008152506020019250505060405180910390a161082c565b80600101905061068f565b8160016000868152602001908152602001600020600201600082825403925050819055508160016000858152602001908152602001600020600201600082825401925050819055507f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb34600060405180828152602001806020018281038252600c8152602001807fe694afe4bb98e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b50505050565b600060016000838152602001908152602001600020600201549050919050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561093e577f5fb1f8a10137232332b1119892c7d5a849be56c5422de50fcbdbaf25edfceeed7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff660405180828152602001806020018281038252600c8152602001807fe69d83e99990e4b88de8b6b300000000000000000000000000000000000000008152506020019250505060405180910390a1610a87565b80600160008481526020019081526020016000206002015410156109f1577f5fb1f8a10137232332b1119892c7d5a849be56c5422de50fcbdbaf25edfceeed7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb6040518082815260200180602001828103825260118152602001807f546f6b656ee4bd99e9a29de4b88de8b6b30000000000000000000000000000008152506020019250505060405180910390a1610a87565b8060016000848152602001908152602001600020600201600082825403925050819055507f5fb1f8a10137232332b1119892c7d5a849be56c5422de50fcbdbaf25edfceeed60006040518082815260200180602001828103825260118152602001807f546f6b656ee5878fe5b091e68890e58a9f0000000000000000000000000000008152506020019250505060405180910390a15b5050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610acc57805160ff1916838001178555610afa565b82800160010185558215610afa579182015b82811115610af9578251825591602001919060010190610ade565b5b509050610b079190610b0b565b5090565b610b2d91905b80821115610b29576000816000905550600101610b11565b5090565b905600a165627a7a72305820a2390520f77ed13e2d0317b580c68a297c5458f50f3e6e9067c5f3b4eae104da0029";
    ;

    @Deprecated
    protected EnterpriseToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EnterpriseToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EnterpriseToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EnterpriseToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static EnterpriseToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EnterpriseToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EnterpriseToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EnterpriseToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EnterpriseToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EnterpriseToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EnterpriseToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EnterpriseToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EnterpriseToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EnterpriseToken.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<EnterpriseToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EnterpriseToken.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EnterpriseToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EnterpriseToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EnterpriseToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EnterpriseToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<String> minter() {
        final Function function = new Function(FUNC_MINTER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addToken(BigInteger code, BigInteger val) {
        final Function function = new Function(
                FUNC_ADDTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addToken(BigInteger code, BigInteger val, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> createEnterprise(BigInteger code, String name) {
        final Function function = new Function(
                FUNC_CREATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createEnterprise(BigInteger code, String name, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> payToken(BigInteger source, BigInteger target, BigInteger val) {
        final Function function = new Function(
                FUNC_PAYTOKEN,
                Arrays.<Type>asList(new Uint256(source),
                        new Uint256(target),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void payToken(BigInteger source, BigInteger target, BigInteger val, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PAYTOKEN,
                Arrays.<Type>asList(new Uint256(source),
                        new Uint256(target),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<BigInteger> getToken(BigInteger code) {
        final Function function = new Function(FUNC_GETTOKEN,
                Arrays.<Type>asList(new Uint256(code)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> subToken(BigInteger code, BigInteger val) {
        final Function function = new Function(
                FUNC_SUBTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void subToken(BigInteger code, BigInteger val, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SUBTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<CreateEnterpriseEventEventResponse> getCreateEnterpriseEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEENTERPRISEEVENT_EVENT, transactionReceipt);
        ArrayList<CreateEnterpriseEventEventResponse> responses = new ArrayList<CreateEnterpriseEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CreateEnterpriseEventEventResponse typedResponse = new CreateEnterpriseEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateEnterpriseEventEventResponse> createEnterpriseEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, CreateEnterpriseEventEventResponse>() {
            @Override
            public CreateEnterpriseEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CREATEENTERPRISEEVENT_EVENT, log);
                CreateEnterpriseEventEventResponse typedResponse = new CreateEnterpriseEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreateEnterpriseEventEventResponse> createEnterpriseEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATEENTERPRISEEVENT_EVENT));
        return createEnterpriseEventEventFlowable(filter);
    }

    public List<AddTokenEventEventResponse> getAddTokenEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDTOKENEVENT_EVENT, transactionReceipt);
        ArrayList<AddTokenEventEventResponse> responses = new ArrayList<AddTokenEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddTokenEventEventResponse typedResponse = new AddTokenEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddTokenEventEventResponse> addTokenEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, AddTokenEventEventResponse>() {
            @Override
            public AddTokenEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ADDTOKENEVENT_EVENT, log);
                AddTokenEventEventResponse typedResponse = new AddTokenEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddTokenEventEventResponse> addTokenEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDTOKENEVENT_EVENT));
        return addTokenEventEventFlowable(filter);
    }

    public List<SubTokenEventEventResponse> getSubTokenEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SUBTOKENEVENT_EVENT, transactionReceipt);
        ArrayList<SubTokenEventEventResponse> responses = new ArrayList<SubTokenEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SubTokenEventEventResponse typedResponse = new SubTokenEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SubTokenEventEventResponse> subTokenEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, SubTokenEventEventResponse>() {
            @Override
            public SubTokenEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SUBTOKENEVENT_EVENT, log);
                SubTokenEventEventResponse typedResponse = new SubTokenEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SubTokenEventEventResponse> subTokenEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBTOKENEVENT_EVENT));
        return subTokenEventEventFlowable(filter);
    }

    public List<PayTokenEventEventResponse> getPayTokenEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PAYTOKENEVENT_EVENT, transactionReceipt);
        ArrayList<PayTokenEventEventResponse> responses = new ArrayList<PayTokenEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            PayTokenEventEventResponse typedResponse = new PayTokenEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PayTokenEventEventResponse> payTokenEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, PayTokenEventEventResponse>() {
            @Override
            public PayTokenEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PAYTOKENEVENT_EVENT, log);
                PayTokenEventEventResponse typedResponse = new PayTokenEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PayTokenEventEventResponse> payTokenEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYTOKENEVENT_EVENT));
        return payTokenEventEventFlowable(filter);
    }

    public static class CreateEnterpriseEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class AddTokenEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class SubTokenEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class PayTokenEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }
}
