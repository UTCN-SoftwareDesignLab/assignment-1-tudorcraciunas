package com.example.assig2.item;

import com.example.assig2.item.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitleContainsOrAuthorContainsOrGenreContains(String title, String author, String genre);

    List<Book> findAllByQuantity(Integer quantity);
}
