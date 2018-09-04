package com.example.demo3.persistence;

import com.example.demo3.vo.Board;
import org.springframework.data.domain.Pageable;
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

}
