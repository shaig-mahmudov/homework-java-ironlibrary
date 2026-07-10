package com.ironhack.mapper;

import com.ironhack.dto.response.IssueResponse;
import com.ironhack.entity.Issue;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {
    private final BookMapper bookMapper;
    private final StudentMapper studentMapper;

    public IssueMapper(BookMapper bookMapper, StudentMapper studentMapper) {
        this.bookMapper = bookMapper;
        this.studentMapper = studentMapper;
    }

    public IssueResponse toDto(Issue issue) {
        return new IssueResponse(
                issue.getId(),
                issue.getIssueDate(),
                issue.getReturnDate(),
                bookMapper.toDto(issue.getBook()),
                studentMapper.toDto(issue.getStudent())
        );
    }
}
