package us.lacchain.crossborder.management.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class MovementResult {

    private long id;
    private String datetime;
    private String transfer_type;
    private String company;
    private float amount_received;
    private String detail;
    private String status;
    private String dlt_address;


    public MovementResult() {
    }

    public MovementResult(long id, String datetime, String transfer_type, String company, float amount_received, String detail, String status, String dlt_address) {
        this.id = id;
        this.datetime = datetime;
        this.transfer_type = transfer_type;
        this.company = company;
        this.amount_received = amount_received;
        this.detail = detail;
        this.status = status;
        this.dlt_address = dlt_address;
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

    public String getTransfer_type() {
        return this.transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getAmount_received() {
        return this.amount_received;
    }

    public void setAmount_received(float amount_received) {
        this.amount_received = amount_received;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Movement)) {
            return false;
        }
        MovementResult movement = (MovementResult) o;
        return id == movement.id && Objects.equals(datetime, movement.datetime) && Objects.equals(transfer_type, movement.transfer_type) && Objects.equals(company, movement.company) && amount_received == movement.amount_received && Objects.equals(detail, movement.detail) && Objects.equals(status, movement.status) && Objects.equals(dlt_address, movement.dlt_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, transfer_type, company, amount_received, detail, status, dlt_address);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", transfer_type='" + getTransfer_type() + "'" +
            ", company='" + getCompany() + "'" +
            ", amount_received='" + getAmount_received() + "'" +
            ", detail='" + getDetail() + "'" +
            ", status='" + getStatus() + "'" +
            ", dlt_address='" + getDlt_address() + "'" +
            "}";
    }
}



    