package com.yoogurt.kettle.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "sync_record")
public class SyncRecord {

    @Id
    @Column(name="sync")
    private String sync;

    @Column(name="from_db")
    private String fromDb;

    @Column(name="to_db")
    private String toDb;

    @Column(name="user")
    private String user;

    @Column(name="token")
    private String token;

    @Column(name="ipv4")
    private String ipv4;

    @Column(name="status")
    private String status;

    @Column(name="gmt_create")
    private Date gmtCreate;

    @Column(name="gmt_modify")
    private Date gmtModify;

}
