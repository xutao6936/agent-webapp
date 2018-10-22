package com.agent.service.impl;

import com.agent.entity.WorkOrder;
import com.agent.page.EasyuiPage;
import com.agent.repository.WorkOrderMapper;
import com.agent.service.WorkOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("workOrderServiceImpl")
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Override
    public EasyuiPage<WorkOrder> find(Integer page, Integer rows,WorkOrder workOrder) {
        PageHelper.startPage(page, rows);
        List<WorkOrder> workOrders = workOrderMapper.find(workOrder);
        EasyuiPage<WorkOrder> pages = new EasyuiPage<WorkOrder>();
        pages.setRows(workOrders);
        PageInfo<WorkOrder> pageInfo = new PageInfo<WorkOrder>(workOrders);
        pages.setTotal(pageInfo.getTotal());
        return pages;
    }

    @Override
    public int update(WorkOrder workOrder) {
        return workOrderMapper.update(workOrder);
    }

    @Override
    public int insert(WorkOrder workOrder) {
        workOrder.setCreateTime(new Date());
//        workOrder.setStatus("0");
        return workOrderMapper.insert(workOrder);
    }
}
