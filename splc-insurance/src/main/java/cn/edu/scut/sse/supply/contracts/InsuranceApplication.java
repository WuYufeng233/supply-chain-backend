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
public class InsuranceApplication extends Contract {
    public static final String FUNC_RECEIVEAPPLICATION = "receiveApplication";
    public static final String FUNC_UPDATEAPPLICATIONSTATUS = "updateApplicationStatus";
    public static final String FUNC_CREATEAPPLICATION = "createApplication";
    public static final String FUNC_GETAPPLICATION = "getApplication";
    public static final Event CREATEAPPLICATIONEVENT_EVENT = new Event("createApplicationEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    public static final Event RECEIVEAPPLICATIONEVENT_EVENT = new Event("receiveApplicationEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event UPDATEAPPLICATIONSTATUSEVENT_EVENT = new Event("updateApplicationStatusEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event GETAPPLICATIONCALLBACK_EVENT = new Event("getApplicationCallback",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Int256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    private static final String BINARY = "608060405234801561001057600080fd5b50610dce806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063098f80441461006757806357a166f0146100da578063bcdd27001461014d578063e67aa8d714610224575b600080fd5b34801561007357600080fd5b506100d860048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610251565b005b3480156100e657600080fd5b5061014b60048036038101908080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061060d565b005b34801561015957600080fd5b5061022260048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610760565b005b34801561023057600080fd5b5061024f600480360381019080803590602001909291905050506109d0565b005b6000806000848152602001908152602001600020600701541415610304577fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610609565b6040516020018060000190506040516020818303038152906040526040518082805190602001908083835b602083101515610354578051825260208201915060208101905060208303925061032f565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008084815260200190815260200160002060050160405160200180828054600181600116156101000203166002900480156103f85780601f106103d65761010080835404028352918201916103f8565b820191906000526020600020905b8154815290600101906020018083116103e4575b50509150506040516020818303038152906040526040518082805190602001908083835b602083101515610441578051825260208201915060208101905060208303925061041c565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191614151561050e577fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610609565b806000808481526020019081526020016000206005019080519060200190610537929190610c7d565b506040805190810160405280600981526020017fe5b7b2e5a484e7908600000000000000000000000000000000000000000000008152506000808481526020019081526020016000206008019080519060200190610596929190610c7d565b507fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d60006040518082815260200180602001828103825260128152602001807fe794b3e8afb7e5a484e79086e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050565b60008060008481526020019081526020016000206007015414156106c0577f7bbb3d3a81e64c2a1b5542060d9b3b78cca79948eb59fd2a0b8af375180ff5067fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a161075c565b8060008084815260200190815260200160002060080190805190602001906106e9929190610c7d565b507f7bbb3d3a81e64c2a1b5542060d9b3b78cca79948eb59fd2a0b8af375180ff50660006040518082815260200180602001828103825260128152602001807fe69bb4e696b0e78ab6e68081e68890e58a9f00000000000000000000000000008152506020019250505060405180910390a15b5050565b600080600088815260200190815260200160002060070154141515610814577f9a884513e0d49fa396c7df9756d8cdf372dae25e03ad21423644129e034e89737fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a16109c8565b61012060405190810160405280878152602001868152602001858152602001848152602001838152602001602060405190810160405280600081525081526020018281526020014281526020016040805190810160405280600f81526020017fe5b7b2e58f91e8b5b7e794b3e8afb700000000000000000000000000000000008152508152506000808881526020019081526020016000206000820151816000015560208201518160010190805190602001906108d2929190610cfd565b5060408201518160020155606082015181600301556080820151816004019080519060200190610903929190610cfd565b5060a0820151816005019080519060200190610920929190610cfd565b5060c0820151816006015560e08201518160070155610100820151816008019080519060200190610952929190610cfd565b509050507f9a884513e0d49fa396c7df9756d8cdf372dae25e03ad21423644129e034e8973600060405180828152602001806020018281038252600c8152602001807fe794b3e8afb7e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b505050505050565b600080600083815260200190815260200160002090507f79444d00a7a14216ec1642671c24d14f5babb54eb5c11fc162084f5dc540a75d816001018260020154836003015484600401856005018660060154876007015488600801604051808060200189815260200188815260200180602001806020018781526020018681526020018060200185810385528d818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610ad85780601f10610aad57610100808354040283529160200191610ad8565b820191906000526020600020905b815481529060010190602001808311610abb57829003601f168201915b505085810384528a818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610b5b5780601f10610b3057610100808354040283529160200191610b5b565b820191906000526020600020905b815481529060010190602001808311610b3e57829003601f168201915b5050858103835289818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610bde5780601f10610bb357610100808354040283529160200191610bde565b820191906000526020600020905b815481529060010190602001808311610bc157829003601f168201915b5050858103825286818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610c615780601f10610c3657610100808354040283529160200191610c61565b820191906000526020600020905b815481529060010190602001808311610c4457829003601f168201915b50509c5050505050505050505050505060405180910390a15050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610cbe57805160ff1916838001178555610cec565b82800160010185558215610cec579182015b82811115610ceb578251825591602001919060010190610cd0565b5b509050610cf99190610d7d565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d3e57805160ff1916838001178555610d6c565b82800160010185558215610d6c579182015b82811115610d6b578251825591602001919060010190610d50565b5b509050610d799190610d7d565b5090565b610d9f91905b80821115610d9b576000816000905550600101610d83565b5090565b905600a165627a7a72305820537557519d0539b473dbb3ca562e67abaa1643e61e51a1ff3a6f3ac25448163b0029";
    ;

    @Deprecated
    protected InsuranceApplication(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected InsuranceApplication(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected InsuranceApplication(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected InsuranceApplication(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static InsuranceApplication load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new InsuranceApplication(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static InsuranceApplication load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new InsuranceApplication(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static InsuranceApplication load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new InsuranceApplication(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static InsuranceApplication load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new InsuranceApplication(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<InsuranceApplication> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(InsuranceApplication.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<InsuranceApplication> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(InsuranceApplication.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<InsuranceApplication> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(InsuranceApplication.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<InsuranceApplication> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(InsuranceApplication.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<TransactionReceipt> receiveApplication(BigInteger id, String receiverSignature) {
        final Function function = new Function(
                FUNC_RECEIVEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(receiverSignature)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void receiveApplication(BigInteger id, String receiverSignature, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RECEIVEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(receiverSignature)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> updateApplicationStatus(BigInteger id, String status) {
        final Function function = new Function(
                FUNC_UPDATEAPPLICATIONSTATUS,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(status)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateApplicationStatus(BigInteger id, String status, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEAPPLICATIONSTATUS,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(status)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> createApplication(BigInteger id, String content, BigInteger sponsor, BigInteger receiver, String sponsorSignature, BigInteger applicationType) {
        final Function function = new Function(
                FUNC_CREATEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(content),
                        new Uint256(sponsor),
                        new Uint256(receiver),
                        new Utf8String(sponsorSignature),
                        new Int256(applicationType)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createApplication(BigInteger id, String content, BigInteger sponsor, BigInteger receiver, String sponsorSignature, BigInteger applicationType, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(content),
                        new Uint256(sponsor),
                        new Uint256(receiver),
                        new Utf8String(sponsorSignature),
                        new Int256(applicationType)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> getApplication(BigInteger id) {
        final Function function = new Function(
                FUNC_GETAPPLICATION,
                Arrays.<Type>asList(new Int256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getApplication(BigInteger id, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETAPPLICATION,
                Arrays.<Type>asList(new Int256(id)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<CreateApplicationEventEventResponse> getCreateApplicationEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEAPPLICATIONEVENT_EVENT, transactionReceipt);
        ArrayList<CreateApplicationEventEventResponse> responses = new ArrayList<CreateApplicationEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CreateApplicationEventEventResponse typedResponse = new CreateApplicationEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateApplicationEventEventResponse> createApplicationEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, CreateApplicationEventEventResponse>() {
            @Override
            public CreateApplicationEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CREATEAPPLICATIONEVENT_EVENT, log);
                CreateApplicationEventEventResponse typedResponse = new CreateApplicationEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreateApplicationEventEventResponse> createApplicationEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATEAPPLICATIONEVENT_EVENT));
        return createApplicationEventEventFlowable(filter);
    }

    public List<ReceiveApplicationEventEventResponse> getReceiveApplicationEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEAPPLICATIONEVENT_EVENT, transactionReceipt);
        ArrayList<ReceiveApplicationEventEventResponse> responses = new ArrayList<ReceiveApplicationEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ReceiveApplicationEventEventResponse typedResponse = new ReceiveApplicationEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReceiveApplicationEventEventResponse> receiveApplicationEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, ReceiveApplicationEventEventResponse>() {
            @Override
            public ReceiveApplicationEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEAPPLICATIONEVENT_EVENT, log);
                ReceiveApplicationEventEventResponse typedResponse = new ReceiveApplicationEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReceiveApplicationEventEventResponse> receiveApplicationEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEAPPLICATIONEVENT_EVENT));
        return receiveApplicationEventEventFlowable(filter);
    }

    public List<UpdateApplicationStatusEventEventResponse> getUpdateApplicationStatusEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEAPPLICATIONSTATUSEVENT_EVENT, transactionReceipt);
        ArrayList<UpdateApplicationStatusEventEventResponse> responses = new ArrayList<UpdateApplicationStatusEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UpdateApplicationStatusEventEventResponse typedResponse = new UpdateApplicationStatusEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateApplicationStatusEventEventResponse> updateApplicationStatusEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateApplicationStatusEventEventResponse>() {
            @Override
            public UpdateApplicationStatusEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEAPPLICATIONSTATUSEVENT_EVENT, log);
                UpdateApplicationStatusEventEventResponse typedResponse = new UpdateApplicationStatusEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateApplicationStatusEventEventResponse> updateApplicationStatusEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEAPPLICATIONSTATUSEVENT_EVENT));
        return updateApplicationStatusEventEventFlowable(filter);
    }

    public List<GetApplicationCallbackEventResponse> getGetApplicationCallbackEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(GETAPPLICATIONCALLBACK_EVENT, transactionReceipt);
        ArrayList<GetApplicationCallbackEventResponse> responses = new ArrayList<GetApplicationCallbackEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            GetApplicationCallbackEventResponse typedResponse = new GetApplicationCallbackEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.content = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.sponsor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.receiver = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sponsorSignature = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.receiverSignature = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.applicationType = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.status = (String) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GetApplicationCallbackEventResponse> getApplicationCallbackEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, GetApplicationCallbackEventResponse>() {
            @Override
            public GetApplicationCallbackEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(GETAPPLICATIONCALLBACK_EVENT, log);
                GetApplicationCallbackEventResponse typedResponse = new GetApplicationCallbackEventResponse();
                typedResponse.log = log;
                typedResponse.content = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.sponsor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.receiver = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.sponsorSignature = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.receiverSignature = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.applicationType = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                typedResponse.status = (String) eventValues.getNonIndexedValues().get(7).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GetApplicationCallbackEventResponse> getApplicationCallbackEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GETAPPLICATIONCALLBACK_EVENT));
        return getApplicationCallbackEventFlowable(filter);
    }

    public static class CreateApplicationEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class ReceiveApplicationEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class UpdateApplicationStatusEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class GetApplicationCallbackEventResponse {
        public Log log;

        public String content;

        public BigInteger sponsor;

        public BigInteger receiver;

        public String sponsorSignature;

        public String receiverSignature;

        public BigInteger applicationType;

        public BigInteger startTime;

        public String status;
    }
}
