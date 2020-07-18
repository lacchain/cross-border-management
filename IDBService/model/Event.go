package model

type Event struct{
	Id string `json:"id"`
	Type  string `json:"type"`
	Details Details `json:"details"`
	Retries string `json:"retries"` 
}