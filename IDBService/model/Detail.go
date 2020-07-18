package model

type Details struct{
	Name string `json:"name"`
	FilterId  string `json:"type"`
	NodeName  string `json:"nodeName"`
	IndexedParameters []Parameter `json:"indexedParameters"`
	NonIndexedParameters []Parameter `json:"nonIndexedParameters"`
	TransactionHash string `json:"transactionHash"`
	LogIndex  int `json:"logIndex"`
	BlockNumber int `json:"blockNumber"`
	BlockHash  string `json:"blockHash"`
	Address string `json:"address"`
	Status  string `json:"status"` 
	EventSpecificationSignature  string `json:"eventSpecificationSignature"`
	NetworkName string `json:"networkName"`
	Id  string `json:"id"` 
}