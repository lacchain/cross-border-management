package us.lacchain.crossborder.management.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

@SqlResultSetMapping(name = "transactionResultMapping", classes = {
    @ConstructorResult(targetClass = Transaction.class, columns = { @ColumnResult(name = "id", type = String.class),
            @ColumnResult(name = "datetime", type= String.class), @ColumnResult(name = "transfer_type", type= String.class), @ColumnResult(name = "sender_name", type = String.class), 
            @ColumnResult(name = "receiver_name", type = String.class), @ColumnResult(name = "sent_amount", type = Float.class), @ColumnResult(name = "recipient_will_get",type = Float.class),
            @ColumnResult(name = "fee_applied",type = Float.class), @ColumnResult(name = "rate_applied",type = Float.class), 
            @ColumnResult(name = "status",type = String.class) }) })
@NamedNativeQueries({
    @NamedNativeQuery(name = "MovementDetailRepository.getAllTransactions", query = "SELECT id, datetime, CASE WHEN UPPER(sender_dlt_address) = UPPER('0X0000000000000000000000000000000000000000') THEN 'TOKENIZATION' ELSE 'ACH' END AS transfer_type, CASE WHEN UPPER(sender_dlt_address) = UPPER('0X0000000000000000000000000000000000000000') THEN 'Citibank' ELSE sender_name END AS sender_name, receiver_name, sent_amount,recipient_will_get,fee_applied,rate_applied, status from movements_view m", resultSetMapping = "transactionResultMapping")})
    

@Entity
@Table(name = "movements_view")
public class MovementDetail {

    @Id
    private String id;
    private String datetime;
    private float sent_amount;
    private float fee_applied;
    private float converted_amount;
    private float rate_applied;
    private float recipient_will_get;
    private String sender_name;
    private String sender_bank;
    private String sender_bank_account;
    private String sender_dlt_address;
    private String sender_currency;
    private String receiver_name;
    private String receiver_bank;
    private String receiver_bank_account;
    private String receiver_dlt_address;
    private String receiver_currency;
    private String operation_requested;
    private String set_fee;
    private String operation_approved;
    private String operation_executed;
    private String endtoend_id;
    private String apimguid;
    private String acctsvcrref;
    private String status;

    public MovementDetail() {
    }

    public MovementDetail(String id, String datetime, float sent_amount, float fee_applied, float converted_amount, float rate_applied, float recipient_will_get, String sender_name, String sender_bank, String sender_bank_account, String sender_dlt_address, String sender_currency, String receiver_name, String receiver_bank, String receiver_bank_account, String receiver_dlt_address, String receiver_currency, String operation_requested, String set_fee, String operation_approved, String operation_executed, String endtoend_id, String apimguid, String acctsvcrref, String status) {
        this.id = id;
        this.datetime = datetime;
        this.sent_amount = sent_amount;
        this.fee_applied = fee_applied;
        this.converted_amount = converted_amount;
        this.rate_applied = rate_applied;
        this.recipient_will_get = recipient_will_get;
        this.sender_name = sender_name;
        this.sender_bank = sender_bank;
        this.sender_bank_account = sender_bank_account;
        this.sender_dlt_address = sender_dlt_address;
        this.sender_currency = sender_currency;
        this.receiver_name = receiver_name;
        this.receiver_bank = receiver_bank;
        this.receiver_bank_account = receiver_bank_account;
        this.receiver_dlt_address = receiver_dlt_address;
        this.receiver_currency = receiver_currency;
        this.operation_requested = operation_requested;
        this.set_fee = set_fee;
        this.operation_approved = operation_approved;
        this.operation_executed = operation_executed;
        this.endtoend_id = endtoend_id;
        this.apimguid = apimguid;
        this.acctsvcrref = acctsvcrref;
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

    public float getSent_amount() {
        return this.sent_amount;
    }

    public void setSent_amount(float sent_amount) {
        this.sent_amount = sent_amount;
    }

    public float getFee_applied() {
        return this.fee_applied;
    }

    public void setFee_applied(float fee_applied) {
        this.fee_applied = fee_applied;
    }

    public float getConverted_amount() {
        return this.converted_amount;
    }

    public void setConverted_amount(float converted_amount) {
        this.converted_amount = converted_amount;
    }

    public float getRate_applied() {
        return this.rate_applied;
    }

    public void setRate_applied(float rate_applied) {
        this.rate_applied = rate_applied;
    }

    public float getRecipient_will_get() {
        return this.recipient_will_get;
    }

    public void setRecipient_will_get(float recipient_will_get) {
        this.recipient_will_get = recipient_will_get;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_bank() {
        return this.sender_bank;
    }

    public void setSender_bank(String sender_bank) {
        this.sender_bank = sender_bank;
    }

    public String getSender_bank_account() {
        return this.sender_bank_account;
    }

    public void setSender_bank_account(String sender_bank_account) {
        this.sender_bank_account = sender_bank_account;
    }

    public String getSender_dlt_address() {
        return this.sender_dlt_address;
    }

    public void setSender_dlt_address(String sender_dlt_address) {
        this.sender_dlt_address = sender_dlt_address;
    }

    public String getSender_currency() {
        return this.sender_currency;
    }

    public void setSender_currency(String sender_currency) {
        this.sender_currency = sender_currency;
    }

    public String getReceiver_name() {
        return this.receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_bank() {
        return this.receiver_bank;
    }

    public void setReceiver_bank(String receiver_bank) {
        this.receiver_bank = receiver_bank;
    }

    public String getReceiver_bank_account() {
        return this.receiver_bank_account;
    }

    public void setReceiver_bank_account(String receiver_bank_account) {
        this.receiver_bank_account = receiver_bank_account;
    }

    public String getReceiver_dlt_address() {
        return this.receiver_dlt_address;
    }

    public void setReceiver_dlt_address(String receiver_dlt_address) {
        this.receiver_dlt_address = receiver_dlt_address;
    }

    public String getReceiver_currency() {
        return this.receiver_currency;
    }

    public void setReceiver_currency(String receiver_currency) {
        this.receiver_currency = receiver_currency;
    }

    public String getOperation_requested() {
        return this.operation_requested;
    }

    public void setOperation_requested(String operation_requested) {
        this.operation_requested = operation_requested;
    }

    public String getSet_fee() {
        return this.set_fee;
    }

    public void setSet_fee(String set_fee) {
        this.set_fee = set_fee;
    }

    public String getOperation_approved() {
        return this.operation_approved;
    }

    public void setOperation_approved(String operation_approved) {
        this.operation_approved = operation_approved;
    }

    public String getOperation_executed() {
        return this.operation_executed;
    }

    public void setOperation_executed(String operation_executed) {
        this.operation_executed = operation_executed;
    }

    public String getEndtoend_id() {
        return this.endtoend_id;
    }
    
    public void setEndtoend_id(String endtoend_id) {
        this.endtoend_id = endtoend_id;
    }

    public String getApimguid() {
        return this.apimguid;
    }
    
    public void setApimguid(String apimguid) {
        this.apimguid = apimguid;
    }

    public String getAcctsvcrref() {
        return this.acctsvcrref;
    }

    public void setAcctsvcrref(String acctsvcrref) {
        this.acctsvcrref = acctsvcrref;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MovementDetail)) {
            return false;
        }
        MovementDetail movementDetail = (MovementDetail) o;
        return id == movementDetail.id && Objects.equals(datetime, movementDetail.datetime) && sent_amount == movementDetail.sent_amount && fee_applied == movementDetail.fee_applied && converted_amount == movementDetail.converted_amount && rate_applied == movementDetail.rate_applied && recipient_will_get == movementDetail.recipient_will_get && Objects.equals(sender_name, movementDetail.sender_name) && Objects.equals(sender_bank, movementDetail.sender_bank) && Objects.equals(sender_bank_account, movementDetail.sender_bank_account) && Objects.equals(sender_dlt_address, movementDetail.sender_dlt_address) && Objects.equals(sender_currency, movementDetail.sender_currency) && Objects.equals(receiver_name, movementDetail.receiver_name) && Objects.equals(receiver_bank, movementDetail.receiver_bank) && Objects.equals(receiver_bank_account, movementDetail.receiver_bank_account) && Objects.equals(receiver_dlt_address, movementDetail.receiver_dlt_address) && Objects.equals(receiver_currency, movementDetail.receiver_currency) && Objects.equals(operation_requested, movementDetail.operation_requested) && Objects.equals(set_fee, movementDetail.set_fee) && Objects.equals(operation_approved, movementDetail.operation_approved) && Objects.equals(operation_executed, movementDetail.operation_executed) && Objects.equals(status, movementDetail.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, sent_amount, fee_applied, converted_amount, rate_applied, recipient_will_get, sender_name, sender_bank, sender_bank_account, sender_dlt_address, sender_currency, receiver_name, receiver_bank, receiver_bank_account, receiver_dlt_address, receiver_currency, operation_requested, set_fee, operation_approved, operation_executed, endtoend_id, apimguid, acctsvcrref, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", sent_amount='" + getSent_amount() + "'" +
            ", fee_applied='" + getFee_applied() + "'" +
            ", converted_amount='" + getConverted_amount() + "'" +
            ", rate_applied='" + getRate_applied() + "'" +
            ", recipient_will_get='" + getRecipient_will_get() + "'" +
            ", sender_name='" + getSender_name() + "'" +
            ", sender_bank='" + getSender_bank() + "'" +
            ", sender_bank_account='" + getSender_bank_account() + "'" +
            ", sender_dlt_address='" + getSender_dlt_address() + "'" +
            ", sender_currency='" + getSender_currency() + "'" +
            ", receiver_name='" + getReceiver_name() + "'" +
            ", receiver_bank='" + getReceiver_bank() + "'" +
            ", receiver_bank_account='" + getReceiver_bank_account() + "'" +
            ", receiver_dlt_address='" + getReceiver_dlt_address() + "'" +
            ", receiver_currency='" + getReceiver_currency() + "'" +
            ", operation_requested='" + getOperation_requested() + "'" +
            ", set_fee='" + getSet_fee() + "'" +
            ", operation_approved='" + getOperation_approved() + "'" +
            ", operation_executed='" + getOperation_approved() + "'" +
            ", endtoend_id ='" + getEndtoend_id() + "'" +
            ", apimguid ='" + getApimguid() + "'" +
            ", acctsvcrref ='" + getAcctsvcrref() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }    
}
