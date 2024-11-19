package com.demo.sso.domain.hospital.domain;

import com.demo.sso.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Hospital extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name="hospital_id")
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String homepage;
    @Column(precision = 10, scale = 7)
    private BigDecimal posY;
    @Column(precision = 10, scale = 7)
    private BigDecimal posX;
    private String imagePath;

//    private boolean isClosed;
//    private String code;


}
