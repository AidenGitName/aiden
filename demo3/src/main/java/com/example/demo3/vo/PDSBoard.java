package com.example.demo3.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "files")
@Entity
@Table(name = "tbl_pds")
public class PDSBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String pname;
    private String pwriter;

    @OneToMany(cascade = CascadeType.ALL) // 엔티티의 상태변화를 같이 처리
    @JoinColumn(name = "pdsno")
    private List<PDSFile> files;
}
