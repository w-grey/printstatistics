package com.wgrey.printstatistics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgrey.printstatistics.util.JobTypeAdapter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="jobs")
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
    @SequenceGenerator(name = "job_seq", sequenceName = "job_seq")
    @JsonIgnore
    @XmlTransient
    private Long id;

    @XmlAttribute(name = "id")
    private long jobId;

    @NotBlank
    @XmlElement
    private String device;

    @NotBlank
    @Column(name="user_name")
    @XmlElement
    private String user;


    @NotNull
    @Enumerated(EnumType.STRING)
    @XmlJavaTypeAdapter(JobTypeAdapter.class)
    @XmlElement
    private JobType type;

    @Min(1)
    @XmlElement
    private int amount;

    @NotNull
    @XmlTransient
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime time;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return id.equals(job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
