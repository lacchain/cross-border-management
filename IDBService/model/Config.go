package model

type ApplicationConfig struct {
	NodeURL string `mapstructure:"nodeURL"`
	ContractAddress string `mapstructure:"contractAddress"`
}

type KeyStoreConfig struct {
	FeeAgent string `mapstructure:"feeAgent"`
	OperatorAgent string `mapstructure:"operatorAgent"`
}

type PassphraseConfig struct {
	FeeAgent string `mapstructure:"feeAgent"`
	OperatorAgent string `mapstructure:"operatorAgent"`
}

type KafkaConfig struct {
	URL string `mapstructure:"url"`
	Topic string `mapstructure:"topic"`
	GroupId string `mapstructure:"groupId"`
}

type Config struct {
	Application  ApplicationConfig `mapstructure:"application"`
	KeyStore  KeyStoreConfig `mapstructure:"keystore"`
	Passphrase PassphraseConfig   `mapstructure:"passphrase"`
	Kafka KafkaConfig `mapstructure:"kafka"`     
}