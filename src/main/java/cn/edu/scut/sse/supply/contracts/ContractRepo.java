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
    public static final Event LAUNCHCONTRACTEVENT_EVENT = new Event("launchContractEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));

    public static final String FUNC_UPDATECONTRACTSTATUS = "updateContractStatus";

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
    private static final String BINARY = "608060405234801561001057600080fd5b5061137b806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806347dedb4714610067578063684f261a146100e4578063d9e64c21146101b1578063dac3c7691461022e575b600080fd5b34801561007357600080fd5b506100e26004803603810190808035906020019092919080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061025b565b005b3480156100f057600080fd5b506101af60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105f4565b005b3480156101bd57600080fd5b5061022c6004803603810190808035906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610852565b005b34801561023a57600080fd5b5061025960048036038101908080359060200190929190505050610f29565b005b600080600085815260200190815260200160002060060154141561030e577f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a16105ef565b6000808481526020019081526020016000206002015482141580156103485750600080848152602001908152602001600020600301548214155b156103e2577f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe99d9ee6b395e79a84e8afb7e6b18200000000000000000000000000000000008152506020019250505060405180910390a16105ef565b80600080858152602001908152602001600020600701908051906020019061040b92919061122a565b50600080848152602001908152602001600020600201548214156104ff576020604051908101604052806000815250600080858152602001908152602001600020600501908051906020019061046292919061122a565b507f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f60006040518082815260200180602001828103825260308152602001807fe59088e5908ce78ab6e68081e5b7b2e4bfaee694b9efbc8ce7ad89e5be85e5af81526020017fb9e696b9e7adbee5908de7a1aee8aea4000000000000000000000000000000008152506040019250505060405180910390a16105ef565b600080848152602001908152602001600020600301548214156105ee576020604051908101604052806000815250600080858152602001908152602001600020600401908051906020019061055592919061122a565b507f91673064bb2e635c90009f220e9db32ea85980f9278ad87cc59284e8f88e439f60006040518082815260200180602001828103825260308152602001807fe59088e5908ce78ab6e68081e5b7b2e4bfaee694b9efbc8ce7ad89e5be85e5af81526020017fb9e696b9e7adbee5908de7a1aee8aea4000000000000000000000000000000008152506040019250505060405180910390a15b5b505050565b6000806000878152602001908152602001600020600601541415156106a8577f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a5105307fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a161084b565b61010060405190810160405280868152602001858152602001848152602001838152602001828152602001602060405190810160405280600081525081526020014281526020016040805190810160405280600681526020017fe6ada3e5b8b800000000000000000000000000000000000000000000000000008152508152506000808781526020019081526020016000206000820151816000015560208201518160010190805190602001906107609291906112aa565b50604082015181600201556060820151816003015560808201518160040190805190602001906107919291906112aa565b5060a08201518160050190805190602001906107ae9291906112aa565b5060c0820151816006015560e08201518160070190805190602001906107d59291906112aa565b509050507f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a51053060006040518082815260200180602001828103825260128152602001807fe59088e5908ce58f91e8b5b7e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050505050565b6000806000858152602001908152602001600020600601541415610905577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610f24565b81600080858152602001908152602001600020600201541415610bcc576040516020018060000190506040516020818303038152906040526040518082805190602001908083835b602083101515610972578051825260208201915060208101905060208303925061094d565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166000808581526020019081526020016000206004016040516020018082805460018160011615610100020316600290048015610a165780601f106109f4576101008083540402835291820191610a16565b820191906000526020600020905b815481529060010190602001808311610a02575b50509150506040516020818303038152906040526040518082805190602001908083835b602083101515610a5f5780518252602082019150602081019050602083039250610a3a565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141515610b2c577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610f24565b806000808581526020019081526020016000206004019080519060200190610b5592919061122a565b507f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a60960006040518082815260200180602001828103825260128152602001807fe59088e5908ce7adbee5908de5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a1610f24565b81600080858152602001908152602001600020600301541415610e93576040516020018060000190506040516020818303038152906040526040518082805190602001908083835b602083101515610c395780518252602082019150602081019050602083039250610c14565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166000808581526020019081526020016000206005016040516020018082805460018160011615610100020316600290048015610cdd5780601f10610cbb576101008083540402835291820191610cdd565b820191906000526020600020905b815481529060010190602001808311610cc9575b50509150506040516020818303038152906040526040518082805190602001908083835b602083101515610d265780518252602082019150602081019050602083039250610d01565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141515610df3577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610f24565b806000808581526020019081526020016000206005019080519060200190610e1c92919061122a565b507f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a60960006040518082815260200180602001828103825260128152602001807fe59088e5908ce7adbee5908de5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a1610f24565b7f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd60405180828152602001806020018281038252600c8152602001807fe99d9ee6b395e8afb7e6b18200000000000000000000000000000000000000008152506020019250505060405180910390a15b505050565b7ff18bd1a56617b1a9e1adbdfd0cf7f1d931950ffdcc6cf8e773b338e2addd8e85600080838152602001908152602001600020600101600080848152602001908152602001600020600201546000808581526020019081526020016000206003015460008086815260200190815260200160002060040160008087815260200190815260200160002060050160008088815260200190815260200160002060060154600080898152602001908152602001600020600701604051808060200188815260200187815260200180602001806020018681526020018060200185810385528c8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156110875780601f1061105c57610100808354040283529160200191611087565b820191906000526020600020905b81548152906001019060200180831161106a57829003601f168201915b505085810384528981815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561110a5780601f106110df5761010080835404028352916020019161110a565b820191906000526020600020905b8154815290600101906020018083116110ed57829003601f168201915b505085810383528881815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561118d5780601f106111625761010080835404028352916020019161118d565b820191906000526020600020905b81548152906001019060200180831161117057829003601f168201915b50508581038252868181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156112105780601f106111e557610100808354040283529160200191611210565b820191906000526020600020905b8154815290600101906020018083116111f357829003601f168201915b50509b50505050505050505050505060405180910390a150565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061126b57805160ff1916838001178555611299565b82800160010185558215611299579182015b8281111561129857825182559160200191906001019061127d565b5b5090506112a6919061132a565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112eb57805160ff1916838001178555611319565b82800160010185558215611319579182015b828111156113185782518255916020019190600101906112fd565b5b509050611326919061132a565b5090565b61134c91905b80821115611348576000816000905550600101611330565b5090565b905600a165627a7a72305820d27e42a68c13f8f17827a38f86fe2172adbecaff0983bcba578f5c8013ffb2f90029";
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

    public RemoteCall<TransactionReceipt> receiveContract(BigInteger id, BigInteger code, String signature) {
        final Function function = new Function(
                FUNC_RECEIVECONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Uint256(code),
                        new Utf8String(signature)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void receiveContract(BigInteger id, BigInteger code, String signature, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RECEIVECONTRACT,
                Arrays.<Type>asList(new Int256(id),
                        new Uint256(code),
                        new Utf8String(signature)),
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

    public Flowable<GetContractCallbackEventResponse> getContractCallbackEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GETCONTRACTCALLBACK_EVENT));
        return getContractCallbackEventFlowable(filter);
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
