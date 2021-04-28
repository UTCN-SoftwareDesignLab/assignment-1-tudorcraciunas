package com.example.assig2.report;

import com.example.assig2.item.model.DTO.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.List;

import static com.example.assig2.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    @Override
    public ReportType getType() {
        return CSV;
    }

    @Override
    public void export(List<BookDTO> bookDTOList) {
        try{
            CSVWriter csvWriter = new CSVWriter(new FileWriter("OutOfStock.csv"));
            for(BookDTO bookDTO : bookDTOList){
                csvWriter.writeNext(new String[]{String.valueOf(bookDTO.getTitle())});
                csvWriter.writeNext(new String[]{String.valueOf(bookDTO.getAuthor())});
                csvWriter.writeNext(new String[]{String.valueOf(bookDTO.getGenre())});
                csvWriter.writeNext(new String[]{String.valueOf(bookDTO.getPrice())});
            }
            csvWriter.flush();
            csvWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
