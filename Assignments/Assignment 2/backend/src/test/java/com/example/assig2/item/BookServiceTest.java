package com.example.assig2.item;

import com.example.assig2.TestCreationFactory;
import com.example.assig2.item.model.Book;
import com.example.assig2.item.model.DTO.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {

        List<Book> items = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(items);

        List<BookDTO> all = bookService.findAllDTO();

        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void create(){
        BookDTO bookDTO = TestCreationFactory.newItemDTO();
        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        BookDTO bookDTO1 = bookService.create(bookDTO);

        Assertions.assertEquals(bookDTO1, bookDTO);
    }

    @Test
    void addDummyData() {
        bookService.addDummyData();

        verify(bookRepository, times(1)).save(
                any()
        );
    }
}