package com.example.demo3.persistence;

import com.example.demo3.vo.WebBoard;
import com.example.demo3.vo.WebReply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WepReplyRepository extends CrudRepository<WebReply, Long> {

    @Query("SELECT r FROM WebReply r WHERE r.board = ?1 AND r.rno > 0 ORDER BY r.rno ASC ")
    List<WebReply> getRepliesOfBoard(WebBoard board);
}
