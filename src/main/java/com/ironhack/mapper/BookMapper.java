package com.ironhack.mapper;

import com.ironhack.dto.response.BookResponse;
import com.ironhack.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookResponse toDto(Book book) {
        return new BookResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getCategory(),
                book.getQuantity(),
                authorMapper.toDto(book.getAuthor())
        );
    }
}
