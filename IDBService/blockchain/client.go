package blockchain

import (
	"context"
	"time"
	"fmt"
	"math/big"
	"os"
	"strings"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/rpc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/common/hexutil"
	"github.com/ccamaleon5/CredentialServer/util"
	store "github.com/lacchain/cross-border-management/IDBService/blockchain/contracts"
	"github.com/lacchain/cross-border-management/IDBService/errors"
	"github.com/lacchain/cross-border-management/IDBService/model"
	log "github.com/lacchain/cross-border-management/IDBService/utils"
)

const CrossBorderABI = "[{\"inputs\":[{\"internalType\":\"uint8\",\"name\":\"_blocksFrequency\",\"type\":\"uint8\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"contractDeployed\",\"type\":\"address\"}],\"name\":\"ContractDeployed\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"blockNumber\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"gasUsed\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"gasLimit\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"gasUsedLastBlocks\",\"type\":\"uint256\"}],\"name\":\"GasUsedByTransaction\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"}],\"name\":\"Relayed\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"relay\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes4\",\"name\":\"selector\",\"type\":\"bytes4\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"charge\",\"type\":\"uint256\"}],\"name\":\"TransactionRelayed\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"newNode\",\"type\":\"address\"}],\"name\":\"addNode\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint16\",\"name\":\"index\",\"type\":\"uint16\"}],\"name\":\"deleteNode\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_byteCode\",\"type\":\"bytes\"},{\"internalType\":\"uint256\",\"name\":\"gasLimit\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"nonce\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"signature\",\"type\":\"bytes\"}],\"name\":\"deployMetaTx\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"},{\"internalType\":\"address\",\"name\":\"deployedAddress\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getGasLimit\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getGasUsedLastBlocks\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getNodes\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"}],\"name\":\"getNonce\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"encodedFunction\",\"type\":\"bytes\"},{\"internalType\":\"uint256\",\"name\":\"gasLimit\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"nonce\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"signature\",\"type\":\"bytes\"}],\"name\":\"relayMetaTx\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint8\",\"name\":\"_blocksFrequency\",\"type\":\"uint8\"}],\"name\":\"setBlocksFrequency\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"newGasUsed\",\"type\":\"uint256\"}],\"name\":\"setGasUsedLastBlocks\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"

//Client to manage Connection to Ethereum
type Client struct {
	client *ethclient.Client
}

//Connect to Ethereum
func (ec *Client) Connect(nodeURL string) error {
	client, err := ethclient.Dial(nodeURL)
	if err != nil {
		msg := fmt.Sprintf("Can't connect to node %s", nodeURL)
		err = errors.FailedConnection.Wrapf(err,msg)
		return err
	}

	log.GeneralLogger.Println("Connected to Ethereum Node:", nodeURL)
	ec.client = client
	return nil
}

//Close ethereum connection
func (ec *Client) Close() {
	ec.client.Close()
}

//ConfigTransaction from ethereum address contract
func (ec *Client) ConfigTransaction(keyStorePath,keystorepass string) (*bind.TransactOpts, error) {
	keystore, err := os.Open(keyStorePath)
	defer keystore.Close()
    if err != nil {
		msg := fmt.Sprintf("could not load keystore from location %s",keyStorePath)
		err = errors.FailedKeystore.Wrapf(err,msg)
		return nil,err
    }
	
	auth, err := bind.NewTransactor(keystore, keystorepass)
	if err != nil {
		msg := fmt.Sprintf("can't open the keystore")
		err = errors.FailedKeystore.Wrapf(err,msg)
		return nil, err
	}

	nonce, err := ec.client.PendingNonceAt(context.Background(), auth.From)
	if err != nil {
		msg := fmt.Sprintf("can't get pending nonce for:",auth.From)
		err = errors.FailedConfigTransaction.Wrapf(err,msg)
		return nil, err
	}

	gasPrice, err := ec.client.SuggestGasPrice(context.Background())
	if err != nil {
		msg := fmt.Sprintf("can't get gas price suggested")
		err = errors.FailedConfigTransaction.Wrapf(err,msg)
		return nil, err
	}

	auth.Nonce = big.NewInt(int64(nonce))
	auth.Value = big.NewInt(0)      // in wei
	auth.GasLimit = uint64(4000700) // in units
	auth.GasPrice = gasPrice

	gas := util.CalcGasCost(auth.GasLimit, gasPrice)

	log.GeneralLogger.Printf("OptionsTransaction=[From:0x%x,nonce:%d,gasPrice:%s,gasLimit:%d,gas:%s", auth.From,nonce,gasPrice,auth.GasLimit,gas)

	return auth, nil
}

//SetFeeTransfer into blockchain
func (ec *Client) SetFeeTransfer(contractAddress common.Address, options *bind.TransactOpts, operationId string, fee *big.Int, tasa *big.Int) (error, *common.Hash) {
	contract, err := store.NewCrossBorderPayment(contractAddress, ec.client)
	if err != nil {
		msg := fmt.Sprintf("can't instance CrossBoarderPayment contract %s", contractAddress)
		err = errors.FailedContract.Wrapf(err,msg)
		return err, nil
	}

	log.GeneralLogger.Println("CrossBoarderPayment Contract instanced")

	log.GeneralLogger.Println("operationId:", operationId)
	log.GeneralLogger.Println("fee:", fee)
	log.GeneralLogger.Println("tasa:", tasa)

	tx, err := contract.SetFeeRate(options, operationId, fee, tasa)
	if err != nil {
		msg := fmt.Sprintf("failed executing contract")
		err = errors.BadTransaction.Wrapf(err,msg)
		return err, nil
	}

	if (len(tx.Hash()) == 0){
		msg := fmt.Sprintf("failed execute transaction")
		err = errors.FailedTransaction.Wrapf(err,msg)
		return err, nil
	}
	log.GeneralLogger.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}

//ExecuteTransfer into blockchain
func (ec *Client) ExecuteTransfer(contractAddress common.Address, options *bind.TransactOpts, operationId string) (error, *common.Hash) {
	contract, err := store.NewCrossBorderPayment(contractAddress, ec.client)
	if err != nil {
		msg := fmt.Sprintf("can't instance CrossBoarderPayment contract %s", contractAddress)
		err = errors.FailedContract.Wrapf(err,msg)
		return err, nil
	}

	log.GeneralLogger.Println("CrossBoarderPayment Contract instanced")
	log.GeneralLogger.Println("operationId:", operationId)

	tx, err := contract.ApproveTransfer(options, operationId)
	if err != nil {
		msg := fmt.Sprintf("failed executing contract")
		err = errors.BadTransaction.Wrapf(err,msg)
		return err, nil
	}

	if (len(tx.Hash()) == 0){
		msg := fmt.Sprintf("failed execute transaction")
		err = errors.FailedTransaction.Wrapf(err,msg)
		return err, nil
	}
	log.GeneralLogger.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}


//SendDollarsToExchange into blockchain
func (ec *Client) SendDollarsToExchange(contractAddress common.Address, options *bind.TransactOpts, operationId string) (error, *common.Hash) {
	contract, err := store.NewCrossBorderPayment(contractAddress, ec.client)
	if err != nil {
		msg := fmt.Sprintf("can't instance CrossBoarderPayment contract %s", contractAddress)
		err = errors.FailedContract.Wrapf(err,msg)
		return err, nil
	}

	log.GeneralLogger.Println("CrossBoarderPayment Contract instanced")
	log.GeneralLogger.Println("operationId:", operationId)

	tx, err := contract.SendDollarsToExchange(options, operationId)
	if err != nil {
		msg := fmt.Sprintf("failed executing contract")
		err = errors.BadTransaction.Wrapf(err,msg)
		return err, nil
	}

	if (len(tx.Hash()) == 0){
		msg := fmt.Sprintf("failed execute transaction")
		err = errors.FailedTransaction.Wrapf(err,msg)
		return err, nil
	}
	log.GeneralLogger.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}

//ChangeDollarsToPesos into blockchain
func (ec *Client) ChangeDollarsToPesos(contractAddress common.Address, options *bind.TransactOpts, operationId string) (error, *common.Hash) {
	contract, err := store.NewCrossBorderPayment(contractAddress, ec.client)
	if err != nil {
		msg := fmt.Sprintf("can't instance CrossBoarderPayment contract %s", contractAddress)
		err = errors.FailedContract.Wrapf(err,msg)
		return err, nil
	}

	log.GeneralLogger.Println("CrossBoarderPayment Contract instanced")
	log.GeneralLogger.Println("operationId:", operationId)

	tx, err := contract.ChangeDollarsToPesos(options, operationId)
	if err != nil {
		msg := fmt.Sprintf("failed executing contract")
		err = errors.BadTransaction.Wrapf(err,msg)
		return err, nil
	}

	if (len(tx.Hash()) == 0){
		msg := fmt.Sprintf("failed execute transaction")
		err = errors.FailedTransaction.Wrapf(err,msg)
		return err, nil
	}
	log.GeneralLogger.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}

//SendPesosToRecepient into blockchain
func (ec *Client) SendPesosToRecepient(contractAddress common.Address, options *bind.TransactOpts, operationId string) (error, *common.Hash) {
	contract, err := store.NewCrossBorderPayment(contractAddress, ec.client)
	if err != nil {
		msg := fmt.Sprintf("can't instance CrossBoarderPayment contract %s", contractAddress)
		err = errors.FailedContract.Wrapf(err,msg)
		return err, nil
	}

	log.GeneralLogger.Println("CrossBoarderPayment Contract instanced")
	log.GeneralLogger.Println("operationId:", operationId)

	tx, err := contract.SendPesosToRecepient(options, operationId)
	if err != nil {
		msg := fmt.Sprintf("failed executing contract")
		err = errors.BadTransaction.Wrapf(err,msg)
		return err, nil
	}

	if (len(tx.Hash()) == 0){
		msg := fmt.Sprintf("failed execute transaction")
		err = errors.FailedTransaction.Wrapf(err,msg)
		return err, nil
	}
	log.GeneralLogger.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}

func (ec *Client) SimulateTransaction(nodeURL string, from common.Address, tx *types.Transaction) bool {
	client, err := rpc.DialHTTP(nodeURL)
    if err != nil {
        log.GeneralLogger.Fatal(err)
    }
    defer client.Close()
	
	var result string
	err = client.Call(&result,"eth_call",createCallMsgFromTransaction(from, tx), "latest")
	if err != nil {
		log.GeneralLogger.Fatal("Cannot not get revert reason: " + err.Error())
		return false
	}
	log.GeneralLogger.Println("result:",result)
	value := new(big.Int)
	
	hexResult := strings.Replace(result, "0x", "", -1)
	value.SetString(hexResult, 16)
	log.GeneralLogger.Println("value:",value)
	if value.Int64() == 0 {
		log.GeneralLogger.Println("no error message or out of gas")
		return false
	}
	return true
}

func createCallMsgFromTransaction(from common.Address, tx *types.Transaction) model.CallRequest {
	
	log.GeneralLogger.Println("Call From:",from.Hex())
	log.GeneralLogger.Println("Call To:",tx.To().Hex())
	log.GeneralLogger.Println("Call Data:",hexutil.Encode(tx.Data()))
	log.GeneralLogger.Println("Call GasLimit:",hexutil.EncodeUint64(tx.Gas()))

	return model.CallRequest{
		From: from.Hex(),
		To: tx.To().Hex(),
		Gas: hexutil.EncodeUint64(tx.Gas()),
		Data: hexutil.Encode(tx.Data()),
	}
}

//GenerateTransaction ...
func (ec *Client)GenerateTransaction(nonce, gasLimitTx uint64, crossBorderAddress common.Address, functionContract, operationID string, fee, rate *big.Int) (*types.Transaction){
	testabi, err := abi.JSON(strings.NewReader(CrossBorderABI))
	if err != nil{
		log.GeneralLogger.Println("Error decoding ABI")
	}

	var bytesData []byte

	if (functionContract=="setFeeRate"){
		bytesData, _ = testabi.Pack(functionContract,operationID,fee,rate)
	}else{
		bytesData, _ = testabi.Pack(functionContract,operationID)
	}
		tx := types.NewTransaction(nonce, crossBorderAddress, big.NewInt(0), gasLimitTx, big.NewInt(0), bytesData)
	return tx
}

//GetTransactionReceipt ...
func (ec *Client) GetTransactionReceipt(transactionHash common.Hash)(*big.Int, string, error){
	receipt, err := ec.client.TransactionReceipt(context.Background(), transactionHash)
        if err != nil {
            log.GeneralLogger.Fatal(err)
		}
		
	log.GeneralLogger.Println("Status:",receipt.Status)
	log.GeneralLogger.Println("BlockNumber:",receipt.BlockNumber)

	block, err := ec.client.BlockByNumber(context.Background(), receipt.BlockNumber)
    if err != nil {
        log.GeneralLogger.Fatal(err)
	}
	
	log.GeneralLogger.Println("block time:",block.Time())

	ts := time.Unix(int64(block.Time()),0).UTC()

	return receipt.BlockNumber, ts.String(), nil
}
