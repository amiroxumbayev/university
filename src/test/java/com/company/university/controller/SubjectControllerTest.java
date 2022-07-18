package com.company.university.controller;

import static org.junit.jupiter.api.Assertions.fail;
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

import com.company.university.domain.Subject;
import com.company.university.service.SubjectService;
import com.company.university.view.SubjectView;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectView subjectView;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SubjectController subjectController;
    
    @Test
    void provideSubjectView_shouldChooseFindAllOption_whenStatementCorrect() {
        doReturn(1).doReturn(5).when(subjectView).provideView();
        when(subjectService.findAll()).thenReturn(new ArrayList<>());
        subjectController.provideView();
        verify(subjectView, times(2)).provideView();
        verify(subjectService).findAll();
    }
    
    @Test
    void provideSubjectView_shouldChooseSaveOption_whenStatementCorrect() {
        doReturn(2).doReturn(5).when(subjectView).provideView();
        when(subjectView.createEntity()).thenReturn(new Subject(null, null, 0));
        subjectController.provideView();
        verify(subjectView, times(2)).provideView();
        verify(subjectView).createEntity();
    }
    
    @Test
    void provideSubjectView_shouldChooseUpdateOption_whenStatementCorrect() {
        doReturn(3).doReturn(5).when(subjectView).provideView();
        when(subjectView.updateEntity()).thenReturn(new Subject(null, null, 0));
        subjectController.provideView();
        verify(subjectView, times(2)).provideView();
        verify(subjectView).updateEntity();
    }
    
    @Test
    void provideSubjectView_shouldChooseDeleteByIdOption_whenStatementCorrect() {
        doReturn(4).doReturn(5).when(subjectView).provideView();
        when(subjectView.deleteEntity()).thenReturn(1L);
        subjectController.provideView();
        verify(subjectView, times(2)).provideView();
        verify(subjectView).deleteEntity();
    }
}
