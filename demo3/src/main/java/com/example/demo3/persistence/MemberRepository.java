package com.example.demo3.persistence;

import com.example.demo3.vo.Memner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Memner, String> {

    @Query("SELECT m.uid, count(p) FROM Memner m LEFT OUTER JOIN Profile p ON m.uid = p.memner WHERE m.uid = ?1 GROUP BY m")
    List<Object[]> getMemnerByWithProfileCount(String uid);

    @Query("SELECT m,p FROM Memner m LEFT OUTER JOIN Profile p ON m.uid = p.memner WHERE m.uid = ?1 AND p.current = true")
    List<Object[]> gerMemnerByWithProfile(String uid);

}
