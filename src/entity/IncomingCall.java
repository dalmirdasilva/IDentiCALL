package entity;

import java.io.Serializable;
import java.sql.Time;
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
    private long id;
    private String number;
    @Column(name = "call_at")
    private Time callAt;

    public IncomingCall() {
    }

    public IncomingCall(long id, String number, Time callAt) {
        this.id = id;
        this.number = number;
        this.callAt = callAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Time getCallAt() {
        return callAt;
    }

    public void setCallAt(Time callAt) {
        this.callAt = callAt;
    }

    @Override
    public String toString() {
        return "{number: " + number + ", callAt: " + callAt + "}";
    }
}
