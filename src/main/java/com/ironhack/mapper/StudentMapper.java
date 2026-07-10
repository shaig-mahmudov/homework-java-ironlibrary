package com.ironhack.mapper;

import com.ironhack.dto.response.StudentResponse;
import com.ironhack.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse toDto(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getUsn(),
                student.getName()
        );
    }
}
