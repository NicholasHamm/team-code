package project.app.Controllers;

import project.app.Entities.OrderDetail;
import project.app.Entities.Orders;
import project.app.Entities.Product;
import project.app.JpaRepository.OrderDetailRepository;
import project.app.JpaRepository.OrdersRepository;
import project.app.JpaRepository.UserRepository;
import project.app.JpaRepository.ProductRepository;
import project.app.Vo.OrderVo;
import project.app.Vo.PutOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
public class OrderController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @ResponseBody
    @PutMapping("/orders/{orderId}")
    public Map<String, Object> setStatus(@PathVariable("orderId") Integer orderId,
                                         @RequestBody PutOrderVo orderStatus) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (orderStatus.getOrderStatus() < 0 || orderStatus.getOrderStatus() > 4)
                throw new IllegalArgumentException("Invalid status range.");
            if (!ordersRepository.existsById(orderId))
                throw new IllegalArgumentException("Order with ID = " + orderId + "does not exist.");
            ordersRepository.setStatusById(orderId, orderStatus.getOrderStatus(), new Timestamp(System.currentTimeMillis()));
            map.put("status", "success");
            map.put("msg", "Order status changed.");
        } catch (Exception e) {
            map.put("status", "fail");
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @DeleteMapping("/order/{id}")
    public Map<String, Object> deleteOrder(@PathVariable("id") Integer id) {

        Map<String, Object> map = new HashMap<>();

        try {
            if (!ordersRepository.existsById(id))
                throw new IllegalArgumentException("Order with ID = " + id + " does not exist.");
            ordersRepository.deleteById(id);
            orderDetailRepository.deleteByOrderId(id);
            map.put("status", "success");
            map.put("data", id);
        } catch (Exception e) {
            map.put("status", "fail");
            map.put("msg", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/customer/{id}/orders")
    public Map<String, Object> getCustomerOrder(@PathVariable("id") Integer userId) {
        Map<String, Object> map = new HashMap<>();
        List<OrderVo> result = new ArrayList<>();
        try {
            if (!userRepository.existsById(userId))
                throw new IllegalArgumentException("User with ID = " + userId + " doesn't exist.");
            if (userRepository.getOne(userId).getType() != 2)
                throw new IllegalArgumentException("User with ID = " + userId + " is not customer.");
            for (Orders orders : ordersRepository.findByUserId(userId)) {
                Integer ordersId = orders.getId();
                OrderVo order = new OrderVo();
                order.setOrderNumber(orders.getOrderNumber());
                order.setPrice(orders.getPrice());
                order.setCreatedAt(orders.getCreatedAt());
                order.setId(orders.getId());
                order.setUpdatedAt(orders.getUpdatedAt());
                order.setStatus(orders.getStatus());
                order.setUserId(orders.getUserId());
                for (OrderDetail detail : orderDetailRepository.findOrderDetailByOrderId(ordersId)) {
                    order.getDetail().add(detail);
                }
                result.add(order);
            }
            map.put("status", "success");
            map.put("data", result);
        } catch (Exception e) {
            map.put("status", "fail");
            map.put("msg", e.getMessage());
            //e.printStackTrace();
        }
        return map;
    }

    @ResponseBody
    @GetMapping("/owner/{id}/orders")
    public Map<Integer, OrderVo> getOwnerOrders(@PathVariable("id") Integer ownerId) {
        /*
            1.  To view all order history

         */
        Map<String, Object> map = new HashMap<>();
        List<Orders> orders = ordersRepository.findAll();
        Map<Integer, OrderVo> resultOrders = new HashMap<>();

        for (Orders o : orders) {
            OrderVo ov = new OrderVo();
            List<OrderDetail> orderDetails = orderDetailRepository.findAll();
            for (OrderDetail orderDetail : orderDetails) {
                Optional<Product> op = productRepository.findById(orderDetail.getProductId());
                Product product = op.orElse(null);
                if (product != null) {
                    if (product.getOwnerId().equals(ownerId)) {
                        if (!resultOrders.containsKey(o.getId())) {
                            ov.setId(o.getId());
                            ov.setCreatedAt(o.getCreatedAt());
                            ov.setOrderNumber(o.getOrderNumber());
                            ov.setPrice(o.getPrice());
                            ov.setStatus(o.getStatus());
                            ov.setUpdatedAt(o.getUpdatedAt());
                            ov.setUserId(o.getUserId());
                            resultOrders.put(o.getId(), ov);
                        }

                        List<OrderDetail> details = resultOrders.get(o.getId()).getDetail();
                        details.add(orderDetail);

                        resultOrders.get(o.getId()).setDetail(details);
                    }
                }
            }
        }
        return resultOrders;
    }
}
