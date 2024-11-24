package org.example.yogabusinessmanagementweb.repositories;

import org.example.yogabusinessmanagementweb.common.entities.Address;
import org.example.yogabusinessmanagementweb.common.entities.Comment;

import org.example.yogabusinessmanagementweb.common.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findByProductAndRatePointGreaterThan(Pageable pageable, Product product, int ratePoint);
    List<Comment> findByParentCommentId(Long parentCommentId);
    Page<Comment> findByProduct(Pageable pageable, Product product);

}
