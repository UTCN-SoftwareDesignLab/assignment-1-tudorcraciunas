package com.example.assig2.item;

import com.example.assig2.item.model.DTO.BookDTO;
import com.example.assig2.report.ReportServiceFactory;
import com.example.assig2.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.assig2.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;


    @GetMapping(value = FIRST_TEST)
    public String test(){
        bookService.addDummyData();
        return "WORKING TEST! ^^";
    }


    @GetMapping
    public List<BookDTO> findAllBooks(){
        return bookService.findAllDTO();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO){
        return bookService.create(bookDTO);
    }

    @PatchMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return bookService.edit(id, bookDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) {

        reportServiceFactory.getReportService(type).export(bookService.findOutOfStock());
    }

    @GetMapping(SEARCH)
    public List<BookDTO> searchBook(@PathVariable String string) {
        return bookService.searchByTitleOrAuthorOrGenre(string);
    }

}

