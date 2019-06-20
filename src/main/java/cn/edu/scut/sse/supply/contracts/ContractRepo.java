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
public class ContractRepo extends Contract {
    public static final String FUNC_UPDATECONTRACTSTATUS = "updateContractStatus";
    public static final Event LAUNCHCONTRACTEVENT_EVENT = new Event("launchContractEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));

    public static final String FUNC_LAUNCHCONTRACT = "launchContract";

    public static final String FUNC_RECEIVECONTRACT = "receiveContract";

    public static final String FUNC_GETCONTRACT = "getContract";
    public static final Event RECEIVECONTRACTEVENT_EVENT = new Event("receiveContractEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event UPDATECONTRACTSTATUSEVENT_EVENT = new Event("updateContractStatusEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    public static final Event GETCONTRACTCALLBACK_EVENT = new Event("getContractCallback",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;
    private static final String BINARY = "608060405234801561001057600080fd5b50610ff7806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806347dedb4714610067578063684f261a146100e4578063bad04a76146101b1578063dac3c76914610224575b600080fd5b34801561007357600080fd5b506100e26004803603810190808035906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610251565b005b3480156100f057600080fd5b506101af60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105ea565b005b3480156101bd57600080fd5b5061022260048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610848565b005b34801561023057600080fd5b5061024f60048036038101908080359060200190929190505050610ba5565b005b6000806000858152602001908152602001600020600601541415610304577f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a16105e5565b60008084815260200190815260200160002060020154821415801561033e5750600080848152602001908152602001600020600301548214155b156103d8577f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe99d9ee6b395e79a84e8afb7e6b18200000000000000000000000000000000008152506020019250505060405180910390a16105e5565b806000808581526020019081526020016000206007019080519060200190610401929190610ea6565b50600080848152602001908152602001600020600201548214156104f55760206040519081016040528060008152506000808581526020019081526020016000206005019080519060200190610458929190610ea6565b507f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f60006040518082815260200180602001828103825260308152602001807fe59088e5908ce78ab6e68081e5b7b2e4bfaee694b9efbc8ce7ad89e5be85e5af81526020017fb9e696b9e7adbee5908de7a1aee8aea4000000000000000000000000000000008152506040019250505060405180910390a16105e5565b600080848152602001908152602001600020600301548214156105e4576020604051908101604052806000815250600080858152602001908152602001600020600401908051906020019061054b929190610ea6565b507f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f60006040518082815260200180602001828103825260308152602001807fe59088e5908ce78ab6e68081e5b7b2e4bfaee694b9efbc8ce7ad89e5be85e5af81526020017fb9e696b9e7adbee5908de7a1aee8aea4000000000000000000000000000000008152506040019250505060405180910390a15b5b505050565b60008060008781526020019081526020016000206006015414151561069e577f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a5105307fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610841565b61010060405190810160405280868152602001858152602001848152602001838152602001828152602001602060405190810160405280600081525081526020014281526020016040805190810160405280600681526020017fe6ada3e5b8b80000000000000000000000000000000000000000000000000000815250815250600080878152602001908152602001600020600082015181600001556020820151816001019080519060200190610756929190610f26565b5060408201518160020155606082015181600301556080820151816004019080519060200190610787929190610f26565b5060a08201518160050190805190602001906107a4929190610f26565b5060c0820151816006015560e08201518160070190805190602001906107cb929190610f26565b509050507f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a51053060006040518082815260200180602001828103825260128152602001807fe59088e5908ce58f91e8b5b7e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050505050565b60008060008481526020019081526020016000206006015414156108fb577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610ba1565b6040516020018060000190506040516020818303038152906040526040518082805190602001908083835b60208310151561094b5780518252602082019150602081019050602083039250610926565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008084815260200190815260200160002060050160405160200180828054600181600116156101000203166002900480156109ef5780601f106109cd5761010080835404028352918201916109ef565b820191906000526020600020905b8154815290600101906020018083116109db575b50509150506040516020818303038152906040526040518082805190602001908083835b602083101515610a385780518252602082019150602081019050602083039250610a13565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141515610b05577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610ba1565b806000808481526020019081526020016000206005019080519060200190610b2e929190610ea6565b507f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a60960006040518082815260200180602001828103825260128152602001807fe59088e5908ce68ea5e58f97e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050565b7ff18bd1a56617b1a9e1adbdfd0cf7f1d931950ffdcc6cf8e773b338e2addd8e85600080838152602001908152602001600020600101600080848152602001908152602001600020600201546000808581526020019081526020016000206003015460008086815260200190815260200160002060040160008087815260200190815260200160002060050160008088815260200190815260200160002060060154600080898152602001908152602001600020600701604051808060200188815260200187815260200180602001806020018681526020018060200185810385528c818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610d035780601f10610cd857610100808354040283529160200191610d03565b820191906000526020600020905b815481529060010190602001808311610ce657829003601f168201915b5050858103845289818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610d865780601f10610d5b57610100808354040283529160200191610d86565b820191906000526020600020905b815481529060010190602001808311610d6957829003601f168201915b5050858103835288818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610e095780601f10610dde57610100808354040283529160200191610e09565b820191906000526020600020905b815481529060010190602001808311610dec57829003601f168201915b5050858103825286818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610e8c5780601f10610e6157610100808354040283529160200191610e8c565b820191906000526020600020905b815481529060010190602001808311610e6f57829003601f168201915b50509b50505050505050505050505060405180910390a150565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ee757805160ff1916838001178555610f15565b82800160010185558215610f15579182015b82811115610f14578251825591602001919060010190610ef9565b5b509050610f229190610fa6565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f6757805160ff1916838001178555610f95565b82800160010185558215610f95579182015b82811115610f94578251825591602001919060010190610f79565b5b509050610fa29190610fa6565b5090565b610fc891905b80821115610fc4576000816000905550600101610fac565b5090565b905600a165627a7a723058203b291458598b425abe0e9c8602d587c47880c87d9e8f8caba6290e3e34ea7b3f0029";
    ;

    @Deprecated
    protected ContractRepo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractRepo(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractRepo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractRepo(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ContractRepo load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractRepo(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractRepo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractRepo(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractRepo load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContractRepo(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractRepo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContractRepo(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractRepo> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractRepo.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractRepo> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractRepo.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractRepo> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractRepo.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractRepo> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractRepo.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<LaunchContractEventEventResponse> getLaunchContractEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LAUNCHCONTRACTEVENT_EVENT, transactionReceipt);
        ArrayList<LaunchContractEventEventResponse> responses = new ArrayList<LaunchContractEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LaunchContractEventEventResponse typedResponse = new LaunchContractEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LaunchContractEventEventResponse> launchContractEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, LaunchContractEventEventResponse>() {
            @Override
            public LaunchContractEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LAUNCHCONTRACTEVENT_EVENT, log);
                LaunchContractEventEventResponse typedResponse = new LaunchContractEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LaunchContractEventEventResponse> launchContractEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LAUNCHCONTRACTEVENT_EVENT));
        return launchContractEventEventFlowable(filter);
    }

    public List<ReceiveContractEventEventResponse> getReceiveContractEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVECONTRACTEVENT_EVENT, transactionReceipt);
        ArrayList<ReceiveContractEventEventResponse> responses = new ArrayList<ReceiveContractEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ReceiveContractEventEventResponse typedResponse = new ReceiveContractEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReceiveContractEventEventResponse> receiveContractEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, ReceiveContractEventEventResponse>() {
            @Override
            public ReceiveContractEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVECONTRACTEVENT_EVENT, log);
                ReceiveContractEventEventResponse typedResponse = new ReceiveContractEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReceiveContractEventEventResponse> receiveContractEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVECONTRACTEVENT_EVENT));
        return receiveContractEventEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> updateContractStatus(BigInteger id, BigInteger code, String status) {
        final Function function = new Function(
                FUNC_UPDATECONTRACTSTATUS,
                Arrays.<Type>asList(new Int256(id),
                        new Uint256(code),
                        new Utf8String(status)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateContractStatus(BigInteger id, BigInteger code, String status, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATECONTRACTSTATUS,
                Arrays.<Type>asList(new Int256(id),
                        new Uint256(code),
                        new Utf8String(status)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> launchContract(BigInteger id, String hash, BigInteger sponsor, BigInteger receiver, String sponsorSignature) {
        final Function function = new Function(
                FUNC_LAUNCHCONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(hash),
                        new Uint256(sponsor),
                        new Uint256(receiver),
                        new Utf8String(sponsorSignature)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void launchContract(BigInteger id, String hash, BigInteger sponsor, BigInteger receiver, String sponsorSignature, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_LAUNCHCONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(hash),
                        new Uint256(sponsor),
                        new Uint256(receiver),
                        new Utf8String(sponsorSignature)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> receiveContract(BigInteger id, String receiverSignature) {
        final Function function = new Function(
                FUNC_RECEIVECONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(receiverSignature)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public Flowable<GetContractCallbackEventResponse> getContractCallbackEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GETCONTRACTCALLBACK_EVENT));
        return getContractCallbackEventFlowable(filter);
    }

    public void receiveContract(BigInteger id, String receiverSignature, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RECEIVECONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Utf8String(receiverSignature)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> getContract(BigInteger id) {
        final Function function = new Function(
                FUNC_GETCONTRACT,
                Arrays.<Type>asList(new Int256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getContract(BigInteger id, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETCONTRACT,
                Arrays.<Type>asList(new Int256(id)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<UpdateContractStatusEventEventResponse> getUpdateContractStatusEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATECONTRACTSTATUSEVENT_EVENT, transactionReceipt);
        ArrayList<UpdateContractStatusEventEventResponse> responses = new ArrayList<UpdateContractStatusEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UpdateContractStatusEventEventResponse typedResponse = new UpdateContractStatusEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateContractStatusEventEventResponse> updateContractStatusEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateContractStatusEventEventResponse>() {
            @Override
            public UpdateContractStatusEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATECONTRACTSTATUSEVENT_EVENT, log);
                UpdateContractStatusEventEventResponse typedResponse = new UpdateContractStatusEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateContractStatusEventEventResponse> updateContractStatusEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATECONTRACTSTATUSEVENT_EVENT));
        return updateContractStatusEventEventFlowable(filter);
    }

    public List<GetContractCallbackEventResponse> getGetContractCallbackEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(GETCONTRACTCALLBACK_EVENT, transactionReceipt);
        ArrayList<GetContractCallbackEventResponse> responses = new ArrayList<GetContractCallbackEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            GetContractCallbackEventResponse typedResponse = new GetContractCallbackEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.sponsor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.receiver = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sponsorSignature = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.receiverSignature = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.status = (String) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GetContractCallbackEventResponse> getContractCallbackEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, GetContractCallbackEventResponse>() {
            @Override
            public GetContractCallbackEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(GETCONTRACTCALLBACK_EVENT, log);
                GetContractCallbackEventResponse typedResponse = new GetContractCallbackEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.sponsor = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.receiver = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.sponsorSignature = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.receiverSignature = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.status = (String) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public static class LaunchContractEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class ReceiveContractEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class UpdateContractStatusEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class GetContractCallbackEventResponse {
        public Log log;

        public String hash;

        public BigInteger sponsor;

        public BigInteger receiver;

        public String sponsorSignature;

        public String receiverSignature;

        public BigInteger startTime;

        public String status;
    }
}
