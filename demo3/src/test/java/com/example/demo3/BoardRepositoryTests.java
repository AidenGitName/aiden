package com.example.demo3;

import com.example.demo3.persistence.BoardRepository;
import com.example.demo3.vo.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void inspect(){

        //클래스 이름
        Class<?> clz =  boardRepository.getClass();

        System.out.println(clz.getInterfaces());

        //클래스가 구현하고있는 인터페이스 목록
        Class<?>[] interfaces = clz.getInterfaces();

        Stream.of(interfaces).forEach(inter -> System.out.println(inter.getName()));

        // 클래스의 부모클래스
        Class<?> superClasses = clz.getSuperclass();

        System.out.println(superClasses.getName());
    }

    @Test
    public void testInsert(){
        Board board = new Board();
        board.setTitle("게시물의 제목");
        board.setContent("게시물 내용 넣기 ...");
        board.setWriter("user00");

        boardRepository.save(board);
    }

    @Test
    public void testRead(){
        boardRepository.findById(4L).ifPresent((board -> {
            System.out.println(board);
        }));
    }

    @Test
    public void testUpdate(){
        System.out.println("Read First ...............");
        Optional<Board> board = boardRepository.findById(1L);

        System.out.println("Update Title...........");
        board.ifPresent((board1 -> board1.setTitle("수정된 제목입니다.")));

        System.out.println("Call save() ................");
        boardRepository.save(board.get());

    }

    @Test
    public void testDelete(){
        System.out.println("DELETE Entity");

        boardRepository.deleteById(2L);
    }

    @Test
    public void testInsert200(){
        for(int i = 1; i <= 200; i++) {
            Board board = new Board();
            board.setTitle("제목.."+i);
            board.setContent("내용...."+i+" 채우기");
            board.setWriter(("user0"+(i %10)));
            boardRepository.save(board);
        }
    }

    @Test
    public void testByTitle(){
        boardRepository.findBoardByTitle("제목..177").forEach(board -> {
            System.out.println(board);
        });
    }

    // 컬랙션 활용 리스트에 담기
    @Test
    public void testBtWriter(){
        Collection<Board> result = boardRepository.findByWriter("user00");
        result.forEach(board -> System.out.println(board));
    }

    @Test
    public void testByWriterContaining(){
        Collection<Board> result = boardRepository.findByWriterContaining("05");

        // for 루프 대신 foreach
        result.forEach(board -> System.out.println(board));
    }

    @Test
    public void testByTitleContainingOrContentContaing(){
        Collection<Board> result = boardRepository.findByTitleContainingOrContentContaining("5","7");
        result.forEach(board -> System.out.println(board));
    }

    @Test
    public void testByTitleAndBno(){
        Collection<Board> results = boardRepository.findByTitleContainingAndBnoGreaterThan("5",50L);

        results.forEach(board -> System.out.println(board));
    }

    @Test
    public void testBnoOrderBy(){
        Collection<Board> result = boardRepository.findByBnoGreaterThanOrderByBnoDesc(100L);
        result.forEach(board -> System.out.println(board));
    }

    @Test
    public void testBnoOrderByPaging(){

        Pageable paging = PageRequest.of(0,10);

        Collection<Board> results = boardRepository.findByBnoGreaterThanOrderByBnoDesc(0L,paging);
        results.forEach(board -> System.out.println(board));
    }



}
