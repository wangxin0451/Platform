package com.autobon.platform.controller.technician.order;

import com.autobon.order.entity.Construction;
import com.autobon.order.entity.Order;
import com.autobon.order.entity.WorkItem;
import com.autobon.order.service.CommentService;
import com.autobon.order.service.ConstructionService;
import com.autobon.order.service.OrderService;
import com.autobon.order.service.WorkItemService;
import com.autobon.shared.JsonMessage;
import com.autobon.shared.JsonPage;
import com.autobon.technician.entity.Technician;
import com.autobon.technician.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yuh on 2016/2/22.
 */
@RestController
@RequestMapping("/api/mobile/technician/order")
public class OrderController {
    @Autowired OrderService orderService;
    @Autowired TechnicianService technicianService;
    @Autowired ConstructionService constructionService;
    @Autowired WorkItemService workItemService;
    @Autowired CommentService commentService;

    // 获取已完成的主要责任人订单列表
    @RequestMapping(value = "/listMain", method = RequestMethod.GET)
    public JsonMessage listMain(HttpServletRequest request,
             @RequestParam(value = "page", defaultValue = "1") int page,
             @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Technician technician = (Technician) request.getAttribute("user");
        return new JsonMessage(true, "", "",
                new JsonPage<>(orderService.findFinishedOrderByMainTechId(technician.getId(), page, pageSize)));
    }

    // 获取已完成的次要责任人订单列表
    @RequestMapping(value = "/listSecond", method = RequestMethod.GET)
    public JsonMessage listSecond(HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Technician technician = (Technician) request.getAttribute("user");
        return new JsonMessage(true, "", "",
                new JsonPage<>(orderService.findFinishedOrderBySecondTechId(technician.getId(), page, pageSize)));
    }

    // 获取未完成的订单
    @RequestMapping(value = "/listUnfinished", method = RequestMethod.GET)
    public JsonMessage listUnfinished(HttpServletRequest request,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Technician technician = (Technician) request.getAttribute("user");
        return new JsonMessage(true, "", "",
                new JsonPage<>(orderService.findUnFinishedOrderByTechId(technician.getId(), page, pageSize)));
    }

    // 获取订单信息
    @RequestMapping(value = "/{orderId:[\\d]+}", method = RequestMethod.GET)
    public JsonMessage show(HttpServletRequest request,
            @PathVariable("orderId") int orderId) {
        Technician tech = (Technician) request.getAttribute("user");
        Order order = orderService.get(orderId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order", order);
        map.put("mainTech", order.getMainTechId() > 0 ? technicianService.get(order.getMainTechId()) : null);
        map.put("secondTech", order.getSecondTechId() > 0 ? technicianService.get(order.getSecondTechId()) : null);
        map.put("construction", order.getStatusCode() >= Order.Status.IN_PROGRESS.getStatusCode() ?
                    constructionService.getByTechIdAndOrderId(tech.getId(), order.getId()) : null);
        map.put("comment", order.getStatusCode() >= Order.Status.COMMENTED.getStatusCode() ?
                    commentService.getByOrderIdAndTechId(order.getId(), tech.getId()) : null);
        return new JsonMessage(true, "", "", map);
    }

    // 抢单
    @RequestMapping(value = "/takeup", method = RequestMethod.POST)
    public JsonMessage takeUpOrder(HttpServletRequest request,
            @RequestParam("orderId") int orderId) {
        Technician tech = (Technician) request.getAttribute("user");
        Order order = orderService.get(orderId);
        if (order == null) {
            return new JsonMessage(false, "NO_SUCH_ORDER", "没有这个订单");
        } else if (tech.getStatus() != Technician.Status.VERIFIED) {
            return new JsonMessage(false, "NOT_VERIFIED", "你没有通过认证, 不能抢单");
        } else if (order.getStatus() == Order.Status.CANCELED) {
            return new JsonMessage(false, "ORDER_CANCELED", "订单已取消");
        } else if (order.getStatus() != Order.Status.NEWLY_CREATED) {
            return new JsonMessage(false, "ORDER_TAKEN_UP", "已有人接单");
        } else if (tech.getSkill() == null || !Arrays.stream(tech.getSkill().split(",")).anyMatch(i -> i.equals("" + order.getOrderType()))) {
            String orderType = workItemService.getOrderTypes().stream()
                    .filter(t -> t.getOrderType() == order.getOrderType())
                    .findFirst().orElse(new WorkItem()).getOrderTypeName();
            return new JsonMessage(false, "TECH_SKILL_NOT_SUFFICIANT", "你当前的认证技能没有" + orderType);
        }

        order.setMainTechId(tech.getId());
        order.setStatus(Order.Status.TAKEN_UP);
        orderService.save(order);
        return new JsonMessage(true, "", "", order);
    }

}
