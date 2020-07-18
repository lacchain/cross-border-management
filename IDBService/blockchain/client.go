package blockchain

import (
	"context"
	"time"
	"fmt"
	"log"
	"math/big"
	"os"

	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/ccamaleon5/CredentialServer/util"
	store "github.com/ccamaleon5/IDBService/blockchain/contracts"
	"github.com/ccamaleon5/IDBService/errors"
	l "github.com/ccamaleon5/IDBService/utils"
)

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

	l.GeneralLogger.Println("Connected to Ethereum Node:", nodeURL)
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

	l.GeneralLogger.Printf("OptionsTransaction=[From:0x%x,nonce:%d,gasPrice:%s,gasLimit:%d,gas:%s", auth.From,nonce,gasPrice,auth.GasLimit,gas)

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

	log.Println("CrossBoarderPayment Contract instanced")

	log.Println("operationId:", operationId)
	log.Println("fee:", fee)
	log.Println("tasa:", tasa)

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
	log.Printf("Tx sent: %s", tx.Hash().Hex())

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

	log.Println("CrossBoarderPayment Contract instanced")
	log.Println("operationId:", operationId)

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
	log.Printf("Tx sent: %s", tx.Hash().Hex())

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

	log.Println("CrossBoarderPayment Contract instanced")
	log.Println("operationId:", operationId)

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
	log.Printf("Tx sent: %s", tx.Hash().Hex())

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

	log.Println("CrossBoarderPayment Contract instanced")
	log.Println("operationId:", operationId)

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
	log.Printf("Tx sent: %s", tx.Hash().Hex())

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

	log.Println("CrossBoarderPayment Contract instanced")
	log.Println("operationId:", operationId)

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
	log.Printf("Tx sent: %s", tx.Hash().Hex())

	transactionHash := tx.Hash()

	return nil, &transactionHash
}

//GetTransactionReceipt ...
func (ec *Client) GetTransactionReceipt(transactionHash common.Hash)(*big.Int, string, error){
	receipt, err := ec.client.TransactionReceipt(context.Background(), transactionHash)
        if err != nil {
            log.Fatal(err)
		}
		
	log.Println("Status:",receipt.Status)
	log.Println("BlockNumber:",receipt.BlockNumber)

	block, err := ec.client.BlockByNumber(context.Background(), receipt.BlockNumber)
    if err != nil {
        log.Fatal(err)
	}
	
	log.Println("block time:",block.Time())

	ts := time.Unix(int64(block.Time()),0).UTC()

	return receipt.BlockNumber, ts.String(), nil
}
