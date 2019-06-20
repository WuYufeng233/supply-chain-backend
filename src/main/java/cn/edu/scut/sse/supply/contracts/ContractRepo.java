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
    public static final String FUNC_LAUNCHCONTRACT = "launchContract";
    public static final String FUNC_RECEIVECONTRACT = "receiveContract";
    public static final String FUNC_GETCONTRACT = "getContract";
    public static final Event LAUNCHCONTRACTEVENT_EVENT = new Event("launchContractEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    public static final Event RECEIVECONTRACTEVENT_EVENT = new Event("receiveContractEvent",
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
            }, new TypeReference<Int256>() {
            }));
    ;
    private static final String BINARY = "608060405234801561001057600080fd5b50610b29806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063684f261a1461005c578063bad04a7614610129578063dac3c7691461019c575b600080fd5b34801561006857600080fd5b5061012760048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506101c9565b005b34801561013557600080fd5b5061019a60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506103e0565b005b3480156101a857600080fd5b506101c760048036038101908080359060200190929190505050610758565b005b60008060008781526020019081526020016000206006015414151561027d577f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a5105307fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a16103d9565b610100604051908101604052808681526020018581526020018481526020018381526020018281526020016020604051908101604052806000815250815260200142815260200160028152506000808781526020019081526020016000206000820151816000015560208201518160010190805190602001906103019291906109d8565b50604082015181600201556060820151816003015560808201518160040190805190602001906103329291906109d8565b5060a082015181600501908051906020019061034f9291906109d8565b5060c0820151816006015560e082015181600701559050507f904427f97d501cad566c2b2b93ef36a4260cdb69d50079c5e740c9939a51053060006040518082815260200180602001828103825260128152602001807fe59088e5908ce58f91e8b5b7e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050505050565b6000806000848152602001908152602001600020600601541415610493577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60405180828152602001806020018281038252600f8152602001807fe59088e5908ce4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610754565b6040516020018060000190506040516020818303038152906040526040518082805190602001908083835b6020831015156104e357805182526020820191506020810190506020830392506104be565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008084815260200190815260200160002060050160405160200180828054600181600116156101000203166002900480156105875780601f10610565576101008083540402835291820191610587565b820191906000526020600020905b815481529060010190602001808311610573575b50509150506040516020818303038152906040526040518082805190602001908083835b6020831015156105d057805182526020820191506020810190506020830392506105ab565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191614151561069d577f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a6097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe59088e5908ce5b7b2e7adbee5908d00000000000000000000000000000000008152506020019250505060405180910390a1610754565b8060008084815260200190815260200160002060050190805190602001906106c6929190610a58565b506001600080848152602001908152602001600020600701819055507f31118501a05011f40121148d35cc4e29d452ecfe4ac15048ae05bcac0ac8a60960006040518082815260200180602001828103825260128152602001807fe59088e5908ce68ea5e58f97e5ae8ce6889000000000000000000000000000008152506020019250505060405180910390a15b5050565b7f1246fa3eca19ad8192c1a8d3b4fab3dc4f514a229cda798dbc7eef171e160f70600080838152602001908152602001600020600101600080848152602001908152602001600020600201546000808581526020019081526020016000206003015460008086815260200190815260200160002060040160008087815260200190815260200160002060050160008088815260200190815260200160002060060154600080898152602001908152602001600020600701546040518080602001888152602001878152602001806020018060200186815260200185815260200184810384528b8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156108b95780601f1061088e576101008083540402835291602001916108b9565b820191906000526020600020905b81548152906001019060200180831161089c57829003601f168201915b505084810383528881815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561093c5780601f106109115761010080835404028352916020019161093c565b820191906000526020600020905b81548152906001019060200180831161091f57829003601f168201915b50508481038252878181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156109bf5780601f10610994576101008083540402835291602001916109bf565b820191906000526020600020905b8154815290600101906020018083116109a257829003601f168201915b50509a505050505050505050505060405180910390a150565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a1957805160ff1916838001178555610a47565b82800160010185558215610a47579182015b82811115610a46578251825591602001919060010190610a2b565b5b509050610a549190610ad8565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a9957805160ff1916838001178555610ac7565b82800160010185558215610ac7579182015b82811115610ac6578251825591602001919060010190610aab565b5b509050610ad49190610ad8565b5090565b610afa91905b80821115610af6576000816000905550600101610ade565b5090565b905600a165627a7a7230582004e90ec540d8a463e604efdd94b54e69566d4850d1ea84ddf9100ecb5188a2c90029";
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
            typedResponse.status = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
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
                typedResponse.status = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
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

    public static class GetContractCallbackEventResponse {
        public Log log;

        public String hash;

        public BigInteger sponsor;

        public BigInteger receiver;

        public String sponsorSignature;

        public String receiverSignature;

        public BigInteger startTime;

        public BigInteger status;
    }
}
