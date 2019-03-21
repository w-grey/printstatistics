package com.wgrey.printstatistics;

import com.wgrey.printstatistics.model.Job;
import com.wgrey.printstatistics.model.JobType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TestUtil {
    static final String XML="<jobs>" +
            "    <job id=\"1\">" +
            "        <type>print</type>" +
            "        <user>user1</user>" +
            "        <device>device1</device>" +
            "        <amount>12</amount>" +
            "    </job>" +
            "    <job id=\"2\">" +
            "        <type>scan</type>" +
            "        <user>user1</user>" +
            "        <device>device1</device>" +
            "        <amount>3</amount>" +
            "    </job>" +
            "    <job id=\"3\">" +
            "        <type>fax</type>" +
            "        <user>user2</user>" +
            "        <device>device1</device>" +
            "        <amount>10</amount>" +
            "    </job>" +
            "    <job id=\"4\">" +
            "        <type>fax</type>" +
            "        <user>user2</user>" +
            "        <device>device2</device>" +
            "        <amount>12</amount>" +
            "    </job>" +
            "</jobs>";

    static final String JSON="{\"user1\":15,\"user2\":22}";

    static final LocalDateTime TEST_FROM_TIME =LocalDateTime.of(2019,3,20,9,0);

    static final LocalDateTime TEST_TO_TIME=LocalDateTime.of(2019,3,20,20,0);

    static final String TEST_USER ="user0";

    static final String TEST_DEVICE="device1";

    static final JobType TEST_TYPE=JobType.COPY;

    static final List<Job> TEST_JOBS_SAVE=new ArrayList<>();

    static final List<Job> TEST_JOBS_FILTER =new ArrayList<>();

    static {
        JobType[] types=JobType.values();
        for (int i=0;i<70;i++){
            TEST_JOBS_FILTER.add(new Job(i,"device"+i%2,"user"+i%3, types[i%types.length],i+5, LocalDateTime.of(2019,3,20,i%24,(i+10)%60)));
        }

        TEST_JOBS_SAVE.add(new Job(1,"device1","user1",JobType.PRINT,12,null));
        TEST_JOBS_SAVE.add(new Job(2,"device1","user1",JobType.SCAN,3,null));
        TEST_JOBS_SAVE.add(new Job(3,"device1","user2",JobType.FAX,10,null));
        TEST_JOBS_SAVE.add(new Job(4,"device2","user2",JobType.FAX,12,null));
    }
}
