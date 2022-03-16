package project.app.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Integer type;

    @Column
    private String firstname;

    @Column
    private String surname;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private Timestamp lastLoginAt;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;

}
