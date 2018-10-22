package com.agent.repository;

import com.agent.entity.WorkOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkOrderMapper {

    List<WorkOrder> find(WorkOrder workOrder) ;

    int update(WorkOrder workOrder);

    int insert(WorkOrder workOrder);
}
