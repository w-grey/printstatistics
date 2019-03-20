package com.wgrey.printstatistics.service;

import com.wgrey.printstatistics.controller.Query;
import com.wgrey.printstatistics.error.ApplicationException;
import com.wgrey.printstatistics.model.Job;
import com.wgrey.printstatistics.repository.JobRepository;
import com.wgrey.printstatistics.specification.JobSpecification;
import com.wgrey.printstatistics.to.JobsContainer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    public JobServiceImpl(JobRepository repository) {
        this.repository = repository;
    }

    private JobRepository repository;

    @Override
    public Map<String, Integer> saveTransaction(String xml) {
        List<Job> jobs;
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(JobsContainer.class).createUnmarshaller();
            JobsContainer container=(JobsContainer) unmarshaller.unmarshal(new StringReader(xml));
            jobs=container.getJobs();
        }
        catch (JAXBException e){
            throw new ApplicationException("Couldn't unmarshall jobs.",e);
        }
        jobs.forEach(job -> job.setTime(LocalDateTime.now()));
        repository.saveAll(jobs);

        return jobs.stream()
                .collect(Collectors.groupingBy(Job::getUser, Collectors.summingInt(Job::getAmount)));
    }

    @Override
    public Collection<Job> getStatistics(Query query) {

        return repository
                .findAll(JobSpecification.getAllFiltered(query), Sort.by("time"));
    }
}
