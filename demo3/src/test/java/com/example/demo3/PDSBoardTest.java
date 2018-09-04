package com.example.demo3;

import com.example.demo3.persistence.PDSBoardRepository;
import com.example.demo3.vo.PDSBoard;
import com.example.demo3.vo.PDSFile;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit //변경사항 커밋
public class PDSBoardTest {
    @Autowired
    PDSBoardRepository repo;

    @Test
    public void testInsertPDS(){
        PDSBoard pds = new PDSBoard();
        pds.setPname("Document");

        PDSFile file1 = new PDSFile();
        file1.setPdsfile("file1.doc");

        PDSFile file2 = new PDSFile();
        file2.setPdsfile("file2.doc");

        pds.setFiles(Arrays.asList(file1,file2));

        log.info("try to save pds");

        repo.save(pds);

    }

    @Transactional // 기본이 롤백    @Test
    public void testUpdateFileName1(){
        Long fno = 1L;
        String newName = "updateFile1.doc";

        int count = repo.updatePDSFile(fno, newName);
        // @Log 설정 이후 사용가능
        log.info("Update count : " + count);
    }
    // 순수한 객체를 이용한 수정
    @Transactional
    @Test
    public void testUpdateFileName2(){

        String newName = "updateFile2.doc";
        // 반드시 번호가 존재하는지 확인할 것
        Optional<PDSBoard> result = repo.findById(2L);

        result.ifPresent(pds -> {
            log.info("데이터가 존재하므로 update 시도");

            PDSFile target = new PDSFile();
            target.setFno(2L);
            target.setPdsfile(newName);

            int idx = pds.getFiles().indexOf(target);

            if(idx > -1) {
                List<PDSFile> list = pds.getFiles();
                list.remove(idx);
                list.add(target);
                pds.setFiles(list);
            }
            repo.save(pds);
        });
    }

    @Transactional
    @Test
    public void deleteFile(){
        Long fno =2L;

        int count = repo.deletePDSFile(fno);

        log.info("DELETE PDSFile: " +count);
    }

    //Join 처리
    @Test
    public void insertDummies(){

        List<PDSBoard> list = new ArrayList<>();
        IntStream.range(1,100).forEach(i -> {
            PDSBoard pds = new PDSBoard();
            pds.setPname("자료" + i);

            PDSFile file1 = new PDSFile();
            file1.setPdsfile("file1.doc");

            PDSFile file2 = new PDSFile();
            file2.setPdsfile("file2.doc");

            pds.setFiles(Arrays.asList(file1,file2));

            log.info("try to save pds");

            list.add(pds);
        });

        repo.saveAll(list); // 한번에 여러개의 데이터를 insert
    }
}
