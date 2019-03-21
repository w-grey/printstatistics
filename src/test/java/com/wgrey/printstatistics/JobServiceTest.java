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

@RunWith(SpringRunner.class)
public class JobServiceTest {

    private static JobService jobService;

    @BeforeClass
    public static void setUp(){
        jobService=new JobServiceImpl(Mockito.mock(JobRepository.class));
    }

    @Test
    public void checkSaveTransactionResponse(){
        String xml="<jobs>" +
                "    <job id=\"16\">" +
                "        <type>print</type>" +
                "        <user>user1</user>" +
                "        <device>device1</device>" +
                "        <amount>12</amount>" +
                "    </job>" +
                "    <job id=\"17\">" +
                "        <type>scan</type>" +
                "        <user>user1</user>" +
                "        <device>device1</device>" +
                "        <amount>3</amount>" +
                "    </job>" +
                "    <job id=\"18\">" +
                "        <type>fax</type>" +
                "        <user>user2</user>" +
                "        <device>device1</device>" +
                "        <amount>10</amount>" +
                "    </job>" +
                "    <job id=\"19\">" +
                "        <type>fax</type>" +
                "        <user>user2</user>" +
                "        <device>device1</device>" +
                "        <amount>12</amount>" +
                "    </job>" +
                "</jobs>";

        Map<String,Integer> result=jobService.saveTransaction(xml);

        assertEquals(result.get("user1").intValue(),15);
        assertEquals(result.get("user2").intValue(),22);
    }

}
