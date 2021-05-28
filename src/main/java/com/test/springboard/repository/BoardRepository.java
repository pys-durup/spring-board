package com.test.springboard.repository;

import com.test.springboard.model.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
    List<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);

}
