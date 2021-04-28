package com.example.assig2.item;

import com.example.assig2.BaseControllerTest;
import com.example.assig2.TestCreationFactory;
import com.example.assig2.item.model.DTO.BookDTO;
import com.example.assig2.report.CSVReportService;
import com.example.assig2.report.PdfReportService;
import com.example.assig2.report.ReportServiceFactory;
import com.example.assig2.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.assig2.TestCreationFactory.randomLong;
import static com.example.assig2.TestCreationFactory.randomString;
import static com.example.assig2.UrlMapping.*;
import static com.example.assig2.report.ReportType.CSV;
import static com.example.assig2.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        //List<BookDTO> items = TestCreationFactory.listOf(Book.class);
        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.findAllDTO()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(ITEMS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "";

        String csvResponse = "";

        ResultActions pdfExport = mockMvc.perform(get(ITEMS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(ITEMS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(12f)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(ITEMS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqItem = TestCreationFactory.newItemDTO();

        when(bookService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(ITEMS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        BookDTO reqItem = TestCreationFactory.newItemDTO();
        when(bookService.findByIdDto(reqItem.getId())).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(ITEMS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(ITEMS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }


}