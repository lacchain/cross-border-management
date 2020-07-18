/*
Copyright © 2020 NAME HERE <EMAIL ADDRESS>

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
	"fmt"
    "log"
	
	"github.com/ethereum/go-ethereum/crypto"
    "github.com/ethereum/go-ethereum/accounts/keystore"
	"github.com/spf13/cobra"
)

// accountCmd represents the account command
var accountCmd = &cobra.Command{
	Use:   "account",
	Short: "A brief description of your command",
	Long: `A longer description that spans multiple lines and likely contains examples
and usage of using your command. For example:

Cobra is a CLI library for Go that empowers applications.
This application is a tool to generate the needed files
to quickly create a Cobra application.`,
	Run: func(cmd *cobra.Command, args []string) {
		createAccount(args)
	},
}

func init() {
	rootCmd.AddCommand(accountCmd)
}

func createAccount(args []string) {
	if len(args) < 1 {
		fmt.Println("Not enough arguments, is necessary 2 arguments(privateKeyPath and passphrase)\nexample: idbService account /path/privateKey mysecret")
		return
	}
	
    ks := keystore.NewKeyStore("./keystore", keystore.StandardScryptN, keystore.StandardScryptP)
	
	key, err := crypto.LoadECDSA(args[0])
	if err != nil {
		log.Fatal("Error loading key:",err)
	}
	
	acct, err := ks.ImportECDSA(key, args[1])
	if err != nil {
		log.Fatal("Could not create the account:", err)
	}
	fmt.Printf("Address: {0x%x}\n", acct.Address)
}



