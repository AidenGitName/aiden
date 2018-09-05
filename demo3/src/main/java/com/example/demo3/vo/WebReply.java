package com.example.demo3.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
@Table(name = "tbl_webreplies")
@EqualsAndHashCode(of = "rno")
public class WebReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyText;
    private String replyer;

    @CreationTimestamp
    private Timestamp regdate;
    @UpdateTimestamp
    private Timestamp updatedate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private WebBoard board;

}
