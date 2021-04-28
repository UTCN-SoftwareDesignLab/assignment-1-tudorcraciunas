package com.example.assig2.report;

import com.example.assig2.item.model.DTO.BookDTO;

import java.util.List;

public interface ReportService {
    void export(List<BookDTO> bookDTOList);

    ReportType getType();


}
