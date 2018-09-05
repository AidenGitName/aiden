package com.example.demo3;

import com.example.demo3.persistence.WebBoardRepository;
import com.example.demo3.vo.WebBoard;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositortTests {

    @Autowired
    private WebBoardRepository repo;

    @Test
    public void testDummyInsert(){
        IntStream.range(1,310).forEach(i->{
            WebBoard board = new WebBoard();
            board.setTitle("Title . . " + i);
            board.setContent("Contet ... " + i);
            board.setWriter("user" + i);

            repo.save(board);

        });
    }
}
