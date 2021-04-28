package com.example.assig2.item;

import com.example.assig2.item.model.Book;
import com.example.assig2.item.model.DTO.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<BookDTO> findAllDTO(){
        List<Book> books = bookRepository.findAll();


        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public void addDummyData(){
        Random random = new Random();
        bookRepository.save(
                Book.builder()
                .author(String.valueOf(random.nextInt()))
                .genre(String.valueOf(random.nextInt()))
                .title(String.valueOf(random.nextInt()))
                .price(random.nextDouble())
                .quantity(random.nextInt())
                .build()
        );
    }

    private Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book id not found: " + id));
    }

    public BookDTO findByIdDto(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public BookDTO create(BookDTO bookDTO){
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)
        ));
    }

    public List<BookDTO> findOutOfStock(){
        return bookRepository.findAllByQuantity(0)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO edit(Long id, BookDTO bookDTO){
        Book book = findById(bookDTO.getId());

        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(Double.valueOf(bookDTO.getPrice()));

        if(bookDTO.getQuantity() >= 0){
            book.setQuantity(bookDTO.getQuantity());
        }

        return bookMapper.toDto(bookRepository.save(book));
    }


    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public List<BookDTO> searchByTitleOrAuthorOrGenre(String search){
        return bookRepository.findAllByTitleContainsOrAuthorContainsOrGenreContains(search, search, search).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

    }
}

