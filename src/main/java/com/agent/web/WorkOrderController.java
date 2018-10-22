package com.agent.web;

import com.agent.entity.WorkOrder;
import com.agent.page.EasyuiPage;
import com.agent.service.WorkOrderService;
import com.agent.shiro.ShiroUser;
import com.agent.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/workOrder")
@Slf4j
public class WorkOrderController {

    @Resource(name="workOrderServiceImpl")
    WorkOrderService workOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public String toHtml(){
        return "callIn/workOrder";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyuiPage list(Integer page, Integer rows, WorkOrder workOrder){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
         // 2:售后坐席  3:售后维护人员
        List<String>  statusCondition = new ArrayList<String>();
        if(shiroUser.getRoleId() == 2){
            // 状态0:创建 1:下发 2:维修中 3:回单 4:关闭
            statusCondition.add("0");
            statusCondition.add("3");
            workOrder.setCreator(shiroUser.getId());
        }else if(shiroUser.getRoleId() == 3){
            statusCondition.add("1");
            statusCondition.add("2");
        }
        if(!StringUtils.isEmpty(workOrder.getStatus())){
            statusCondition.add(workOrder.getStatus());
        }
        //2018-10-02 00:00:00 - 2018-11-20 00:00:00
        if(!StringUtils.isEmpty(workOrder.getRingTime())){
            String[] split = workOrder.getRingTime().split(" - ");
            workOrder.setBeginDate(DateUtil.parseDate(split[0],DateUtil.DATE_TIME_FORMAT));
            workOrder.setEndDate(DateUtil.parseDate(split[1],DateUtil.DATE_TIME_FORMAT));
        }
        workOrder.setStatusCondition(statusCondition);
        EasyuiPage<WorkOrder> result = workOrderService.find(page, rows, workOrder);
        return result;
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(int id,String flag){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(id);
        workOrder.setUpdateTime(new Date());
        String status = String.valueOf(Integer.parseInt(flag)+1);
        workOrder.setStatus(status);
        workOrderService.update(workOrder);
        return "success";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String insert(WorkOrder workOrder){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        workOrder.setCreator(shiroUser.getId());
        workOrderService.insert(workOrder);
        return "success";
    }


}
