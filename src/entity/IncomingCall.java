package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incoming_call")
public class IncomingCall implements Serializable {

    @Id
    @GeneratedValue
    private String id;
    private String number;
    @Column(name = "call_at")
    private Date callAt;

    public IncomingCall() {
    }

    public IncomingCall(String number, Date callAt) {
        this.number = number;
        this.callAt = callAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCallAt() {
        return callAt;
    }

    public void setCallAt(Date callAt) {
        this.callAt = callAt;
    }
}
