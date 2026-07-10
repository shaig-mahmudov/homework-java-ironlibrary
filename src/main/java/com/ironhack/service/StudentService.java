package com.ironhack.service;

import com.ironhack.dto.request.CreateStudentRequest;
import com.ironhack.dto.response.StudentResponse;
import com.ironhack.entity.Student;
import com.ironhack.exception.ConflictException;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.StudentMapper;
import com.ironhack.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public StudentResponse create(CreateStudentRequest request) {
        checkForDuplicate(request.usn());

        Student student = new Student();
        student.setUsn(request.usn());
        student.setName(request.name());

        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(UUID id) {
        Student student = findByIdOrThrow(id);
        return studentMapper.toDto(student);
    }

    public Student findByIdOrThrow(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
    }

    private void checkForDuplicate(String usn) {
        if (studentRepository.existsByUsn(usn)) {
            throw new ConflictException("Student with this usn already exists");
        }
    }
}
