package com.wgrey.printstatistics.controller;

import com.wgrey.printstatistics.model.Job;
import com.wgrey.printstatistics.service.JobService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RestController
public class JobController {
    private JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping(path = "/jobs",consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Integer> saveJobs(@RequestBody String xml){
        return service.saveTransaction(xml);
    }

    @GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Job> getStatistics(@RequestParam(value = "user",required = false) String user,
                                         @RequestParam(value = "type",required = false) String type,
                                         @RequestParam(value = "device",required = false )String device,
                                         @RequestParam(value = "timeFrom", required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                         @RequestParam(value = "timeTo", required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to){

        return service.getStatistics(new Query(user,type,device,from,to));

    }
}
