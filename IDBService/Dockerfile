# Build Geth in a stock Go builder container
FROM golang:1.13-alpine as builder

RUN apk update && apk add --no-cache make gcc musl-dev linux-headers git \
    && git clone https://github.com/lacchain/cross-border-management.git
WORKDIR /go/cross-border-management/IDBService
RUN export GO111MODULE=on && go build

# Pull Credential Provider Server into a second stage deploy alpine container
FROM alpine:3.12
RUN apk add --no-cache ca-certificates \
    && update-ca-certificates

RUN mkdir -p /IDBService/log
WORKDIR /IDBService

COPY --from=builder go/cross-border-management/IDBService/IDBService /usr/local/bin/
COPY --from=builder go/cross-border-management/IDBService/config.toml /IDBService/config.toml

EXPOSE 9000
CMD ["sh","-c","IDBService start"]