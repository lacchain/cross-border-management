package us.lacchain.crossborder.management.model;

import java.io.Serializable;
import java.util.Objects;

public class Transaction implements Serializable {
    private String id;
    private String datetime;
    private String transferType;
    private String senderName;
    private String receiverName;
    private float amountSent;
    private float amountReceived;
    private String senderCurrency;
    private String receiverCurrency;
    private float fee;
    private float rateApplied;
    private String status;


    public Transaction() {
    }

    public Transaction(String id, String datetime, String transferType, String senderName, String receiverName, float amountSent, float amountReceived, String senderCurrency, String receiverCurrency, float fee, float rateApplied, String status) {
        this.id = id;
        this.datetime = datetime;
        this.transferType = transferType;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amountSent = amountSent;
        this.amountReceived = amountReceived;
        this.senderCurrency = senderCurrency;
        this.receiverCurrency = receiverCurrency;
        this.fee = fee;
        this.rateApplied = rateApplied;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTransferType() {
        return this.transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public float getAmountSent() {
        return this.amountSent;
    }

    public void setAmountSent(float amountSent) {
        this.amountSent = amountSent;
    }

    public float getAmountReceived() {
        return this.amountReceived;
    }

    public void setAmountReceived(float amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getSenderCurrency() {
        return this.senderCurrency;
    }

    public void setSenderCurrency(String senderCurrency) {
        this.senderCurrency = senderCurrency;
    }

    public String getReceiverCurrency() {
        return this.receiverCurrency;
    }

    public void setReceiverCurrency(String receiverCurrency) {
        this.receiverCurrency = receiverCurrency;
    }

    public float getFee() {
        return this.fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getRateApplied() {
        return this.rateApplied;
    }

    public void setRateApplied(float rateApplied) {
        this.rateApplied = rateApplied;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transaction id(String id) {
        this.id = id;
        return this;
    }

    public Transaction datetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public Transaction transferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public Transaction senderName(String senderName) {
        this.senderName = senderName;
        return this;
    }

    public Transaction receiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public Transaction amountSent(float amountSent) {
        this.amountSent = amountSent;
        return this;
    }

    public Transaction amountReceived(float amountReceived) {
        this.amountReceived = amountReceived;
        return this;
    }

    public Transaction senderCurrency(String senderCurrency) {
        this.senderCurrency = senderCurrency;
        return this;
    }

    public Transaction receiverCurrency(String receiverCurrency) {
        this.receiverCurrency = receiverCurrency;
        return this;
    }

    public Transaction fee(float fee) {
        this.fee = fee;
        return this;
    }

    public Transaction rateApplied(float rateApplied) {
        this.rateApplied = rateApplied;
        return this;
    }

    public Transaction status(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.id) && Objects.equals(datetime, transaction.datetime) && Objects.equals(transferType, transaction.transferType) && Objects.equals(senderName, transaction.senderName) && Objects.equals(receiverName, transaction.receiverName) && amountSent == transaction.amountSent && amountReceived == transaction.amountReceived && Objects.equals(senderCurrency, transaction.senderCurrency) && Objects.equals(receiverCurrency, transaction.receiverCurrency) && fee == transaction.fee && rateApplied == transaction.rateApplied && Objects.equals(status, transaction.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, transferType, senderName, receiverName, amountSent, amountReceived, senderCurrency, receiverCurrency, fee, rateApplied, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", transferType='" + getTransferType() + "'" +
            ", senderName='" + getSenderName() + "'" +
            ", receiverName='" + getReceiverName() + "'" +
            ", amountSent='" + getAmountSent() + "'" +
            ", amountReceived='" + getAmountReceived() + "'" +
            ", senderCurrency='" + getSenderCurrency() + "'" +
            ", receiverCurrency='" + getReceiverCurrency() + "'" +
            ", fee='" + getFee() + "'" +
            ", rateApplied='" + getRateApplied() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

}
