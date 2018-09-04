package com.example.demo3.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_memners")
@EqualsAndHashCode(of = "uid")
public class Memner {

    @Id
    private String uid;
    private String upw;
    private String uname;

}
