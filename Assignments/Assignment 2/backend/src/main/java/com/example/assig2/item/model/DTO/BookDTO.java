package com.example.assig2.item.model.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String author = "";

    @Builder.Default
    private String genre = "";

    @Builder.Default
    private Float price = -1F;

    @Builder.Default
    private Integer quantity = -1;

    @Override
    public String toString(){
        return "Title: " + this.getTitle() + ", " +
                "Author: " + this.getAuthor() + ", " +
                "Genre: " + this.getGenre() + ", " +
                "Price: " + this.getPrice();
    }
}
