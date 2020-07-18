# Build Geth in a stock Go builder container
FROM golang:1.13-alpine as builder

RUN apk update && apk add --no-cache make gcc musl-dev linux-headers git \
    && git clone https://github.com/ccamaleon5/IDBService.git
WORKDIR /go/IDBService
RUN export GO111MODULE=on && go build

# Pull Credential Provider Server into a second stage deploy alpine container
FROM alpine:3.12
RUN apk add --no-cache ca-certificates \
    && update-ca-certificates

RUN mkdir -p /CredentialMother 
WORKDIR /CredentialMother

COPY --from=builder go/CredentialServer/cmd/credential-provider-server /usr/local/bin/
COPY --from=builder go/CredentialServer/cmd/credential-provider-server/server.crt /CredentialMother/
COPY --from=builder go/CredentialServer/cmd/credential-provider-server/server.key /CredentialMother/
COPY --from=builder go/CredentialServer/swagger/ /CredentialMother/

EXPOSE 9002
CMD ["sh","-c","credential-provider-server start --host=0.0.0.0 --tlscertificate=/usr/local/bin/server.crt --tlskey=/usr/local/bin/server.key --port 8000 --secret Password."]