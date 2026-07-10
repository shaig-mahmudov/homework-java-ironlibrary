package com.ironhack.service;

import com.ironhack.dto.response.BookResponse;
import com.ironhack.dto.request.IssueBookRequest;
import com.ironhack.dto.response.IssueResponse;
import com.ironhack.entity.Book;
import com.ironhack.entity.Issue;
import com.ironhack.entity.Student;
import com.ironhack.exception.ConflictException;
import com.ironhack.mapper.BookMapper;
import com.ironhack.mapper.IssueMapper;
import com.ironhack.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final BookService bookService;
    private final StudentService studentService;
    private final IssueMapper issueMapper;
    private final BookMapper bookMapper;

    public IssueService(
            IssueRepository issueRepository,
            BookService bookService,
            StudentService studentService,
            IssueMapper issueMapper,
            BookMapper bookMapper
    ) {
        this.issueRepository = issueRepository;
        this.bookService = bookService;
        this.studentService = studentService;
        this.issueMapper = issueMapper;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public IssueResponse issueBook(IssueBookRequest request) {
        Book book = bookService.findByIdOrThrow(request.bookId());
        Student student = studentService.findByIdOrThrow(request.studentId());

        checkForDuplicate(student, book);

        bookService.decreaseQuantity(book.getId(), 1);

        Issue issue = new Issue();
        issue.setIssueDate(request.issueDate());
        issue.setReturnDate(request.returnDate());
        issue.setBook(book);
        issue.setStudent(student);

        Issue savedIssue = issueRepository.save(issue);
        return issueMapper.toDto(savedIssue);
    }

    private void checkForDuplicate(Student student, Book book) {
        if (issueRepository.existsByStudent_Id(student.getId())) {
            throw new ConflictException("Student already has an issued book");
        }
        if (issueRepository.existsByBook_Id(book.getId())) {
            throw new ConflictException("Book is already issued");
        }
    }

    @Transactional(readOnly = true)
    public List<BookResponse> listBooksByUsn(String usn) {
        return issueRepository.findByStudent_Usn(usn)
                .stream()
                .map(Issue::getBook)
                .map(bookMapper::toDto)
                .toList();
    }
}
