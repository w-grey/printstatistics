package com.wgrey.printstatistics.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wgrey.printstatistics.error.ApplicationException;

import java.util.Arrays;

public enum JobType {
    PRINT("print"), COPY("copy"), SCAN("scan"), FAX("fax");

    private final String value;

    JobType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static JobType getJobType(String s) {
        return Arrays.stream(JobType.values())
                .filter(jobType -> jobType.value.equals(s)).
                        findFirst().orElseThrow(
                                () -> new ApplicationException("There is no appropriate job type")
                );
    }
}
