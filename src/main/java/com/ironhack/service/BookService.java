package com.ironhack.service;

import com.ironhack.dto.request.CreateBookRequest;
import com.ironhack.dto.response.BookResponse;
import com.ironhack.entity.Author;
import com.ironhack.entity.Book;
import com.ironhack.exception.BadRequestException;
import com.ironhack.exception.ConflictException;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.BookMapper;
import com.ironhack.model.BookCategory;
import com.ironhack.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorService authorService, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponse create(CreateBookRequest request) {
        checkForDuplicate(request.isbn());

        Author author = authorService.findByIdOrThrow(request.authorId());

        Book book = new Book();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setCategory(request.category());
        book.setQuantity(request.quantity());
        book.setAuthor(author);

        author.setBook(book);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    private void checkForDuplicate(String isbn) {
        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new ConflictException("Book with this ISBN already exists");
        }
    }

    @Transactional(readOnly = true)
    public BookResponse getById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
        return bookMapper.toDto(book);
    }

    public Book findByIdOrThrow(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooks(String title, BookCategory category, UUID authorId) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> title == null || book.getTitle().contains(title))
                .filter(book -> category == null || book.getCategory() == category)
                .filter(book -> authorId == null || book.getAuthor().getId().equals(authorId))
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    public void decreaseQuantity(UUID id, Integer quantity) {
        Book book = findByIdOrThrow(id);

        if (book.getQuantity() < quantity) {
            throw new BadRequestException("Book quantity exceeded");
        }
        book.setQuantity(book.getQuantity() - quantity);
        bookRepository.save(book);
    }
}
