package com.phonon.loastesting.model;

import com.phonon.loastesting.enums.RequestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Request {

    @Id
    private UUID uuid;

    private RequestStatus requestStatus;

    private String name;

    private Timestamp requestTime;

    public Request(){

    }

    public Request(String name){
        this.uuid = UUID.randomUUID();
        this.requestStatus = RequestStatus.PENDING;
        this.name = name;
        this.requestTime = new Timestamp(System.currentTimeMillis());
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public String getName() {
        return name;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    @Override
    public String toString() {
        return "Request{" + "id=" + uuid + ", requestStatus=" + requestStatus + ", name='" + name + '\'' +
                ", requestTime=" + requestTime + '}';
    }
}
