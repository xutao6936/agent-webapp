package com.agent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单
 */
@Data
public class WorkOrder implements Serializable{
    private int id;
    private String callerNumber;
    private String serialNo;
    private String problem;
    private Integer creator;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<String> statusCondition;
    private String ringTime;
    private Date beginDate;
    private Date endDate;


}
