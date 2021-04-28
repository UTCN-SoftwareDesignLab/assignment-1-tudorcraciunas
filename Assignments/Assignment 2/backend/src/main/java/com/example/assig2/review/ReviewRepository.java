package com.example.assig2.review;


import com.example.assig2.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Set<Review> findAllByBook_Id(Long itemId);
}
