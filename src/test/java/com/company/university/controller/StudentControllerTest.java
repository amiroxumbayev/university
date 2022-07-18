package com.company.university.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.domain.Student;
import com.company.university.service.StudentService;
import com.company.university.view.StudentView;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentView studentView;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    
    @Test
    void provideStudentView_shouldChooseFindAllOption_whenStatementCorrect() {
        doReturn(1).doReturn(5).when(studentView).provideView();
        when(studentService.findAll()).thenReturn(new ArrayList<>());
        studentController.provideView();
        verify(studentView, times(2)).provideView();
        verify(studentService).findAll();
    }
    
    @Test
    void provideStudentView_shouldChooseSaveOption_whenStatementCorrect() {
        doReturn(2).doReturn(5).when(studentView).provideView();
        when(studentView.createEntity()).thenReturn(Student.builder().build());
        studentController.provideView();
        verify(studentView, times(2)).provideView();
        verify(studentView).createEntity();
    }
    
    @Test
    void provideStudentView_shouldChooseUpdateOption_whenStatementCorrect() {
        doReturn(3).doReturn(5).when(studentView).provideView();
        when(studentView.updateEntity()).thenReturn(Student.builder().build());
        studentController.provideView();
        verify(studentView, times(2)).provideView();
        verify(studentView).updateEntity();
    }
    
    @Test
    void provideStudentView_shouldChooseDeleteByIdOption_whenStatementCorrect() {
        doReturn(4).doReturn(5).when(studentView).provideView();
        when(studentView.deleteEntity()).thenReturn(1L);
        studentController.provideView();
        verify(studentView, times(2)).provideView();
        verify(studentView).deleteEntity();
    }

}
