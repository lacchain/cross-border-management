package us.lacchain.crossborder.management.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction implements Serializable {
    private long id;
    private String datetime;
    private String senderBank; 
    private String senderDltAddress;
    private String receiverBank;
    private String receiverDltAddress;
    private float amountSent;
    private float amountReceived;
    private float fee;
    private float rateApplied;
    private int status;


    public Transaction() {
    }

    public Transaction(long id, String datetime, String senderBank, String senderDltAddress, String receiverBank, String receiverDltAddress, float amountSent, float amountReceived, float fee, float rateApplied, int status) {
        this.id = id;
        this.datetime = datetime;
        this.senderBank = senderBank;
        this.senderDltAddress = senderDltAddress;
        this.receiverBank = receiverBank;
        this.receiverDltAddress = receiverDltAddress;
        this.amountSent = amountSent;
        this.amountReceived = amountReceived;
        this.fee = fee;
        this.rateApplied = rateApplied;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSenderBank() {
        return this.senderBank;
    }

    public void setSenderBank(String senderBank) {
        this.senderBank = senderBank;
    }

    public String getSenderDltAddress() {
        return this.senderDltAddress;
    }

    public void setSenderDltAddress(String senderDltAddress) {
        this.senderDltAddress = senderDltAddress;
    }

    public String getReceiverBank() {
        return this.receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public String getReceiverDltAddress() {
        return this.receiverDltAddress;
    }

    public void setReceiverDltAddress(String receiverDltAddress) {
        this.receiverDltAddress = receiverDltAddress;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return id == transaction.id && datetime == transaction.datetime && Objects.equals(senderBank, transaction.senderBank) && Objects.equals(senderDltAddress, transaction.senderDltAddress) && Objects.equals(receiverBank, transaction.receiverBank) && Objects.equals(receiverDltAddress, transaction.receiverDltAddress) && amountSent == transaction.amountSent && amountReceived == transaction.amountReceived && fee == transaction.fee && rateApplied == transaction.rateApplied && status == transaction.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, senderBank, senderDltAddress, receiverBank, receiverDltAddress, amountSent, amountReceived, fee, rateApplied, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", senderBank='" + getSenderBank() + "'" +
            ", senderDltAddress='" + getSenderDltAddress() + "'" +
            ", receiverBank='" + getReceiverBank() + "'" +
            ", receiverDltAddress='" + getReceiverDltAddress() + "'" +
            ", amountSent='" + getAmountSent() + "'" +
            ", amountReceived='" + getAmountReceived() + "'" +
            ", fee='" + getFee() + "'" +
            ", rateApplied='" + getRateApplied() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

}
