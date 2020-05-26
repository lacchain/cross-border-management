package us.lacchain.crossborder.management.model;

import javax.persistence.ColumnResult;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "movementResultMapping", classes = {
    @ConstructorResult(targetClass = MovementResult.class, columns = { @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "datetime", type= String.class), @ColumnResult(name = "transfer_type"), @ColumnResult(name = "company"), @ColumnResult(name = "amount_received", type = Float.class), @ColumnResult(name = "detail"), @ColumnResult(name = "status"), @ColumnResult(name = "dlt_address") }) })
@NamedNativeQueries({
    @NamedNativeQuery(name = "MovementRepository.getAllMovementsByDltAddress", query = "SELECT DISTINCT movements.id AS id, movements.datetime, CASE WHEN movements.sender = :dltAddress THEN 'TRANSFER OUT' ELSE 'TRANSFER IN' END AS transfer_type,users.company,(movements.received_amount*movements.rate) AS amount_received,movements.detail,CASE WHEN movements.status = 1 THEN 'REQUESTED' WHEN movements.status = 2 THEN 'IN PROGRESS' ELSE 'COMPLETED' END AS status, CASE WHEN movements.sender = :dltAddress THEN movements.receiver ELSE movements.sender END AS dlt_address FROM movements INNER JOIN accounts ON accounts.dlt_address = dlt_address INNER JOIN users ON users.id = accounts.user_id WHERE (movements.sender = :dltAddress and movements.receiver = dlt_address) or (movements.receiver = :dltAddress and movements.sender = dlt_address)", resultSetMapping = "movementResultMapping")})
    
@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(generator="movements_sequence")
    private long id;
    @Column(name = "datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime datetime;
    private String sender;
    private String receiver;
    private float amount;
    private String detail;
    private float received_amount;
    private float fee;
    private float rate;
    private int status;


    public Movement() {
    }

    public Movement(long id, LocalDateTime datetime, String sender, String receiver, float amount, String detail, float received_amount, float fee, float rate, int status) {
        this.id = id;
        this.datetime = datetime;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.detail = detail;
        this.received_amount = received_amount;
        this.fee = fee;
        this.rate = rate;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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
        return Objects.equals(id, movement.id) && Objects.equals(datetime, movement.datetime) && Objects.equals(sender, movement.sender) && Objects.equals(receiver, movement.receiver) && amount == movement.amount && Objects.equals(detail, movement.detail) && received_amount == movement.received_amount && fee == movement.fee && rate == movement.rate && status == movement.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, sender, receiver, amount, detail, received_amount, fee, rate, status);
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
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
