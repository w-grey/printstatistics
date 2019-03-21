package com.wgrey.printstatistics;


import com.wgrey.printstatistics.repository.JobRepository;
import com.wgrey.printstatistics.service.JobService;
import com.wgrey.printstatistics.service.JobServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;
import static com.wgrey.printstatistics.TestUtil.XML;

@RunWith(SpringRunner.class)
public class JobServiceTest {

    private static JobService jobService;

    @BeforeClass
    public static void setUp(){
        jobService=new JobServiceImpl(Mockito.mock(JobRepository.class));
    }

    @Test
    public void checkSaveTransactionResponse(){

        Map<String,Integer> result=jobService.saveTransaction(XML);

        assertEquals(result.get("user1").intValue(),15);
        assertEquals(result.get("user2").intValue(),22);
    }

}
