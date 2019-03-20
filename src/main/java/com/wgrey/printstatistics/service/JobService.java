package com.wgrey.printstatistics.service;

import com.wgrey.printstatistics.controller.Query;
import com.wgrey.printstatistics.model.Job;

import java.util.Collection;
import java.util.Map;

public interface JobService {
    Map<String,Integer> saveTransaction(String xml);
    Collection<Job> getStatistics(Query query);
}
