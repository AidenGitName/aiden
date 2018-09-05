package com.example.demo3.controller;

import com.example.demo3.persistence.WepReplyRepository;
import com.example.demo3.vo.WebBoard;
import com.example.demo3.vo.WebReply;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replies/*")
@Log
public class WebReplyController {

    @Autowired
    private WepReplyRepository replyRepo;


    @PostMapping("/{bno}")
    public ResponseEntity<Void> addReply(@PathVariable("bno")Long bno, @RequestBody WebReply reply){

        log.info("AddReply........................");
        log.info("BNO: " + bno);
        log.info("REPLY: " +reply);

        WebBoard board = new WebBoard();
        board.setBno(bno);

        reply.setBoard(board);

        replyRepo.save(reply);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
