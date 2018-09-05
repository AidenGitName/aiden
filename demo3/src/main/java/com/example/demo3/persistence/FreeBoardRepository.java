package com.example.demo3.persistence;

import com.example.demo3.vo.FreeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long> {

    List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable paging);

    @Query("SELECT b.bno, b.title, count(r) FROM FreeBoard b LEFT OUTER JOIN b.replies r where b.bno > 0 GROUP BY b")
    List<Object[]> getPage(Pageable page);
}
