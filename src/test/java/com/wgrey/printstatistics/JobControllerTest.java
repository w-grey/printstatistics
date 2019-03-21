package com.wgrey.printstatistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgrey.printstatistics.model.Job;
import com.wgrey.printstatistics.repository.JobRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.wgrey.printstatistics.TestUtil.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void postJobs() throws Exception {

        repository.deleteAllInBatch();
        mockMvc.perform(post("/jobs").contentType("application/xml").content(XML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON))
                .andDo(print());

        List<Job> actual = repository.findAll();

        assertThat(actual).usingElementComparatorIgnoringFields("id", "time").isEqualTo(TEST_JOBS_SAVE);

    }

    @Test
    public void filterJobsWithoutParams() throws Exception {

        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER);

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }


    @Test
    public void filterJobsWithUser() throws Exception {

        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER.stream()
                .filter(job -> job.getUser().equals(TEST_USER)).collect(Collectors.toList()));

        mockMvc.perform(get("/statistics").param("user", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }


    @Test
    public void filterJobsWithUserAndDevice() throws Exception {
        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER.stream()
                .filter(job -> job.getUser().equals(TEST_USER)
                        && job.getDevice().equals(TEST_DEVICE)).collect(Collectors.toList()));

        mockMvc.perform(get("/statistics").param("user", TEST_USER)
                .param("device", TEST_DEVICE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }


    @Test
    public void filterJobsWithUserAndDeviceAndType() throws Exception {
        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER.stream()
                .filter(job -> job.getUser().equals(TEST_USER)
                        && job.getDevice().equals(TEST_DEVICE)
                        && job.getType().equals(TEST_TYPE))
                .collect(Collectors.toList()));

        mockMvc.perform(get("/statistics")
                .param("user", TEST_USER)
                .param("device", TEST_DEVICE)
                .param("type", TEST_TYPE.getValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }

    @Test
    public void filterJobsWithUserAndDeviceAndTypeAndFromTime() throws Exception {
        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER.stream()
                .filter(job -> job.getUser().equals(TEST_USER)
                        && job.getDevice().equals(TEST_DEVICE)
                        && job.getType().equals(TEST_TYPE)
                        && job.getTime().isAfter(TEST_FROM_TIME))
                .collect(Collectors.toList()));

        mockMvc.perform(get("/statistics")
                .param("user", TEST_USER)
                .param("device", TEST_DEVICE)
                .param("type", TEST_TYPE.getValue())
                .param("timeFrom", TEST_FROM_TIME.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }

    @Test
    public void filterJobsWithUserAndDeviceAndTypeAndFromTimeAndToTime() throws Exception {
        prepareDBForFilterTest();

        String jsonExpected = getJson(TEST_JOBS_FILTER.stream()
                .filter(job -> job.getUser().equals(TEST_USER)
                        && job.getDevice().equals(TEST_DEVICE)
                        && job.getType().equals(TEST_TYPE)
                        && job.getTime().isAfter(TEST_FROM_TIME)
                        && job.getTime().isBefore(TEST_TO_TIME))
                .collect(Collectors.toList()));

        mockMvc.perform(get("/statistics")
                .param("user", TEST_USER)
                .param("device", TEST_DEVICE)
                .param("type", TEST_TYPE.getValue())
                .param("timeFrom", TEST_FROM_TIME.toString())
                .param("timeTo", TEST_TO_TIME.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonExpected))
                .andDo(print());
    }

    private void prepareDBForFilterTest() {
        repository.deleteAllInBatch();
        repository.saveAll(TEST_JOBS_FILTER);
    }

    private String getJson(List<Job> jobs) throws Exception {
        return objectMapper.writeValueAsString(jobs);
    }

}
