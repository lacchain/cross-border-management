/*
Copyright © 2020 Adrian Pareja <adriancc5.5@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package cmd

import (
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
	"fmt"
	"log"
	"os"
	"net/http"
	"math/big"
	"encoding/json"

	"github.com/ccamaleon5/IDBService/model"
	"github.com/ccamaleon5/IDBService/service"
)

var config *model.Config
var crossBoarderPaymentService *service.CrossBoarderPaymentService

// startCmd represents the start command
var startCmd = &cobra.Command{
	Use:   "start",
	Short: "start IDB Service",
	Long: `start IDB Service. For example:
			idbService start  ->  start idbService`,  
	Run: func(cmd *cobra.Command, args []string) {
		start(args)
	},
}

func init() {
	rootCmd.AddCommand(startCmd)
}

func start(args []string){
	config = getConfigFromFile()

	crossBoarderPaymentService = new(service.CrossBoarderPaymentService)
	crossBoarderPaymentService.Init(config)
	
	setupRoutes()

	fmt.Println("listening events... !!")

//	event := model.Event{}
//    json.Unmarshal(m.Value, &event)
}

func setFeeRate(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	fmt.Println("Body:", r.Body)

	var request model.Request

	err := json.NewDecoder(r.Body).Decode(&request)
	if err != nil {
		fmt.Println("Error Decoding Json RPC")
		fmt.Println(err)
		return
	}

	fmt.Println("operationId:",request.OperationId)
	fmt.Println("operationId:",request.Fee)
	fmt.Println("operationId:",request.Rate)
	fee := new(big.Int).SetInt64(int64(request.Fee * 10000))
	rate := new(big.Int).SetInt64(int64(request.Rate * 10000))
	code := crossBoarderPaymentService.SetFeeTasaTransfer(request.OperationId, fee, rate, config.Application.ContractAddress)
	if code == 100{
		log.Println("Reintentar envio de transacción")
	}else{
		log.Println("Success")
	}

	w.Write([]byte("{'success':12}"))
}

func executeTransfer(w http.ResponseWriter, r *http.Request){
	fmt.Println("Processing a new Event:")
	fmt.Println("operationId:",r)
	crossBoarderPaymentService.ExecuteTransfer("12345", config.Application.ContractAddress)
}

func sendDollarsToExchange(w http.ResponseWriter, r *http.Request){
	fmt.Println("Processing a new Event:")
	fmt.Println("Body:", r.Body)

	var request model.Request

	err := json.NewDecoder(r.Body).Decode(&request)
	if err != nil {
		fmt.Println("Error Decoding Json RPC")
		fmt.Println(err)
		return
	}

	fmt.Println("operationId:",request.OperationId)
	crossBoarderPaymentService.SendDollarsToExchange(request.OperationId, config.Application.ContractAddress)
}

func changeDollarsToPesos(w http.ResponseWriter, r *http.Request){
	fmt.Println("Processing a new Event:")
	fmt.Println("Body:", r.Body)

	var request model.Request

	err := json.NewDecoder(r.Body).Decode(&request)
	if err != nil {
		fmt.Println("Error Decoding Json RPC")
		fmt.Println(err)
		return
	}

	fmt.Println("operationId:",request.OperationId)
	crossBoarderPaymentService.ChangeDollarsToPesos(request.OperationId, config.Application.ContractAddress)
}

func sendPesosToRecepient(w http.ResponseWriter, r *http.Request){
	fmt.Println("Processing a new Event:")
	fmt.Println("Body:", r.Body)

	var request model.Request

	err := json.NewDecoder(r.Body).Decode(&request)
	if err != nil {
		fmt.Println("Error Decoding Json RPC")
		fmt.Println(err)
		return
	}

	fmt.Println("operationId:",request.OperationId)
	crossBoarderPaymentService.SendPesosToRecepient(request.OperationId, config.Application.ContractAddress)
}

func getConfigFromFile()(*model.Config){
	v := viper.New()
	v.SetConfigName("config")
	v.AddConfigPath(".")
	if err := v.ReadInConfig(); err != nil {
		fmt.Printf("couldn't load config: %s", err)
		os.Exit(1)
	}
	var c model.Config
	if err := v.Unmarshal(&c); err != nil {
		fmt.Printf("couldn't read config: %s", err)
	}
	fmt.Printf("smartContract=%s FeeAgentKey=%s\n", c.Application.ContractAddress, c.KeyStore.FeeAgent)
	return &c
}

func setupRoutes() {
	fmt.Println("IDBService Started")
	http.HandleFunc("/setFeeRate", setFeeRate)
	http.HandleFunc("/executeTransfer", executeTransfer)
	http.HandleFunc("/sendDollarsToExchange", sendDollarsToExchange)
	http.HandleFunc("/changeDollarsToPesos", changeDollarsToPesos)
	http.HandleFunc("/sendPesosToRecepient", sendPesosToRecepient)
	http.ListenAndServe(":9000", nil)
}
