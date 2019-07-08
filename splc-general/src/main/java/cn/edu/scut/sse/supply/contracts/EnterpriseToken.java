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
    public static final String BINARY = "608060405234801561001057600080fd5b5033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506111cc806100616000396000f300608060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063075461721461009e57806317b9ebec146100f557806338d075611461012c5780633f4dca591461019f578063ae6aa3f014610212578063ce404d2d14610249578063da304e4d1461028a578063e4b50cb8146102cb578063f61c55f01461030c575b600080fd5b3480156100aa57600080fd5b506100b3610343565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561010157600080fd5b5061012a6004803603810190808035906020019092919080359060200190929190505050610369565b005b34801561013857600080fd5b5061019d60048036038101908080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506104e8565b005b3480156101ab57600080fd5b5061021060048036038101908080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061075c565b005b34801561021e57600080fd5b506102476004803603810190808035906020019092919080359060200190929190505050610a35565b005b34801561025557600080fd5b50610288600480360381019080803590602001909291908035906020019092919080359060200190929190505050610bab565b005b34801561029657600080fd5b506102b560048036038101908080359060200190929190505050610e08565b6040518082815260200191505060405180910390f35b3480156102d757600080fd5b506102f660048036038101908080359060200190929190505050610e28565b6040518082815260200191505060405180910390f35b34801561031857600080fd5b506103416004803603810190808035906020019092919080359060200190929190505050610e48565b005b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008090505b60008054905081101561044e578260008281548110151561038c57fe5b906000526020600020015414156103a25761044e565b600160008054905003811415610443577f50443d9bb04b7c026fad4f3fba886565496bc4e65d1480e74bbfaa57fced0b047ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd60405180828152602001806020018281038252600f8152602001807fe4bc81e4b89ae4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a15b80600101905061036f565b8160016000858152602001908152602001600020600201600082825401925050819055507f86a1fd4739ec1f40aa2adb89fd005fe52344286ad4b7341989109e936ff6c8f460006040518082815260200180602001828103825260118152602001807f546f6b656ee5a29ee58aa0e68890e58a9f0000000000000000000000000000008152506020019250505060405180910390a1505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156105d6577f7f9f773ff51d466612a006b24cd852e6a2e1fcdd725798564ab720db37b519857ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff660405180828152602001806020018281038252600c8152602001807fe69d83e99990e4b88de8b6b300000000000000000000000000000000000000008152506020019250505060405180910390a1610757565b600090505b6000805490508110156106ba57826000828154811015156105f857fe5b9060005260206000200154141561060e576106ba565b6001600080549050038114156106af577f7f9f773ff51d466612a006b24cd852e6a2e1fcdd725798564ab720db37b519857ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd60405180828152602001806020018281038252600f8152602001807fe4bc81e4b89ae4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a15b8060010190506105db565b816001600085815260200190815260200160002060010190805190602001906106e492919061107b565b507f7f9f773ff51d466612a006b24cd852e6a2e1fcdd725798564ab720db37b51985600060405180828152602001806020018281038252600c8152602001807fe4bfaee694b9e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561084a577f6ed1237213d19af243be645115aca9751349fa743c4ccffe1bc5a40601356bd37ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff660405180828152602001806020018281038252600c8152602001807fe69d83e99990e4b88de8b6b300000000000000000000000000000000000000008152506020019250505060405180910390a1610a30565b600090505b60008054905081101561091d578260008281548110151561086c57fe5b90600052602060002001541415610912577f6ed1237213d19af243be645115aca9751349fa743c4ccffe1bc5a40601356bd37ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe60405180828152602001806020018281038252600f8152602001807fe4bc81e4b89ae5b7b2e5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a1610a30565b80600101905061084f565b6080604051908101604052808481526020018381526020016000815260200160008152506001600085815260200190815260200160002060008201518160000155602082015181600101908051906020019061097a9291906110fb565b50604082015181600201556060820151816003015590505060008390806001815401808255809150509060018203906000526020600020016000909192909190915055507f6ed1237213d19af243be645115aca9751349fa743c4ccffe1bc5a40601356bd3600060405180828152602001806020018281038252600c8152602001807fe5889be5bbbae68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b505050565b60008090505b600080549050811015610b1a5782600082815481101515610a5857fe5b90600052602060002001541415610a6e57610b1a565b600160008054905003811415610b0f577f50443d9bb04b7c026fad4f3fba886565496bc4e65d1480e74bbfaa57fced0b047ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd60405180828152602001806020018281038252600f8152602001807fe4bc81e4b89ae4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a15b806001019050610a3b565b8160016000858152602001908152602001600020600301819055507f50443d9bb04b7c026fad4f3fba886565496bc4e65d1480e74bbfaa57fced0b04600060405180828152602001806020018281038252600c8152602001807fe68e88e4bfa1e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a1505050565b60008160016000868152602001908152602001600020600201541015610c60577f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb347ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb6040518082815260200180602001828103825260118152602001807f546f6b656ee4bd99e9a29de4b88de8b6b30000000000000000000000000000008152506020019250505060405180910390a1610e02565b600090505b600080549050811015610d485782600082815481101515610c8257fe5b90600052602060002001541415610c9857610d48565b600160008054905003811415610d3d577f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb347fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6040518082815260200180602001828103825260188152602001807fe4b88de5ad98e59ca8e79a84e79baee6a087e585ace58fb800000000000000008152506020019250505060405180910390a1610e02565b806001019050610c65565b8160016000868152602001908152602001600020600201600082825403925050819055508160016000858152602001908152602001600020600201600082825401925050819055507f3dcc8f190b1cbb86df63d878384c5cdbb42de99fc788231b42749fb6e56ffb34600060405180828152602001806020018281038252600c8152602001807fe694afe4bb98e68890e58a9f00000000000000000000000000000000000000008152506020019250505060405180910390a15b50505050565b600060016000838152602001908152602001600020600301549050919050565b600060016000838152602001908152602001600020600201549050919050565b60008090505b600080549050811015610f2d5782600082815481101515610e6b57fe5b90600052602060002001541415610e8157610f2d565b600160008054905003811415610f22577f50443d9bb04b7c026fad4f3fba886565496bc4e65d1480e74bbfaa57fced0b047ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd60405180828152602001806020018281038252600f8152602001807fe4bc81e4b89ae4b88de5ad98e59ca800000000000000000000000000000000008152506020019250505060405180910390a15b806001019050610e4e565b8160016000858152602001908152602001600020600201541015610fe0577f5fb1f8a10137232332b1119892c7d5a849be56c5422de50fcbdbaf25edfceeed7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb6040518082815260200180602001828103825260118152602001807f546f6b656ee4bd99e9a29de4b88de8b6b30000000000000000000000000000008152506020019250505060405180910390a1611076565b8160016000858152602001908152602001600020600201600082825403925050819055507f5fb1f8a10137232332b1119892c7d5a849be56c5422de50fcbdbaf25edfceeed60006040518082815260200180602001828103825260118152602001807f546f6b656ee5878fe5b091e68890e58a9f0000000000000000000000000000008152506020019250505060405180910390a15b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110bc57805160ff19168380011785556110ea565b828001600101855582156110ea579182015b828111156110e95782518255916020019190600101906110ce565b5b5090506110f7919061117b565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061113c57805160ff191683800117855561116a565b8280016001018555821561116a579182015b8281111561116957825182559160200191906001019061114e565b5b509050611177919061117b565b5090565b61119d91905b80821115611199576000816000905550600101611181565b5090565b905600a165627a7a72305820fcfcf18aa14f7768fbd33d43195be0776d2247e87b29d3542636c35a3a2867bb0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"minter\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"val\",\"type\":\"uint256\"}],\"name\":\"addToken\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"updateEnterprise\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"createEnterprise\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"credit\",\"type\":\"uint256\"}],\"name\":\"setCredit\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"source\",\"type\":\"uint256\"},{\"name\":\"target\",\"type\":\"uint256\"},{\"name\":\"val\",\"type\":\"uint256\"}],\"name\":\"payToken\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"}],\"name\":\"getCredit\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"}],\"name\":\"getToken\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"val\",\"type\":\"uint256\"}],\"name\":\"subToken\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"createEnterpriseEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"updateEnterpriseEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"setCreditEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"addTokenEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"subTokenEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"code\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"payTokenEvent\",\"type\":\"event\"}]";

    public static final String FUNC_MINTER = "minter";

    public static final String FUNC_ADDTOKEN = "addToken";

    public static final String FUNC_UPDATEENTERPRISE = "updateEnterprise";

    public static final String FUNC_CREATEENTERPRISE = "createEnterprise";

    public static final String FUNC_SETCREDIT = "setCredit";

    public static final String FUNC_PAYTOKEN = "payToken";

    public static final String FUNC_GETCREDIT = "getCredit";

    public static final String FUNC_GETTOKEN = "getToken";

    public static final String FUNC_SUBTOKEN = "subToken";

    public static final Event CREATEENTERPRISEEVENT_EVENT = new Event("createEnterpriseEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

    public static final Event UPDATEENTERPRISEEVENT_EVENT = new Event("updateEnterpriseEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

    public static final Event SETCREDITEVENT_EVENT = new Event("setCreditEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {
            }, new TypeReference<Utf8String>() {
            }));
    ;

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

    public String addTokenSeq(BigInteger code, BigInteger val) {
        final Function function = new Function(
                FUNC_ADDTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> updateEnterprise(BigInteger code, String name) {
        final Function function = new Function(
                FUNC_UPDATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateEnterprise(BigInteger code, String name, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateEnterpriseSeq(BigInteger code, String name) {
        final Function function = new Function(
                FUNC_UPDATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String createEnterpriseSeq(BigInteger code, String name) {
        final Function function = new Function(
                FUNC_CREATEENTERPRISE,
                Arrays.<Type>asList(new Uint256(code),
                        new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> setCredit(BigInteger code, BigInteger credit) {
        final Function function = new Function(
                FUNC_SETCREDIT,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(credit)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setCredit(BigInteger code, BigInteger credit, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETCREDIT,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(credit)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setCreditSeq(BigInteger code, BigInteger credit) {
        final Function function = new Function(
                FUNC_SETCREDIT,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(credit)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String payTokenSeq(BigInteger source, BigInteger target, BigInteger val) {
        final Function function = new Function(
                FUNC_PAYTOKEN,
                Arrays.<Type>asList(new Uint256(source),
                        new Uint256(target),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> getCredit(BigInteger code) {
        final Function function = new Function(FUNC_GETCREDIT,
                Arrays.<Type>asList(new Uint256(code)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public String subTokenSeq(BigInteger code, BigInteger val) {
        final Function function = new Function(
                FUNC_SUBTOKEN,
                Arrays.<Type>asList(new Uint256(code),
                        new Uint256(val)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public List<UpdateEnterpriseEventEventResponse> getUpdateEnterpriseEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEENTERPRISEEVENT_EVENT, transactionReceipt);
        ArrayList<UpdateEnterpriseEventEventResponse> responses = new ArrayList<UpdateEnterpriseEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UpdateEnterpriseEventEventResponse typedResponse = new UpdateEnterpriseEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateEnterpriseEventEventResponse> updateEnterpriseEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateEnterpriseEventEventResponse>() {
            @Override
            public UpdateEnterpriseEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEENTERPRISEEVENT_EVENT, log);
                UpdateEnterpriseEventEventResponse typedResponse = new UpdateEnterpriseEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateEnterpriseEventEventResponse> updateEnterpriseEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEENTERPRISEEVENT_EVENT));
        return updateEnterpriseEventEventFlowable(filter);
    }

    public List<SetCreditEventEventResponse> getSetCreditEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SETCREDITEVENT_EVENT, transactionReceipt);
        ArrayList<SetCreditEventEventResponse> responses = new ArrayList<SetCreditEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SetCreditEventEventResponse typedResponse = new SetCreditEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SetCreditEventEventResponse> setCreditEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, SetCreditEventEventResponse>() {
            @Override
            public SetCreditEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SETCREDITEVENT_EVENT, log);
                SetCreditEventEventResponse typedResponse = new SetCreditEventEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SetCreditEventEventResponse> setCreditEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETCREDITEVENT_EVENT));
        return setCreditEventEventFlowable(filter);
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

    public static class UpdateEnterpriseEventEventResponse {
        public Log log;

        public BigInteger code;

        public String msg;
    }

    public static class SetCreditEventEventResponse {
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
