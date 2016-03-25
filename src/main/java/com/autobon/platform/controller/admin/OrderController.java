package com.autobon.platform.controller.admin;

import com.autobon.getui.PushService;
import com.autobon.order.entity.DetailedOrder;
import com.autobon.order.entity.Order;
import com.autobon.order.service.DetailedOrderService;
import com.autobon.order.service.OrderService;
import com.autobon.shared.JsonMessage;
import com.autobon.shared.JsonPage;
import com.autobon.shared.VerifyCode;
import com.autobon.staff.entity.Staff;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by dave on 16/3/1.
 */
@RestController("adminOrderController")
@RequestMapping("/api/web/admin/order")
public class OrderController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);
    @Value("${com.autobon.gm-path}") String gmPath;
    @Autowired OrderService orderService;
    @Autowired DetailedOrderService detailedOrderService;
    @Autowired @Qualifier("PushServiceA")
    PushService pushServiceA;

    @RequestMapping(method = RequestMethod.GET)
    public JsonMessage search(
            @RequestParam(value = "orderNum", required = false) String orderNum,
            @RequestParam(value = "orderCreator", required = false) String orderCreator,
            @RequestParam(value = "orderType", required = false) Integer orderType,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        String creatorName = null;
        String contactPhone = null;
        Integer statusCode = null;

        if (orderCreator != null) {
            if (Pattern.matches("[\\d\\-]+", orderCreator)) {
                contactPhone = orderCreator;
            } else {
                creatorName = orderCreator;
            }
        }

        if (orderStatus != null) {
            try {
                Order.Status s = Order.Status.valueOf(orderStatus);
                statusCode = s.getStatusCode();
            } catch (Exception e) {}
        }

        return new JsonMessage(true, "", "",
                new JsonPage<>(orderService.find(orderNum, creatorName, contactPhone,
                        orderType, statusCode, page, pageSize)));
    }

    @RequestMapping(value = "/{orderNum:\\d+.*}", method = RequestMethod.GET)
    public JsonMessage show(@PathVariable("orderNum") String orderNum) {
        DetailedOrder order = detailedOrderService.getByOrderNum(orderNum);
        if (order != null) return new JsonMessage(true, "", "", order);
        else return new JsonMessage(false, "ILLEGAL_PARAM", "没有这个订单");
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonMessage createOrder(HttpServletRequest request,
            @RequestParam("orderType")   int orderType,
            @RequestParam("orderTime")   String orderTime,
            @RequestParam("positionLon") String positionLon,
            @RequestParam("positionLat") String positionLat,
            @RequestParam("contactPhone") String contactPhone,
            @RequestParam("contact") String contact,
            @RequestParam("photo") String photo,
            @RequestParam(value = "remark", required = false) String remark) throws Exception {
        Staff staff = (Staff) request.getAttribute("user");
        if (!Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", orderTime))
            return new JsonMessage(false, "ILLEGAL_PARAM", "订单时间格式不对, 正确格式: 2016-02-10 09:23");

        Order order = new Order();
        order.setCreatorType(2);
        order.setCreatorId(staff.getId());
        order.setCreatorName(contact);
        order.setContactPhone(contactPhone);
        order.setPositionLon(positionLon);
        order.setPositionLat(positionLat);
        order.setOrderType(orderType);
        order.setOrderTime(Date.from(
                LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").parse(orderTime))
                .atZone(ZoneId.systemDefault()).toInstant()));
        order.setRemark(remark);
        order.setPhoto(photo);
        orderService.save(order);

        String msgTitle = "你收到新订单推送消息";
        HashMap<String, Object> map = new HashMap<>();
        map.put("action", "NEW_ORDER");
        map.put("order", order);
        map.put("title", msgTitle);
        boolean result = pushServiceA.pushToApp(msgTitle, new ObjectMapper().writeValueAsString(map), 0);
        if (!result) log.info("订单: " + order.getOrderNum() + "的推送消息发送失败");
        return new JsonMessage(true, "", "", order);
    }

    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public JsonMessage uploadPhoto(HttpServletRequest request,
            @RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) return new JsonMessage(false, "NO_UPLOAD_FILE", "没有上传文件");

        String path = "/uploads/order";
        File dir = new File(request.getServletContext().getRealPath(path));
        if (!dir.exists()) dir.mkdirs();

        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf('.')).toLowerCase();
        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                            + VerifyCode.generateVerifyCode(6) + extension;
        file.transferTo(new File(dir.getAbsolutePath() + File.separator + filename));

        return new JsonMessage(true, "", "", path + "/" + filename);
    }


}
