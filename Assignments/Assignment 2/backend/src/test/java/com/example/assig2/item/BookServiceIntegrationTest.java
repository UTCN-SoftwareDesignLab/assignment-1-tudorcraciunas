package com.example.assig2.item;

import com.example.assig2.TestCreationFactory;
import com.example.assig2.item.model.Book;
import com.example.assig2.item.model.DTO.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

    }

    @Test
    void findAll() {

        List<Book> items = new ArrayList<>();
        int nrItems = 10;
        for(int i = 0; i < nrItems; i++){
            bookRepository.save(Book.builder()
                    .title(String.valueOf(i))
                    .genre("genre:" + i)
                    .price((double) i)
                    .author("author" + i)
                    .quantity(i)
                    .build());
        }

        List<Book> all = bookService.findAll();

        Assertions.assertEquals(10, all.size());
    }


    @Test
    void create(){
        BookDTO book = TestCreationFactory.newItemDTO();
        BookDTO bookDTO = bookService.create(book);

        Assertions.assertEquals(bookService.findAll().size(), 1);
        Assertions.assertNotNull(bookDTO);
    }

    @Test
    void edit(){
        BookDTO   bookDTO = TestCreationFactory.newItemDTO();
        BookDTO bookDTO1 = TestCreationFactory.newItemDTO();

        BookDTO itemDTO = bookService.create(bookDTO);
        BookDTO itemDTO1 = bookService.create(bookDTO1);

        Assertions.assertNotEquals(itemDTO, itemDTO1);

        itemDTO = bookService.edit(itemDTO.getId(), itemDTO1);
        itemDTO.setId(itemDTO1.getId());

        Assertions.assertEquals(itemDTO, itemDTO1);
    }


    @Test
    void delete(){
        BookDTO item = TestCreationFactory.newItemDTO();
        BookDTO itemDTO = bookService.create(item);

        Assertions.assertEquals(bookService.findAll().size(), 1);
        bookService.delete(itemDTO.getId());
        Assertions.assertEquals(bookService.findAll().size(), 0);
    }

    @Test
    void findOutOfStock(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        for (Book book: books)
            book.setQuantity(0);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findOutOfStock();

        Assertions.assertEquals(books.size(), all.size());
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

        bookRepository.saveAll(items);

        List<Book> contains = bookRepository.findAllByTitleContainsOrAuthorContainsOrGenreContains(string, string, string);

        Assertions.assertEquals(items.size(), contains.size());
    }
    @Test
    void addDummyData() {
    }
}