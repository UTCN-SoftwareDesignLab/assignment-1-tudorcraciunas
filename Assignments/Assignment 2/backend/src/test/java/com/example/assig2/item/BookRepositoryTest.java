package com.example.assig2.item;

import com.example.assig2.TestCreationFactory;
import com.example.assig2.item.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static com.example.assig2.TestCreationFactory.randomLong;
import static com.example.assig2.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book itemSaved = repository.save(Book.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(Integer.valueOf(String.valueOf(randomLong())))
                .price(Double.valueOf(String.valueOf(randomLong())))
                .build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);
        repository.saveAll(items);
        List<Book> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }

    @Test
    public void testSearch(){
        String string = "findMe";

        Book item = TestCreationFactory.newItem();
        Book item2 = TestCreationFactory.newItem();
        Book item3 = TestCreationFactory.newItem();

        item.setTitle(string);
        item2.setAuthor(string);
        item3.setGenre(string);

        List<Book> items = new ArrayList<>();

        items.add(item);
        items.add(item2);
        items.add(item3);

        repository.saveAll(items);

        List<Book> contains = repository.findAllByTitleContainsOrAuthorContainsOrGenreContains(string, string, string);

        Assertions.assertEquals(items.size(), contains.size());
    }
}