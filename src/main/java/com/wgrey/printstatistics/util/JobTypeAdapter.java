package com.wgrey.printstatistics.util;

import com.wgrey.printstatistics.util.error.ApplicationException;
import com.wgrey.printstatistics.model.JobType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JobTypeAdapter extends XmlAdapter<String, JobType> {
    @Override
    public JobType unmarshal(String s){
        return JobType.getJobType(s);
    }

    @Override
    public String marshal(JobType jobType){
        throw new ApplicationException("Xml marshalling is not supporting");
    }
}
