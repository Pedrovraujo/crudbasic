package com.example.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public enum Values{
        ADMIN(1L),

        BASIC(2l);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}
