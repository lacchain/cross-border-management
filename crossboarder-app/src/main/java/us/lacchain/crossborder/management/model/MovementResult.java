package us.lacchain.crossborder.management.model;

import java.util.Objects;

public class MovementResult {

    private String id;
    private String datetime;
    private String transfer_type;
    private String sender_name;
    private String receiver_name;
    private float amount;
    private float amount_received;
    private String currency;
    private float fee_applied;
    private float rate_applied;
    private String status;
    private String dlt_address;


    public MovementResult() {
    }

    public MovementResult(String id, String datetime, String transfer_type, String sender_name, String receiver_name, float amount, float amount_received, String currency, float fee_applied, float rate_applied, String status, String dlt_address) {
        this.id = id;
        this.datetime = datetime;
        this.transfer_type = transfer_type;
        this.sender_name = sender_name;
        this.receiver_name = receiver_name;
        this.amount = amount;
        this.amount_received = amount_received;
        this.currency = currency;
        this.fee_applied = fee_applied;
        this.rate_applied = rate_applied;
        this.status = status;
        this.dlt_address = dlt_address;
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

    public String getTransfer_type() {
        return this.transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReceiver_name() {
        return this.receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmount_received() {
        return this.amount_received;
    }

    public void setAmount_received(float amount_received) {
        this.amount_received = amount_received;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getFee_applied() {
        return this.fee_applied;
    }

    public void setFee_applied(float fee_applied) {
        this.fee_applied = fee_applied;
    }

    public float getRate_applied() {
        return this.rate_applied;
    }

    public void setRate_applied(float rate_applied) {
        this.rate_applied = rate_applied;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDlt_address() {
        return this.dlt_address;
    }

    public void setDlt_address(String dlt_address) {
        this.dlt_address = dlt_address;
    }

    public MovementResult id(String id) {
        this.id = id;
        return this;
    }

    public MovementResult datetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public MovementResult transfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
        return this;
    }

    public MovementResult sender_name(String sender_name) {
        this.sender_name = sender_name;
        return this;
    }

    public MovementResult receiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
        return this;
    }

    public MovementResult amount(float amount) {
        this.amount = amount;
        return this;
    }

    public MovementResult amount_received(float amount_received) {
        this.amount_received = amount_received;
        return this;
    }

    public MovementResult currency(String currency) {
        this.currency = currency;
        return this;
    }

    public MovementResult fee_applied(float fee_applied) {
        this.fee_applied = fee_applied;
        return this;
    }

    public MovementResult rate_applied(float rate_applied) {
        this.rate_applied = rate_applied;
        return this;
    }

    public MovementResult status(String status) {
        this.status = status;
        return this;
    }

    public MovementResult dlt_address(String dlt_address) {
        this.dlt_address = dlt_address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MovementResult)) {
            return false;
        }
        MovementResult movementResult = (MovementResult) o;
        return Objects.equals(id, movementResult.id) && Objects.equals(datetime, movementResult.datetime) && Objects.equals(transfer_type, movementResult.transfer_type) && Objects.equals(sender_name, movementResult.sender_name) && Objects.equals(receiver_name, movementResult.receiver_name) && amount == movementResult.amount && amount_received == movementResult.amount_received && Objects.equals(currency, movementResult.currency) && fee_applied == movementResult.fee_applied && rate_applied == movementResult.rate_applied && Objects.equals(status, movementResult.status) && Objects.equals(dlt_address, movementResult.dlt_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, transfer_type, sender_name, receiver_name, amount, amount_received, currency, fee_applied, rate_applied, status, dlt_address);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", transfer_type='" + getTransfer_type() + "'" +
            ", sender_name='" + getSender_name() + "'" +
            ", receiver_name='" + getReceiver_name() + "'" +
            ", amount='" + getAmount() + "'" +
            ", amount_received='" + getAmount_received() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", fee_applied='" + getFee_applied() + "'" +
            ", rate_applied='" + getRate_applied() + "'" +
            ", status='" + getStatus() + "'" +
            ", dlt_address='" + getDlt_address() + "'" +
            "}";
    }
}



    