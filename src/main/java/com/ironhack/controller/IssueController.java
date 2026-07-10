package com.ironhack.controller;

import com.ironhack.dto.response.BookResponse;
import com.ironhack.dto.request.IssueBookRequest;
import com.ironhack.dto.response.IssueResponse;
import com.ironhack.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<IssueResponse> issueBookToStudent(
            @Valid @RequestBody IssueBookRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.issueBook(request));
    }

    @GetMapping("/usn/{usn}/books")
    public ResponseEntity<List<BookResponse>> listBooksByUsn(
            @PathVariable String usn
    ) {
        return ResponseEntity.ok(issueService.listBooksByUsn(usn));
    }
}
