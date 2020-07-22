/*
	CrossBoarderPayment Service
	version 0.9
	author: Adrian Pareja Abarca
	email: adriancc5.5@gmail.com
*/

package service

import (
	"log"
	"fmt"
	"math/big"
	"github.com/ethereum/go-ethereum/common"
	bl "github.com/lacchain/cross-border-management/IDBService/blockchain"
	"github.com/lacchain/cross-border-management/IDBService/model"
	"github.com/lacchain/cross-border-management/IDBService/errors"
)

//CrossBoarderPaymentService is the main service
type CrossBoarderPaymentService struct {
	// The service's configuration
	Config *model.Config
}

//Init configuration parameters
func (service *CrossBoarderPaymentService) Init(_config *model.Config){
	service.Config = _config
}

//SetFeeTasaTransfer saving the hash into blockchain
func (service *CrossBoarderPaymentService) SetFeeTasaTransfer(operationId string, fee *big.Int, tasa *big.Int, crossBoarderPaymentContract string) (int, *common.Hash) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		handleError(err)
	}
	defer client.Close()
	
	options, err := client.ConfigTransaction(service.Config.KeyStore.FeeAgent,service.Config.Passphrase.FeeAgent)
	if err != nil {
		return handleError(err), nil
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	if(!client.SimulateTransaction(service.Config.Application.NodeURL,options.From,client.GenerateTransaction(options.Nonce.Uint64(), options.GasLimit, contractAddress, "setFeeRate", operationId, fee, tasa))){
		log.Println("Transaction will fail, then is rejected")
		return 500, nil
	}

	err, tx := client.SetFeeTransfer(contractAddress, options, operationId, fee, tasa)
	if err != nil {
		return handleError(err), nil
	}

	fmt.Println("tx",tx)

	return 200,tx
}

//ExecuteTransfer saving the hash into blockchain
func (service *CrossBoarderPaymentService) ExecuteTransfer(operationId string, crossBoarderPaymentContract string) (bool, error) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		return false, err
	}
	defer client.Close()

	if err != nil {
		log.Fatal("Error loading KeyStore:",err)
	}

	options, err := client.ConfigTransaction(service.Config.KeyStore.OperatorAgent,service.Config.Passphrase.OperatorAgent)
	if err != nil {
		return false, err
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	err, tx := client.ExecuteTransfer(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return true, nil
}

//SendDollarsToExchange saving the hash into blockchain
func (service *CrossBoarderPaymentService) SendDollarsToExchange(operationId string, crossBoarderPaymentContract string) (int, *common.Hash) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		return 503, nil
	}
	defer client.Close()

	if err != nil {
		log.Fatal("Error loading KeyStore:",err)
	}

	options, err := client.ConfigTransaction(service.Config.KeyStore.OperatorAgent,service.Config.Passphrase.OperatorAgent)
	if err != nil {
		return 504, nil
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	if(!client.SimulateTransaction(service.Config.Application.NodeURL,options.From,client.GenerateTransaction(options.Nonce.Uint64(), options.GasLimit, contractAddress, "sendDollarsToExchange", operationId, nil, nil))){
		log.Println("Transaction will fail, then is rejected")
		return 500, nil
	}

	err, tx := client.SendDollarsToExchange(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return 200,tx
}

//ChangeDollarsToPesos saving the hash into blockchain
func (service *CrossBoarderPaymentService) ChangeDollarsToPesos(operationId string, crossBoarderPaymentContract string) (int, *common.Hash) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		return 503, nil
	}
	defer client.Close()

	if err != nil {
		log.Fatal("Error loading KeyStore:",err)
	}

	options, err := client.ConfigTransaction(service.Config.KeyStore.OperatorAgent,service.Config.Passphrase.OperatorAgent)
	if err != nil {
		return 504, nil
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	if(!client.SimulateTransaction(service.Config.Application.NodeURL,options.From,client.GenerateTransaction(options.Nonce.Uint64(), options.GasLimit, contractAddress, "changeDollarsToPesos", operationId, nil, nil))){
		log.Println("Transaction will fail, then is rejected")
		return 500, nil
	}

	err, tx := client.ChangeDollarsToPesos(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return 200, tx
}

//SendPesosToRecepient saving the hash into blockchain
func (service *CrossBoarderPaymentService) SendPesosToRecepient(operationId string, crossBoarderPaymentContract string) (int, *common.Hash) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		return 503, nil
	}
	defer client.Close()

	if err != nil {
		log.Fatal("Error loading KeyStore:",err)
	}

	options, err := client.ConfigTransaction(service.Config.KeyStore.OperatorAgent,service.Config.Passphrase.OperatorAgent)
	if err != nil {
		return 504, nil
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	if(!client.SimulateTransaction(service.Config.Application.NodeURL,options.From,client.GenerateTransaction(options.Nonce.Uint64(), options.GasLimit, contractAddress, "sendPesosToRecepient", operationId, nil, nil))){
		log.Println("Transaction will fail, then is rejected")
		return 500, nil
	}

	err, tx := client.SendPesosToRecepient(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return 200, tx
}

func handleError(err error)(int){
	errorType := errors.GetType(err)
   	switch errorType {
    	case errors.FailedConnection: 
			  log.Println(err.Error())
			  return 503
		case errors.FailedKeystore:
			  log.Fatal(err.Error())	
		case errors.FailedConfigTransaction:
			log.Println(err.Error())
			return 504 
		default: 
			log.Println("server error")
			return 500
	   }
	  return 100
}
