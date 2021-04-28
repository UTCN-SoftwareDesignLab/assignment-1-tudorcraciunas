package com.example.assig2.item;

import com.example.assig2.item.model.Book;
import com.example.assig2.item.model.DTO.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book item);

    @Mappings({
            @Mapping(target = "reviews", ignore = true)
    })
    Book fromDto(BookDTO item);

}
