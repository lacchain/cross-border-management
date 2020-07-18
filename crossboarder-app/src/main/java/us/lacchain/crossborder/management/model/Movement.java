package us.lacchain.crossborder.management.model;

import javax.persistence.ColumnResult;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "movementResultMapping", classes = {
    @ConstructorResult(targetClass = MovementResult.class, columns = { @ColumnResult(name = "id", type = String.class),
            @ColumnResult(name = "datetime", type= String.class), @ColumnResult(name = "transfer_type"), @ColumnResult(name = "company"), @ColumnResult(name = "amount_received", type = Float.class), @ColumnResult(name = "detail"), @ColumnResult(name = "status"), @ColumnResult(name = "dlt_address") }) })
@NamedNativeQueries({
    @NamedNativeQuery(name = "MovementRepository.getAllMovementsByDltAddress", query = "SELECT DISTINCT movements.id AS id, movements.datetime, CASE WHEN movements.sender = :dltAddress THEN 'TRANSFER OUT' ELSE 'TRANSFER IN' END AS transfer_type,users.company,(movements.received_amount*movements.rate) AS amount_received,movements.detail,CASE WHEN movements.status = 1 THEN 'REQUESTED' WHEN movements.status = 2 THEN 'IN PROGRESS' ELSE 'COMPLETED' END AS status, CASE WHEN movements.sender = :dltAddress THEN movements.receiver ELSE movements.sender END AS dlt_address FROM movements INNER JOIN accounts ON accounts.dlt_address = dlt_address INNER JOIN users ON users.id = accounts.user_id WHERE (movements.sender = :dltAddress and movements.receiver = dlt_address) or (movements.receiver = :dltAddress and movements.sender = dlt_address)", resultSetMapping = "movementResultMapping")})
    
@Entity
@Table(name = "movements")
public class Movement {

    @Id
    private String id;
    @Column(name = "datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime datetime;
    private String sender;
    private String receiver;
    private float amount;
    private String detail;
    private float received_amount;
    private float fee;
    private float rate;
    private String operation_requested;
    private String set_fee;
    private String operation_approved;
    private String endtoend_id;
    private int status;

    public Movement() {
    }

    public Movement(String id, LocalDateTime datetime, String sender, String receiver, float amount, String detail, float received_amount, float fee, float rate, String operation_requested, String set_fee, String operation_approved, String endtoend_id, int status) {
        this.id = id;
        this.datetime = datetime;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.detail = detail;
        this.received_amount = received_amount;
        this.fee = fee;
        this.rate = rate;
        this.operation_requested = operation_requested;
        this.set_fee = set_fee;
        this.operation_approved = operation_approved;
        this.endtoend_id = endtoend_id;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return this.datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getReceived_amount() {
        return this.received_amount;
    }

    public void setReceived_amount(float received_amount) {
        this.received_amount = received_amount;
    }

    public float getFee() {
        return this.fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getRate() {
        return this.rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
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

    public String getEndtoend_id(){
        return this.endtoend_id;
    }

    public void setEndtoend_id(String endtoend_id){
        this.endtoend_id = endtoend_id;
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
        if (!(o instanceof Movement)) {
            return false;
        }
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id) && Objects.equals(datetime, movement.datetime) && Objects.equals(sender, movement.sender) && Objects.equals(receiver, movement.receiver) && amount == movement.amount && Objects.equals(detail, movement.detail) && received_amount == movement.received_amount && fee == movement.fee && rate == movement.rate && Objects.equals(operation_requested, movement.operation_requested) && Objects.equals(endtoend_id, movement.endtoend_id) && status == movement.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, sender, receiver, amount, detail, received_amount, fee, rate, operation_requested, set_fee, operation_approved, endtoend_id, status);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movement{");
        sb.append("id=").append(id);
        sb.append(", datetime='").append(datetime).append('\'');
        sb.append(", sender=").append(sender).append('\'');
        sb.append(", receiver=").append(receiver).append('\'');
        sb.append(", amount=").append(amount).append('\'');
        sb.append(", detail=").append(detail).append('\'');
        sb.append(", received_amount=").append(received_amount).append('\'');
        sb.append(", fee=").append(fee).append('\'');
        sb.append(", rate=").append(rate).append('\'');
        sb.append(", operation_requested=").append(operation_requested).append('\'');
        sb.append(", set_fee=").append(set_fee).append('\'');
        sb.append(", operation_approved=").append(operation_approved).append('\'');
        sb.append(", endtoend_id=").append(endtoend_id).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
