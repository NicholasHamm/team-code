package project.app.Entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Orders
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String orderNumber;

    @Column
    private Integer userId;

    @Column
    private Integer status;

    @Column
    private Double price;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
