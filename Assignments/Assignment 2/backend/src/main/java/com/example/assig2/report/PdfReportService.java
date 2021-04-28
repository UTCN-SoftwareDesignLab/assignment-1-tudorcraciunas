package com.example.assig2.report;

import com.example.assig2.item.model.DTO.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.assig2.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {


    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public void export(List<BookDTO> bookDTOList) {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream pageContentStream;
        try {
            pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont(PDType1Font.HELVETICA, 11);
            pageContentStream.setLeading(12.5f);
            pageContentStream.newLineAtOffset(50, 700);
            pageContentStream.showText("Out of Stock:");
            pageContentStream.newLine();
            pageContentStream.newLine();
            for (BookDTO bookDTO : bookDTOList) {
                pageContentStream.showText(bookDTO.toString());
                pageContentStream.newLine();
                pageContentStream.newLine();
            }

            pageContentStream.endText();
            pageContentStream.close();

            document.save("src/main/OutOfStock.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
