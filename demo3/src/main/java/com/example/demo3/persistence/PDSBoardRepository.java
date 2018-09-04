package com.example.demo3.persistence;

import com.example.demo3.vo.PDSBoard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {

    @Modifying
    @Query("UPDATE FROM PDSFile f set f.pdsfile = ?2 WHERE f.fno = ?1")
    int updatePDSFile(Long fno, String newFilewName);

    @Modifying
    @Query("DELETE FROM PDSFile f WHERE f.fno = ?1")
    int deletePDSFile(Long fno);
}
