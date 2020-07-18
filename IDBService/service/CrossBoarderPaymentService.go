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
	bl "github.com/ccamaleon5/IDBService/blockchain"
	"github.com/ccamaleon5/IDBService/model"
	"github.com/ccamaleon5/IDBService/errors"
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
func (service *CrossBoarderPaymentService) SetFeeTasaTransfer(operationId string, fee *big.Int, tasa *big.Int, crossBoarderPaymentContract string) (int) {
	client := new(bl.Client)
	err := client.Connect(service.Config.Application.NodeURL)
	if err != nil {
		handleError(err)
	}
	defer client.Close()
	
	options, err := client.ConfigTransaction(service.Config.KeyStore.FeeAgent,service.Config.Passphrase.FeeAgent)
	if err != nil {
		return handleError(err)
	}
	contractAddress := common.HexToAddress(crossBoarderPaymentContract)

	err, tx := client.SetFeeTransfer(contractAddress, options, operationId, fee, tasa)
	if err != nil {
		return handleError(err)
	}

	fmt.Println("tx",tx)

	return 200
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
func (service *CrossBoarderPaymentService) SendDollarsToExchange(operationId string, crossBoarderPaymentContract string) (bool, error) {
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

	err, tx := client.SendDollarsToExchange(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return true, nil
}

//ChangeDollarsToPesos saving the hash into blockchain
func (service *CrossBoarderPaymentService) ChangeDollarsToPesos(operationId string, crossBoarderPaymentContract string) (bool, error) {
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

	err, tx := client.ChangeDollarsToPesos(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return true, nil
}

//SendPesosToRecepient saving the hash into blockchain
func (service *CrossBoarderPaymentService) SendPesosToRecepient(operationId string, crossBoarderPaymentContract string) (bool, error) {
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

	err, tx := client.SendPesosToRecepient(contractAddress,options, operationId)
	if err != nil {
		fmt.Println("Transaction wasn't sent")
	}

	fmt.Println("tx",tx)

	return true, nil
}

func handleError(err error)(int){
	errorType := errors.GetType(err)
   	switch errorType {
    	case errors.FailedConnection: 
			  log.Fatal(err.Error())
		case errors.FailedKeystore:
			  log.Fatal(err.Error())	
		case errors.FailedConfigTransaction:
			log.Println(err.Error())
			return 100  
		default: 
      		log.Println("otro error")
	   }
	  return 100
}
