package com.ironhack.service;

import com.ironhack.dto.response.AuthorResponse;
import com.ironhack.dto.request.CreateAuthorRequest;
import com.ironhack.entity.Author;
import com.ironhack.exception.ConflictException;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.AuthorMapper;
import com.ironhack.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional
    public AuthorResponse create(CreateAuthorRequest request) {
        checkForDuplicate(request.email());

        Author author = new Author();
        author.setName(request.name());
        author.setEmail(request.email());

        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDto(savedAuthor);
    }

    @Transactional(readOnly = true)
    public AuthorResponse getById(UUID id) {
        Author author = findByIdOrThrow(id);
        return authorMapper.toDto(author);
    }

    public Author findByIdOrThrow(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));
    }

    private void checkForDuplicate(String email) {
        if (authorRepository.existsByEmail(email)) {
            throw new ConflictException("Author with this email already exists");
        }
    }
}
