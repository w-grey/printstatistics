package com.wgrey.printstatistics.controller;

import com.wgrey.printstatistics.error.ApplicationException;
import com.wgrey.printstatistics.model.JobType;

import java.time.LocalDateTime;

public class Query {

    private static final LocalDateTime MAX_DATE=LocalDateTime.of(2200,1,1,0,0);
    private static final LocalDateTime MIN_DATE=LocalDateTime.of(1900,1,1,0,0);

    private String user;
    private JobType type;
    private String device;
    private LocalDateTime from;
    private LocalDateTime to;

    public Query(String user, String type, String device, LocalDateTime from, LocalDateTime to) {
        this.user = user;
        try {
            this.type=JobType.getJobType(type);
        }
        catch (ApplicationException e){
            this.type=null;
        }
        this.device = device;
        this.from = from!=null?from:MIN_DATE;
        this.to = to!=null?to:MAX_DATE;
    }

    public String getUser() {
        return user;
    }

    public JobType getType() {
        return type;
    }

    public String getDevice() {
        return device;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

}
