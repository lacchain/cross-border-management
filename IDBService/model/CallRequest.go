package model

//CallRequest to simulate JSON-RPC transaction
type CallRequest struct {
	From string `json:"from"`
	To   string `json:"to"`
	Gas  string `json:"gas"`
	Data string `json:"data"`
}