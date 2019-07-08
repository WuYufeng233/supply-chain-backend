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
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public class BankApplication extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611059806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063098f80441461007257806357a166f0146100e5578063bcdd270014610158578063e67aa8d71461022f578063e9a5c3ba1461025c575b600080fd5b34801561007e57600080fd5b506100e360048036038101908080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061036e565b005b3480156100f157600080fd5b5061015660048036038101908080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061072a565b005b34801561016457600080fd5b5061022d60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061087d565b005b34801561023b57600080fd5b5061025a60048036038101908080359060200190929190505050610aed565b005b34801561026857600080fd5b5061028760048036038101908080359060200190929190505050610d9a565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156102cb5780820151818401526020810190506102b0565b50505050905090810190601f1680156102f85780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610331578082015181840152602081019050610316565b50505050905090810190601f16801561035e5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b6000806000848152602001908152602001600020600701541415610421577fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610726565b6040516020018060000190506040516020818303038152906040526040518082805190602001908083835b602083101515610471578051825260208201915060208101905060208303925061044c565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008084815260200190815260200160002060050160405160200180828054600181600116156101000203166002900480156105155780601f106104f3576101008083540402835291820191610515565b820191906000526020600020905b815481529060010190602001808311610501575b50509150506040516020818303038152906040526040518082805190602001908083835b60208310151561055e5780518252602082019150602081019050602083039250610539565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191614151561062b577fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610726565b806000808481526020019081526020016000206005019080519060200190610654929190610f08565b506040805190810160405280600981526020017fe5b7b2e5a484e79086000000000000000000000000000000000000000000000081525060008084815260200190815260200160002060080190805190602001906106b3929190610f08565b507fcd27aa6445e130eb5114a2e6a2c93edb017253ccacfb4219daae34f867953e6d60006040518082815260200180602001828103825260128152602001807fe794b3e8afb7e5a484e79086e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050565b60008060008481526020019081526020016000206007015414156107dd577f7bbb3d3a81e64c2a1b5542060d9b3b78cca79948eb59fd2a0b8af375180ff5067fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610879565b806000808481526020019081526020016000206008019080519060200190610806929190610f08565b507f7bbb3d3a81e64c2a1b5542060d9b3b78cca79948eb59fd2a0b8af375180ff50660006040518082815260200180602001828103825260128152602001807fe69bb4e696b0e78ab6e68081e68890e58a9f00000000000000000000000000008152506020019250505060405180910390a15b5050565b600080600088815260200190815260200160002060070154141515610931577f9a884513e0d49fa396c7df9756d8cdf372dae25e03ad21423644129e034e89737fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe794b3e8afb7e5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610ae5565b61012060405190810160405280878152602001868152602001858152602001848152602001838152602001602060405190810160405280600081525081526020018281526020014281526020016040805190810160405280600f81526020017fe5b7b2e58f91e8b5b7e794b3e8afb700000000000000000000000000000000008152508152506000808881526020019081526020016000206000820151816000015560208201518160010190805190602001906109ef929190610f88565b5060408201518160020155606082015181600301556080820151816004019080519060200190610a20929190610f88565b5060a0820151816005019080519060200190610a3d929190610f88565b5060c0820151816006015560e08201518160070155610100820151816008019080519060200190610a6f929190610f88565b509050507f9a884513e0d49fa396c7df9756d8cdf372dae25e03ad21423644129e034e8973600060405180828152602001806020018281038252600c8152602001807fe794b3e8afb7e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b505050505050565b600080600083815260200190815260200160002090507f79444d00a7a14216ec1642671c24d14f5babb54eb5c11fc162084f5dc540a75d816001018260020154836003015484600401856005018660060154876007015488600801604051808060200189815260200188815260200180602001806020018781526020018681526020018060200185810385528d818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610bf55780601f10610bca57610100808354040283529160200191610bf5565b820191906000526020600020905b815481529060010190602001808311610bd857829003601f168201915b505085810384528a818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610c785780601f10610c4d57610100808354040283529160200191610c78565b820191906000526020600020905b815481529060010190602001808311610c5b57829003601f168201915b5050858103835289818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610cfb5780601f10610cd057610100808354040283529160200191610cfb565b820191906000526020600020905b815481529060010190602001808311610cde57829003601f168201915b5050858103825286818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610d7e5780601f10610d5357610100808354040283529160200191610d7e565b820191906000526020600020905b815481529060010190602001808311610d6157829003601f168201915b50509c5050505050505050505050505060405180910390a15050565b606080600080848152602001908152602001600020600501600080858152602001908152602001600020600801818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e5c5780601f10610e3157610100808354040283529160200191610e5c565b820191906000526020600020905b815481529060010190602001808311610e3f57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ef85780601f10610ecd57610100808354040283529160200191610ef8565b820191906000526020600020905b815481529060010190602001808311610edb57829003601f168201915b5050505050905091509150915091565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f4957805160ff1916838001178555610f77565b82800160010185558215610f77579182015b82811115610f76578251825591602001919060010190610f5b565b5b509050610f849190611008565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fc957805160ff1916838001178555610ff7565b82800160010185558215610ff7579182015b82811115610ff6578251825591602001919060010190610fdb565b5b5090506110049190611008565b5090565b61102a91905b8082111561102657600081600090555060010161100e565b5090565b905600a165627a7a72305820fbd65cdc311bdb4e5944d9e64869f38708a1637dd57a4ad61d1d0053820d01e50029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"int256\"},{\"name\":\"receiverSignature\",\"type\":\"string\"}],\"name\":\"receiveApplication\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"int256\"},{\"name\":\"status\",\"type\":\"string\"}],\"name\":\"updateApplicationStatus\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"int256\"},{\"name\":\"content\",\"type\":\"string\"},{\"name\":\"sponsor\",\"type\":\"uint256\"},{\"name\":\"receiver\",\"type\":\"uint256\"},{\"name\":\"sponsorSignature\",\"type\":\"string\"},{\"name\":\"applicationType\",\"type\":\"int256\"}],\"name\":\"createApplication\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"int256\"}],\"name\":\"getApplication\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"id\",\"type\":\"int256\"}],\"name\":\"getApplicationStatus\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"createApplicationEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"receiveApplicationEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"updateApplicationStatusEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"content\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"sponsor\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"receiver\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"sponsorSignature\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"receiverSignature\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"applicationType\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"startTime\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"status\",\"type\":\"string\"}],\"name\":\"getApplicationCallback\",\"type\":\"event\"}]";

    public static final String FUNC_RECEIVEAPPLICATION = "receiveApplication";

    public static final String FUNC_UPDATEAPPLICATIONSTATUS = "updateApplicationStatus";

    public static final String FUNC_CREATEAPPLICATION = "createApplication";

    public static final String FUNC_GETAPPLICATION = "getApplication";

    public static final String FUNC_GETAPPLICATIONSTATUS = "getApplicationStatus";

    public static final Event CREATEAPPLICATIONEVENT_EVENT = new Event("createApplicationEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

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

    @Deprecated
    protected BankApplication(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BankApplication(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BankApplication(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BankApplication(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static BankApplication load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BankApplication(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BankApplication load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BankApplication(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BankApplication load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BankApplication(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BankApplication load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BankApplication(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BankApplication> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BankApplication.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BankApplication> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BankApplication.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<BankApplication> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BankApplication.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BankApplication> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BankApplication.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

    public String receiveApplicationSeq(BigInteger id, String receiverSignature) {
        final Function function = new Function(
                FUNC_RECEIVEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(receiverSignature)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String updateApplicationStatusSeq(BigInteger id, String status) {
        final Function function = new Function(
                FUNC_UPDATEAPPLICATIONSTATUS,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(status)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String createApplicationSeq(BigInteger id, String content, BigInteger sponsor, BigInteger receiver, String sponsorSignature, BigInteger applicationType) {
        final Function function = new Function(
                FUNC_CREATEAPPLICATION,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(content),
                        new Uint256(sponsor),
                        new Uint256(receiver),
                        new Utf8String(sponsorSignature),
                        new Int256(applicationType)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String getApplicationSeq(BigInteger id) {
        final Function function = new Function(
                FUNC_GETAPPLICATION,
                Arrays.<Type>asList(new Int256(id)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple2<String, String>> getApplicationStatus(BigInteger id) {
        final Function function = new Function(FUNC_GETAPPLICATIONSTATUS,
                Arrays.<Type>asList(new Int256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue());
                    }
                });
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
