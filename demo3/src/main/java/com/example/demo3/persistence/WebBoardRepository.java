package com.example.demo3.persistence;

import com.example.demo3.vo.WebBoard;
import org.springframework.data.repository.CrudRepository;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long> {
}
