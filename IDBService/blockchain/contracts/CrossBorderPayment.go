// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package CrossBorderPayment

import (
	"math/big"
	"strings"

	ethereum "github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/event"
)

// Reference imports to suppress errors if they are not otherwise used.
var (
	_ = big.NewInt
	_ = strings.NewReader
	_ = ethereum.NotFound
	_ = bind.Bind
	_ = common.Big1
	_ = types.BloomLookup
	_ = event.NewSubscription
)

// CrossBorderPaymentABI is the input ABI used to generate the binding from.
const CrossBorderPaymentABI = "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_feeAgent\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"eDollars\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"ePesos\",\"type\":\"address\"}],\"name\":\"CrossBorderCreated\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"Exchanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"operator\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"fee\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"rate\",\"type\":\"uint256\"}],\"name\":\"FeeRateSet\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"SendedToMarket\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"TransferApproved\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"TransferCancelled\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"TransferExecuted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"issuer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"TransferOnHold\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"orderer\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"operatorExchange\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"TransferOrdered\",\"type\":\"event\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"approveTransfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"cancelTransfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"changeDollarsToPesos\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"feeAgent\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getDollarAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getPesosAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"operatorExchange\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"orderTransfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"retrieveTransferData\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"origin\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"operatorExchange\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"fee\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"rate\",\"type\":\"uint256\"},{\"internalType\":\"enumICrossBoarderPayment.TransferStatusCode\",\"name\":\"status\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"sendDollarsToExchange\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"}],\"name\":\"sendPesosToRecepient\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"operationId\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"fee\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"rate\",\"type\":\"uint256\"}],\"name\":\"setFeeRate\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"newTransferAgent\",\"type\":\"address\"}],\"name\":\"setTransferAgent\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"transferAgent\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"

// CrossBorderPayment is an auto generated Go binding around an Ethereum contract.
type CrossBorderPayment struct {
	CrossBorderPaymentCaller     // Read-only binding to the contract
	CrossBorderPaymentTransactor // Write-only binding to the contract
	CrossBorderPaymentFilterer   // Log filterer for contract events
}

// CrossBorderPaymentCaller is an auto generated read-only Go binding around an Ethereum contract.
type CrossBorderPaymentCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CrossBorderPaymentTransactor is an auto generated write-only Go binding around an Ethereum contract.
type CrossBorderPaymentTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CrossBorderPaymentFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type CrossBorderPaymentFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CrossBorderPaymentSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type CrossBorderPaymentSession struct {
	Contract     *CrossBorderPayment // Generic contract binding to set the session for
	CallOpts     bind.CallOpts       // Call options to use throughout this session
	TransactOpts bind.TransactOpts   // Transaction auth options to use throughout this session
}

// CrossBorderPaymentCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type CrossBorderPaymentCallerSession struct {
	Contract *CrossBorderPaymentCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts             // Call options to use throughout this session
}

// CrossBorderPaymentTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type CrossBorderPaymentTransactorSession struct {
	Contract     *CrossBorderPaymentTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts             // Transaction auth options to use throughout this session
}

// CrossBorderPaymentRaw is an auto generated low-level Go binding around an Ethereum contract.
type CrossBorderPaymentRaw struct {
	Contract *CrossBorderPayment // Generic contract binding to access the raw methods on
}

// CrossBorderPaymentCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type CrossBorderPaymentCallerRaw struct {
	Contract *CrossBorderPaymentCaller // Generic read-only contract binding to access the raw methods on
}

// CrossBorderPaymentTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type CrossBorderPaymentTransactorRaw struct {
	Contract *CrossBorderPaymentTransactor // Generic write-only contract binding to access the raw methods on
}

// NewCrossBorderPayment creates a new instance of CrossBorderPayment, bound to a specific deployed contract.
func NewCrossBorderPayment(address common.Address, backend bind.ContractBackend) (*CrossBorderPayment, error) {
	contract, err := bindCrossBorderPayment(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPayment{CrossBorderPaymentCaller: CrossBorderPaymentCaller{contract: contract}, CrossBorderPaymentTransactor: CrossBorderPaymentTransactor{contract: contract}, CrossBorderPaymentFilterer: CrossBorderPaymentFilterer{contract: contract}}, nil
}

// NewCrossBorderPaymentCaller creates a new read-only instance of CrossBorderPayment, bound to a specific deployed contract.
func NewCrossBorderPaymentCaller(address common.Address, caller bind.ContractCaller) (*CrossBorderPaymentCaller, error) {
	contract, err := bindCrossBorderPayment(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentCaller{contract: contract}, nil
}

// NewCrossBorderPaymentTransactor creates a new write-only instance of CrossBorderPayment, bound to a specific deployed contract.
func NewCrossBorderPaymentTransactor(address common.Address, transactor bind.ContractTransactor) (*CrossBorderPaymentTransactor, error) {
	contract, err := bindCrossBorderPayment(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransactor{contract: contract}, nil
}

// NewCrossBorderPaymentFilterer creates a new log filterer instance of CrossBorderPayment, bound to a specific deployed contract.
func NewCrossBorderPaymentFilterer(address common.Address, filterer bind.ContractFilterer) (*CrossBorderPaymentFilterer, error) {
	contract, err := bindCrossBorderPayment(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentFilterer{contract: contract}, nil
}

// bindCrossBorderPayment binds a generic wrapper to an already deployed contract.
func bindCrossBorderPayment(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(CrossBorderPaymentABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_CrossBorderPayment *CrossBorderPaymentRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _CrossBorderPayment.Contract.CrossBorderPaymentCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_CrossBorderPayment *CrossBorderPaymentRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.CrossBorderPaymentTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_CrossBorderPayment *CrossBorderPaymentRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.CrossBorderPaymentTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_CrossBorderPayment *CrossBorderPaymentCallerRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _CrossBorderPayment.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_CrossBorderPayment *CrossBorderPaymentTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_CrossBorderPayment *CrossBorderPaymentTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.contract.Transact(opts, method, params...)
}

// FeeAgent is a free data retrieval call binding the contract method 0xb6ab3bc9.
//
// Solidity: function feeAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCaller) FeeAgent(opts *bind.CallOpts) (common.Address, error) {
	var (
		ret0 = new(common.Address)
	)
	out := ret0
	err := _CrossBorderPayment.contract.Call(opts, out, "feeAgent")
	return *ret0, err
}

// FeeAgent is a free data retrieval call binding the contract method 0xb6ab3bc9.
//
// Solidity: function feeAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentSession) FeeAgent() (common.Address, error) {
	return _CrossBorderPayment.Contract.FeeAgent(&_CrossBorderPayment.CallOpts)
}

// FeeAgent is a free data retrieval call binding the contract method 0xb6ab3bc9.
//
// Solidity: function feeAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCallerSession) FeeAgent() (common.Address, error) {
	return _CrossBorderPayment.Contract.FeeAgent(&_CrossBorderPayment.CallOpts)
}

// GetDollarAddress is a free data retrieval call binding the contract method 0x5d5f2fb9.
//
// Solidity: function getDollarAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCaller) GetDollarAddress(opts *bind.CallOpts) (common.Address, error) {
	var (
		ret0 = new(common.Address)
	)
	out := ret0
	err := _CrossBorderPayment.contract.Call(opts, out, "getDollarAddress")
	return *ret0, err
}

// GetDollarAddress is a free data retrieval call binding the contract method 0x5d5f2fb9.
//
// Solidity: function getDollarAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentSession) GetDollarAddress() (common.Address, error) {
	return _CrossBorderPayment.Contract.GetDollarAddress(&_CrossBorderPayment.CallOpts)
}

// GetDollarAddress is a free data retrieval call binding the contract method 0x5d5f2fb9.
//
// Solidity: function getDollarAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCallerSession) GetDollarAddress() (common.Address, error) {
	return _CrossBorderPayment.Contract.GetDollarAddress(&_CrossBorderPayment.CallOpts)
}

// GetPesosAddress is a free data retrieval call binding the contract method 0xeb26bdf1.
//
// Solidity: function getPesosAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCaller) GetPesosAddress(opts *bind.CallOpts) (common.Address, error) {
	var (
		ret0 = new(common.Address)
	)
	out := ret0
	err := _CrossBorderPayment.contract.Call(opts, out, "getPesosAddress")
	return *ret0, err
}

// GetPesosAddress is a free data retrieval call binding the contract method 0xeb26bdf1.
//
// Solidity: function getPesosAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentSession) GetPesosAddress() (common.Address, error) {
	return _CrossBorderPayment.Contract.GetPesosAddress(&_CrossBorderPayment.CallOpts)
}

// GetPesosAddress is a free data retrieval call binding the contract method 0xeb26bdf1.
//
// Solidity: function getPesosAddress() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCallerSession) GetPesosAddress() (common.Address, error) {
	return _CrossBorderPayment.Contract.GetPesosAddress(&_CrossBorderPayment.CallOpts)
}

// RetrieveTransferData is a free data retrieval call binding the contract method 0xe1c2df34.
//
// Solidity: function retrieveTransferData(string operationId) view returns(address origin, address to, address operatorExchange, uint256 value, uint256 fee, uint256 rate, uint8 status)
func (_CrossBorderPayment *CrossBorderPaymentCaller) RetrieveTransferData(opts *bind.CallOpts, operationId string) (struct {
	Origin           common.Address
	To               common.Address
	OperatorExchange common.Address
	Value            *big.Int
	Fee              *big.Int
	Rate             *big.Int
	Status           uint8
}, error) {
	ret := new(struct {
		Origin           common.Address
		To               common.Address
		OperatorExchange common.Address
		Value            *big.Int
		Fee              *big.Int
		Rate             *big.Int
		Status           uint8
	})
	out := ret
	err := _CrossBorderPayment.contract.Call(opts, out, "retrieveTransferData", operationId)
	return *ret, err
}

// RetrieveTransferData is a free data retrieval call binding the contract method 0xe1c2df34.
//
// Solidity: function retrieveTransferData(string operationId) view returns(address origin, address to, address operatorExchange, uint256 value, uint256 fee, uint256 rate, uint8 status)
func (_CrossBorderPayment *CrossBorderPaymentSession) RetrieveTransferData(operationId string) (struct {
	Origin           common.Address
	To               common.Address
	OperatorExchange common.Address
	Value            *big.Int
	Fee              *big.Int
	Rate             *big.Int
	Status           uint8
}, error) {
	return _CrossBorderPayment.Contract.RetrieveTransferData(&_CrossBorderPayment.CallOpts, operationId)
}

// RetrieveTransferData is a free data retrieval call binding the contract method 0xe1c2df34.
//
// Solidity: function retrieveTransferData(string operationId) view returns(address origin, address to, address operatorExchange, uint256 value, uint256 fee, uint256 rate, uint8 status)
func (_CrossBorderPayment *CrossBorderPaymentCallerSession) RetrieveTransferData(operationId string) (struct {
	Origin           common.Address
	To               common.Address
	OperatorExchange common.Address
	Value            *big.Int
	Fee              *big.Int
	Rate             *big.Int
	Status           uint8
}, error) {
	return _CrossBorderPayment.Contract.RetrieveTransferData(&_CrossBorderPayment.CallOpts, operationId)
}

// TransferAgent is a free data retrieval call binding the contract method 0x760cd8e1.
//
// Solidity: function transferAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCaller) TransferAgent(opts *bind.CallOpts) (common.Address, error) {
	var (
		ret0 = new(common.Address)
	)
	out := ret0
	err := _CrossBorderPayment.contract.Call(opts, out, "transferAgent")
	return *ret0, err
}

// TransferAgent is a free data retrieval call binding the contract method 0x760cd8e1.
//
// Solidity: function transferAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentSession) TransferAgent() (common.Address, error) {
	return _CrossBorderPayment.Contract.TransferAgent(&_CrossBorderPayment.CallOpts)
}

// TransferAgent is a free data retrieval call binding the contract method 0x760cd8e1.
//
// Solidity: function transferAgent() view returns(address)
func (_CrossBorderPayment *CrossBorderPaymentCallerSession) TransferAgent() (common.Address, error) {
	return _CrossBorderPayment.Contract.TransferAgent(&_CrossBorderPayment.CallOpts)
}

// ApproveTransfer is a paid mutator transaction binding the contract method 0xa216bbfa.
//
// Solidity: function approveTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) ApproveTransfer(opts *bind.TransactOpts, operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "approveTransfer", operationId)
}

// ApproveTransfer is a paid mutator transaction binding the contract method 0xa216bbfa.
//
// Solidity: function approveTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) ApproveTransfer(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.ApproveTransfer(&_CrossBorderPayment.TransactOpts, operationId)
}

// ApproveTransfer is a paid mutator transaction binding the contract method 0xa216bbfa.
//
// Solidity: function approveTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) ApproveTransfer(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.ApproveTransfer(&_CrossBorderPayment.TransactOpts, operationId)
}

// CancelTransfer is a paid mutator transaction binding the contract method 0xb0aefc84.
//
// Solidity: function cancelTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) CancelTransfer(opts *bind.TransactOpts, operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "cancelTransfer", operationId)
}

// CancelTransfer is a paid mutator transaction binding the contract method 0xb0aefc84.
//
// Solidity: function cancelTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) CancelTransfer(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.CancelTransfer(&_CrossBorderPayment.TransactOpts, operationId)
}

// CancelTransfer is a paid mutator transaction binding the contract method 0xb0aefc84.
//
// Solidity: function cancelTransfer(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) CancelTransfer(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.CancelTransfer(&_CrossBorderPayment.TransactOpts, operationId)
}

// ChangeDollarsToPesos is a paid mutator transaction binding the contract method 0xc6aac30c.
//
// Solidity: function changeDollarsToPesos(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) ChangeDollarsToPesos(opts *bind.TransactOpts, operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "changeDollarsToPesos", operationId)
}

// ChangeDollarsToPesos is a paid mutator transaction binding the contract method 0xc6aac30c.
//
// Solidity: function changeDollarsToPesos(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) ChangeDollarsToPesos(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.ChangeDollarsToPesos(&_CrossBorderPayment.TransactOpts, operationId)
}

// ChangeDollarsToPesos is a paid mutator transaction binding the contract method 0xc6aac30c.
//
// Solidity: function changeDollarsToPesos(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) ChangeDollarsToPesos(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.ChangeDollarsToPesos(&_CrossBorderPayment.TransactOpts, operationId)
}

// OrderTransfer is a paid mutator transaction binding the contract method 0x1c0fe26d.
//
// Solidity: function orderTransfer(string operationId, address to, address operatorExchange, uint256 value) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) OrderTransfer(opts *bind.TransactOpts, operationId string, to common.Address, operatorExchange common.Address, value *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "orderTransfer", operationId, to, operatorExchange, value)
}

// OrderTransfer is a paid mutator transaction binding the contract method 0x1c0fe26d.
//
// Solidity: function orderTransfer(string operationId, address to, address operatorExchange, uint256 value) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) OrderTransfer(operationId string, to common.Address, operatorExchange common.Address, value *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.OrderTransfer(&_CrossBorderPayment.TransactOpts, operationId, to, operatorExchange, value)
}

// OrderTransfer is a paid mutator transaction binding the contract method 0x1c0fe26d.
//
// Solidity: function orderTransfer(string operationId, address to, address operatorExchange, uint256 value) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) OrderTransfer(operationId string, to common.Address, operatorExchange common.Address, value *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.OrderTransfer(&_CrossBorderPayment.TransactOpts, operationId, to, operatorExchange, value)
}

// SendDollarsToExchange is a paid mutator transaction binding the contract method 0xe97b7091.
//
// Solidity: function sendDollarsToExchange(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) SendDollarsToExchange(opts *bind.TransactOpts, operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "sendDollarsToExchange", operationId)
}

// SendDollarsToExchange is a paid mutator transaction binding the contract method 0xe97b7091.
//
// Solidity: function sendDollarsToExchange(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) SendDollarsToExchange(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SendDollarsToExchange(&_CrossBorderPayment.TransactOpts, operationId)
}

// SendDollarsToExchange is a paid mutator transaction binding the contract method 0xe97b7091.
//
// Solidity: function sendDollarsToExchange(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) SendDollarsToExchange(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SendDollarsToExchange(&_CrossBorderPayment.TransactOpts, operationId)
}

// SendPesosToRecepient is a paid mutator transaction binding the contract method 0x41b73476.
//
// Solidity: function sendPesosToRecepient(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) SendPesosToRecepient(opts *bind.TransactOpts, operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "sendPesosToRecepient", operationId)
}

// SendPesosToRecepient is a paid mutator transaction binding the contract method 0x41b73476.
//
// Solidity: function sendPesosToRecepient(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) SendPesosToRecepient(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SendPesosToRecepient(&_CrossBorderPayment.TransactOpts, operationId)
}

// SendPesosToRecepient is a paid mutator transaction binding the contract method 0x41b73476.
//
// Solidity: function sendPesosToRecepient(string operationId) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) SendPesosToRecepient(operationId string) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SendPesosToRecepient(&_CrossBorderPayment.TransactOpts, operationId)
}

// SetFeeRate is a paid mutator transaction binding the contract method 0xbe791e4a.
//
// Solidity: function setFeeRate(string operationId, uint256 fee, uint256 rate) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) SetFeeRate(opts *bind.TransactOpts, operationId string, fee *big.Int, rate *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "setFeeRate", operationId, fee, rate)
}

// SetFeeRate is a paid mutator transaction binding the contract method 0xbe791e4a.
//
// Solidity: function setFeeRate(string operationId, uint256 fee, uint256 rate) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) SetFeeRate(operationId string, fee *big.Int, rate *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SetFeeRate(&_CrossBorderPayment.TransactOpts, operationId, fee, rate)
}

// SetFeeRate is a paid mutator transaction binding the contract method 0xbe791e4a.
//
// Solidity: function setFeeRate(string operationId, uint256 fee, uint256 rate) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) SetFeeRate(operationId string, fee *big.Int, rate *big.Int) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SetFeeRate(&_CrossBorderPayment.TransactOpts, operationId, fee, rate)
}

// SetTransferAgent is a paid mutator transaction binding the contract method 0x8c6a244c.
//
// Solidity: function setTransferAgent(address newTransferAgent) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactor) SetTransferAgent(opts *bind.TransactOpts, newTransferAgent common.Address) (*types.Transaction, error) {
	return _CrossBorderPayment.contract.Transact(opts, "setTransferAgent", newTransferAgent)
}

// SetTransferAgent is a paid mutator transaction binding the contract method 0x8c6a244c.
//
// Solidity: function setTransferAgent(address newTransferAgent) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentSession) SetTransferAgent(newTransferAgent common.Address) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SetTransferAgent(&_CrossBorderPayment.TransactOpts, newTransferAgent)
}

// SetTransferAgent is a paid mutator transaction binding the contract method 0x8c6a244c.
//
// Solidity: function setTransferAgent(address newTransferAgent) returns(bool)
func (_CrossBorderPayment *CrossBorderPaymentTransactorSession) SetTransferAgent(newTransferAgent common.Address) (*types.Transaction, error) {
	return _CrossBorderPayment.Contract.SetTransferAgent(&_CrossBorderPayment.TransactOpts, newTransferAgent)
}

// CrossBorderPaymentCrossBorderCreatedIterator is returned from FilterCrossBorderCreated and is used to iterate over the raw logs and unpacked data for CrossBorderCreated events raised by the CrossBorderPayment contract.
type CrossBorderPaymentCrossBorderCreatedIterator struct {
	Event *CrossBorderPaymentCrossBorderCreated // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentCrossBorderCreatedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentCrossBorderCreated)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentCrossBorderCreated)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentCrossBorderCreatedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentCrossBorderCreatedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentCrossBorderCreated represents a CrossBorderCreated event raised by the CrossBorderPayment contract.
type CrossBorderPaymentCrossBorderCreated struct {
	EDollars common.Address
	EPesos   common.Address
	Raw      types.Log // Blockchain specific contextual infos
}

// FilterCrossBorderCreated is a free log retrieval operation binding the contract event 0xa878a0bf992765ae440f2e1cc535d1dcfba7a3a1c69491fca54cfd17c6668cfa.
//
// Solidity: event CrossBorderCreated(address eDollars, address ePesos)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterCrossBorderCreated(opts *bind.FilterOpts) (*CrossBorderPaymentCrossBorderCreatedIterator, error) {

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "CrossBorderCreated")
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentCrossBorderCreatedIterator{contract: _CrossBorderPayment.contract, event: "CrossBorderCreated", logs: logs, sub: sub}, nil
}

// WatchCrossBorderCreated is a free log subscription operation binding the contract event 0xa878a0bf992765ae440f2e1cc535d1dcfba7a3a1c69491fca54cfd17c6668cfa.
//
// Solidity: event CrossBorderCreated(address eDollars, address ePesos)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchCrossBorderCreated(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentCrossBorderCreated) (event.Subscription, error) {

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "CrossBorderCreated")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentCrossBorderCreated)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "CrossBorderCreated", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseCrossBorderCreated is a log parse operation binding the contract event 0xa878a0bf992765ae440f2e1cc535d1dcfba7a3a1c69491fca54cfd17c6668cfa.
//
// Solidity: event CrossBorderCreated(address eDollars, address ePesos)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseCrossBorderCreated(log types.Log) (*CrossBorderPaymentCrossBorderCreated, error) {
	event := new(CrossBorderPaymentCrossBorderCreated)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "CrossBorderCreated", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentExchangedIterator is returned from FilterExchanged and is used to iterate over the raw logs and unpacked data for Exchanged events raised by the CrossBorderPayment contract.
type CrossBorderPaymentExchangedIterator struct {
	Event *CrossBorderPaymentExchanged // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentExchangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentExchanged)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentExchanged)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentExchangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentExchangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentExchanged represents a Exchanged event raised by the CrossBorderPayment contract.
type CrossBorderPaymentExchanged struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterExchanged is a free log retrieval operation binding the contract event 0x5841cdf8ed5de325d68f23a5fc071d40224d7ba42ac9cbb8280e58748e13367a.
//
// Solidity: event Exchanged(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterExchanged(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentExchangedIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "Exchanged", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentExchangedIterator{contract: _CrossBorderPayment.contract, event: "Exchanged", logs: logs, sub: sub}, nil
}

// WatchExchanged is a free log subscription operation binding the contract event 0x5841cdf8ed5de325d68f23a5fc071d40224d7ba42ac9cbb8280e58748e13367a.
//
// Solidity: event Exchanged(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchExchanged(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentExchanged, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "Exchanged", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentExchanged)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "Exchanged", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseExchanged is a log parse operation binding the contract event 0x5841cdf8ed5de325d68f23a5fc071d40224d7ba42ac9cbb8280e58748e13367a.
//
// Solidity: event Exchanged(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseExchanged(log types.Log) (*CrossBorderPaymentExchanged, error) {
	event := new(CrossBorderPaymentExchanged)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "Exchanged", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentFeeRateSetIterator is returned from FilterFeeRateSet and is used to iterate over the raw logs and unpacked data for FeeRateSet events raised by the CrossBorderPayment contract.
type CrossBorderPaymentFeeRateSetIterator struct {
	Event *CrossBorderPaymentFeeRateSet // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentFeeRateSetIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentFeeRateSet)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentFeeRateSet)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentFeeRateSetIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentFeeRateSetIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentFeeRateSet represents a FeeRateSet event raised by the CrossBorderPayment contract.
type CrossBorderPaymentFeeRateSet struct {
	Operator    common.Address
	OperationId string
	Fee         *big.Int
	Rate        *big.Int
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterFeeRateSet is a free log retrieval operation binding the contract event 0xb5989af290f7f54033914b41b52f652561137ceb5a9cf077e82117970660ce74.
//
// Solidity: event FeeRateSet(address indexed operator, string operationId, uint256 fee, uint256 rate)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterFeeRateSet(opts *bind.FilterOpts, operator []common.Address) (*CrossBorderPaymentFeeRateSetIterator, error) {

	var operatorRule []interface{}
	for _, operatorItem := range operator {
		operatorRule = append(operatorRule, operatorItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "FeeRateSet", operatorRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentFeeRateSetIterator{contract: _CrossBorderPayment.contract, event: "FeeRateSet", logs: logs, sub: sub}, nil
}

// WatchFeeRateSet is a free log subscription operation binding the contract event 0xb5989af290f7f54033914b41b52f652561137ceb5a9cf077e82117970660ce74.
//
// Solidity: event FeeRateSet(address indexed operator, string operationId, uint256 fee, uint256 rate)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchFeeRateSet(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentFeeRateSet, operator []common.Address) (event.Subscription, error) {

	var operatorRule []interface{}
	for _, operatorItem := range operator {
		operatorRule = append(operatorRule, operatorItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "FeeRateSet", operatorRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentFeeRateSet)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "FeeRateSet", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseFeeRateSet is a log parse operation binding the contract event 0xb5989af290f7f54033914b41b52f652561137ceb5a9cf077e82117970660ce74.
//
// Solidity: event FeeRateSet(address indexed operator, string operationId, uint256 fee, uint256 rate)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseFeeRateSet(log types.Log) (*CrossBorderPaymentFeeRateSet, error) {
	event := new(CrossBorderPaymentFeeRateSet)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "FeeRateSet", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentSendedToMarketIterator is returned from FilterSendedToMarket and is used to iterate over the raw logs and unpacked data for SendedToMarket events raised by the CrossBorderPayment contract.
type CrossBorderPaymentSendedToMarketIterator struct {
	Event *CrossBorderPaymentSendedToMarket // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentSendedToMarketIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentSendedToMarket)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentSendedToMarket)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentSendedToMarketIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentSendedToMarketIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentSendedToMarket represents a SendedToMarket event raised by the CrossBorderPayment contract.
type CrossBorderPaymentSendedToMarket struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterSendedToMarket is a free log retrieval operation binding the contract event 0xda3fde186ae6c314952e40e58304f26b061db4bc0b4e216cc281e72f14639cc3.
//
// Solidity: event SendedToMarket(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterSendedToMarket(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentSendedToMarketIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "SendedToMarket", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentSendedToMarketIterator{contract: _CrossBorderPayment.contract, event: "SendedToMarket", logs: logs, sub: sub}, nil
}

// WatchSendedToMarket is a free log subscription operation binding the contract event 0xda3fde186ae6c314952e40e58304f26b061db4bc0b4e216cc281e72f14639cc3.
//
// Solidity: event SendedToMarket(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchSendedToMarket(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentSendedToMarket, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "SendedToMarket", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentSendedToMarket)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "SendedToMarket", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseSendedToMarket is a log parse operation binding the contract event 0xda3fde186ae6c314952e40e58304f26b061db4bc0b4e216cc281e72f14639cc3.
//
// Solidity: event SendedToMarket(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseSendedToMarket(log types.Log) (*CrossBorderPaymentSendedToMarket, error) {
	event := new(CrossBorderPaymentSendedToMarket)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "SendedToMarket", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentTransferApprovedIterator is returned from FilterTransferApproved and is used to iterate over the raw logs and unpacked data for TransferApproved events raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferApprovedIterator struct {
	Event *CrossBorderPaymentTransferApproved // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentTransferApprovedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentTransferApproved)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentTransferApproved)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentTransferApprovedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentTransferApprovedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentTransferApproved represents a TransferApproved event raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferApproved struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterTransferApproved is a free log retrieval operation binding the contract event 0xc1b8717a0db50803474129e022233b3b5e3d5c319cba269f91a3e25055d7bbe2.
//
// Solidity: event TransferApproved(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterTransferApproved(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentTransferApprovedIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "TransferApproved", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransferApprovedIterator{contract: _CrossBorderPayment.contract, event: "TransferApproved", logs: logs, sub: sub}, nil
}

// WatchTransferApproved is a free log subscription operation binding the contract event 0xc1b8717a0db50803474129e022233b3b5e3d5c319cba269f91a3e25055d7bbe2.
//
// Solidity: event TransferApproved(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchTransferApproved(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentTransferApproved, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "TransferApproved", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentTransferApproved)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferApproved", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferApproved is a log parse operation binding the contract event 0xc1b8717a0db50803474129e022233b3b5e3d5c319cba269f91a3e25055d7bbe2.
//
// Solidity: event TransferApproved(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseTransferApproved(log types.Log) (*CrossBorderPaymentTransferApproved, error) {
	event := new(CrossBorderPaymentTransferApproved)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferApproved", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentTransferCancelledIterator is returned from FilterTransferCancelled and is used to iterate over the raw logs and unpacked data for TransferCancelled events raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferCancelledIterator struct {
	Event *CrossBorderPaymentTransferCancelled // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentTransferCancelledIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentTransferCancelled)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentTransferCancelled)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentTransferCancelledIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentTransferCancelledIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentTransferCancelled represents a TransferCancelled event raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferCancelled struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterTransferCancelled is a free log retrieval operation binding the contract event 0xf8bdc32e3214bc0618f2e71323ca0f82d89f6057787a7defb55d4ed4cfa47cdf.
//
// Solidity: event TransferCancelled(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterTransferCancelled(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentTransferCancelledIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "TransferCancelled", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransferCancelledIterator{contract: _CrossBorderPayment.contract, event: "TransferCancelled", logs: logs, sub: sub}, nil
}

// WatchTransferCancelled is a free log subscription operation binding the contract event 0xf8bdc32e3214bc0618f2e71323ca0f82d89f6057787a7defb55d4ed4cfa47cdf.
//
// Solidity: event TransferCancelled(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchTransferCancelled(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentTransferCancelled, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "TransferCancelled", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentTransferCancelled)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferCancelled", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferCancelled is a log parse operation binding the contract event 0xf8bdc32e3214bc0618f2e71323ca0f82d89f6057787a7defb55d4ed4cfa47cdf.
//
// Solidity: event TransferCancelled(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseTransferCancelled(log types.Log) (*CrossBorderPaymentTransferCancelled, error) {
	event := new(CrossBorderPaymentTransferCancelled)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferCancelled", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentTransferExecutedIterator is returned from FilterTransferExecuted and is used to iterate over the raw logs and unpacked data for TransferExecuted events raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferExecutedIterator struct {
	Event *CrossBorderPaymentTransferExecuted // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentTransferExecutedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentTransferExecuted)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentTransferExecuted)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentTransferExecutedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentTransferExecutedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentTransferExecuted represents a TransferExecuted event raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferExecuted struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterTransferExecuted is a free log retrieval operation binding the contract event 0x3ab04c4b61dfe99d86c9fc866f66c8fbdb378b8b5f05dc4954dd56e31fd76446.
//
// Solidity: event TransferExecuted(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterTransferExecuted(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentTransferExecutedIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "TransferExecuted", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransferExecutedIterator{contract: _CrossBorderPayment.contract, event: "TransferExecuted", logs: logs, sub: sub}, nil
}

// WatchTransferExecuted is a free log subscription operation binding the contract event 0x3ab04c4b61dfe99d86c9fc866f66c8fbdb378b8b5f05dc4954dd56e31fd76446.
//
// Solidity: event TransferExecuted(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchTransferExecuted(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentTransferExecuted, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "TransferExecuted", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentTransferExecuted)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferExecuted", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferExecuted is a log parse operation binding the contract event 0x3ab04c4b61dfe99d86c9fc866f66c8fbdb378b8b5f05dc4954dd56e31fd76446.
//
// Solidity: event TransferExecuted(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseTransferExecuted(log types.Log) (*CrossBorderPaymentTransferExecuted, error) {
	event := new(CrossBorderPaymentTransferExecuted)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferExecuted", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentTransferOnHoldIterator is returned from FilterTransferOnHold and is used to iterate over the raw logs and unpacked data for TransferOnHold events raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferOnHoldIterator struct {
	Event *CrossBorderPaymentTransferOnHold // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentTransferOnHoldIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentTransferOnHold)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentTransferOnHold)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentTransferOnHoldIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentTransferOnHoldIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentTransferOnHold represents a TransferOnHold event raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferOnHold struct {
	Issuer      common.Address
	OperationId string
	Raw         types.Log // Blockchain specific contextual infos
}

// FilterTransferOnHold is a free log retrieval operation binding the contract event 0xf1f18ab7df91a8e91ca3765e7c53380a6277cb6e6d881810471797a36e4bf863.
//
// Solidity: event TransferOnHold(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterTransferOnHold(opts *bind.FilterOpts, issuer []common.Address) (*CrossBorderPaymentTransferOnHoldIterator, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "TransferOnHold", issuerRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransferOnHoldIterator{contract: _CrossBorderPayment.contract, event: "TransferOnHold", logs: logs, sub: sub}, nil
}

// WatchTransferOnHold is a free log subscription operation binding the contract event 0xf1f18ab7df91a8e91ca3765e7c53380a6277cb6e6d881810471797a36e4bf863.
//
// Solidity: event TransferOnHold(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchTransferOnHold(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentTransferOnHold, issuer []common.Address) (event.Subscription, error) {

	var issuerRule []interface{}
	for _, issuerItem := range issuer {
		issuerRule = append(issuerRule, issuerItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "TransferOnHold", issuerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentTransferOnHold)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferOnHold", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferOnHold is a log parse operation binding the contract event 0xf1f18ab7df91a8e91ca3765e7c53380a6277cb6e6d881810471797a36e4bf863.
//
// Solidity: event TransferOnHold(address indexed issuer, string operationId)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseTransferOnHold(log types.Log) (*CrossBorderPaymentTransferOnHold, error) {
	event := new(CrossBorderPaymentTransferOnHold)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferOnHold", log); err != nil {
		return nil, err
	}
	return event, nil
}

// CrossBorderPaymentTransferOrderedIterator is returned from FilterTransferOrdered and is used to iterate over the raw logs and unpacked data for TransferOrdered events raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferOrderedIterator struct {
	Event *CrossBorderPaymentTransferOrdered // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CrossBorderPaymentTransferOrderedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CrossBorderPaymentTransferOrdered)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CrossBorderPaymentTransferOrdered)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CrossBorderPaymentTransferOrderedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CrossBorderPaymentTransferOrderedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CrossBorderPaymentTransferOrdered represents a TransferOrdered event raised by the CrossBorderPayment contract.
type CrossBorderPaymentTransferOrdered struct {
	Orderer          common.Address
	OperationId      string
	To               common.Address
	OperatorExchange common.Address
	Value            *big.Int
	Raw              types.Log // Blockchain specific contextual infos
}

// FilterTransferOrdered is a free log retrieval operation binding the contract event 0xc219f19cc20bf6ced3cb6ae6c3862d465a9893ea6c02e740d2c90852a366da25.
//
// Solidity: event TransferOrdered(address indexed orderer, string operationId, address indexed to, address indexed operatorExchange, uint256 value)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) FilterTransferOrdered(opts *bind.FilterOpts, orderer []common.Address, to []common.Address, operatorExchange []common.Address) (*CrossBorderPaymentTransferOrderedIterator, error) {

	var ordererRule []interface{}
	for _, ordererItem := range orderer {
		ordererRule = append(ordererRule, ordererItem)
	}

	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}
	var operatorExchangeRule []interface{}
	for _, operatorExchangeItem := range operatorExchange {
		operatorExchangeRule = append(operatorExchangeRule, operatorExchangeItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.FilterLogs(opts, "TransferOrdered", ordererRule, toRule, operatorExchangeRule)
	if err != nil {
		return nil, err
	}
	return &CrossBorderPaymentTransferOrderedIterator{contract: _CrossBorderPayment.contract, event: "TransferOrdered", logs: logs, sub: sub}, nil
}

// WatchTransferOrdered is a free log subscription operation binding the contract event 0xc219f19cc20bf6ced3cb6ae6c3862d465a9893ea6c02e740d2c90852a366da25.
//
// Solidity: event TransferOrdered(address indexed orderer, string operationId, address indexed to, address indexed operatorExchange, uint256 value)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) WatchTransferOrdered(opts *bind.WatchOpts, sink chan<- *CrossBorderPaymentTransferOrdered, orderer []common.Address, to []common.Address, operatorExchange []common.Address) (event.Subscription, error) {

	var ordererRule []interface{}
	for _, ordererItem := range orderer {
		ordererRule = append(ordererRule, ordererItem)
	}

	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}
	var operatorExchangeRule []interface{}
	for _, operatorExchangeItem := range operatorExchange {
		operatorExchangeRule = append(operatorExchangeRule, operatorExchangeItem)
	}

	logs, sub, err := _CrossBorderPayment.contract.WatchLogs(opts, "TransferOrdered", ordererRule, toRule, operatorExchangeRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CrossBorderPaymentTransferOrdered)
				if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferOrdered", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferOrdered is a log parse operation binding the contract event 0xc219f19cc20bf6ced3cb6ae6c3862d465a9893ea6c02e740d2c90852a366da25.
//
// Solidity: event TransferOrdered(address indexed orderer, string operationId, address indexed to, address indexed operatorExchange, uint256 value)
func (_CrossBorderPayment *CrossBorderPaymentFilterer) ParseTransferOrdered(log types.Log) (*CrossBorderPaymentTransferOrdered, error) {
	event := new(CrossBorderPaymentTransferOrdered)
	if err := _CrossBorderPayment.contract.UnpackLog(event, "TransferOrdered", log); err != nil {
		return nil, err
	}
	return event, nil
}
