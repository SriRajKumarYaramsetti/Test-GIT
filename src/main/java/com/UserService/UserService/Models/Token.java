package com.UserService.UserService.Models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
}
