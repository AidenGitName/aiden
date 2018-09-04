package com.example.demo3.persistence;

import com.example.demo3.vo.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {

    List<Board> findBoardByTitle(String title);

    Collection<Board> findByWriter(String writer);

    Collection<Board> findByWriterContaining(String writer);

    //or 조건 처리
    Collection<Board> findByTitleContainingOrContentContaining(String title, String content);

    //부등호 처리
    Collection<Board> findByTitleContainingAndBnoGreaterThan(String keywoard, Long num);

    //order by bno DESC
    Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    //기본적 페이징 처리
    List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);

//    List<Board> findByBnoGreaterThan(Long bno, Pageable paging);

    //Page Type
    Page<Board> findByBnoGreaterThan(Long Bno, Pageable paging);

    // @Query 제목검색
    @Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
    List<Board> findByTitle(String title);

    // nativeQuery
    @Query(value = "select bno, title, writer,content, regdate,updatetadte from tbl_boards where title like CONCAT('%', ?1, '%') and bno > 0 order by bno desc", nativeQuery = true)
    List<Board> findByTitle3(String title);

    // native Query paging
    @Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
    List<Board> findByPage(Pageable paging);

}
