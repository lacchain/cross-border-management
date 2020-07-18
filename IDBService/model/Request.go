package model

//Request ...
type Request struct {
	OperationId string `json:"operationId"`
	Fee         float64 `json:"fee,omitempty"`
	Rate        float64 `json:"rate,omitempty"`      
}