package project.app.Vo;

import project.app.Entities.OrderDetail;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderVo {

    private Integer id;
    private Timestamp createdAt;
    private String orderNumber;
    private Double price;
    private Integer status;
    private Timestamp updatedAt;
    private Integer userId;
    private List<OrderDetail> detail = new ArrayList<>();
}
