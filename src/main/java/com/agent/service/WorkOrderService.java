package com.agent.service;

import com.agent.entity.WorkOrder;
import com.agent.page.EasyuiPage;
import com.agent.page.Page;

import java.util.Map;

public interface WorkOrderService {

    EasyuiPage<WorkOrder> find(Integer page, Integer rows,WorkOrder workOrder) ;

    int update(WorkOrder workOrder);

    int insert(WorkOrder workOrder);
}
