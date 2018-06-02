package com.yoogurt.kettle.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "authorized_user")
public class AuthorizedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user")
    private String user;

    @Column(name="token")
    private String token;

    @Column(name="status")
    private String status;

    @Column(name="gmt_create")
    private Date gmtCreate;

    @Column(name="gmt_modify")
    private Date gmtModify;
}
