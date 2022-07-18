package com.company.university.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.domain.Faculty;
import com.company.university.domain.Subject;
import com.company.university.service.FacultyService;
import com.company.university.service.SubjectService;
import com.company.university.view.FacultyView;

@ExtendWith(MockitoExtension.class)
class FacultyControllerTest {

    @Mock
    private FacultyView facultyView;
    @Mock
    private FacultyService facultyService;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private FacultyController facultyController;
    
    @Test
    void provideFacultyView_shouldChooseFindAllOption_whenStatementCorrect() {
        doReturn(1).doReturn(5).when(facultyView).provideView();
        when(facultyService.findAll()).thenReturn(new ArrayList<>());
        facultyController.provideView();
        verify(facultyView, times(2)).provideView();
        verify(facultyService).findAll();
    }
    
    @Test
    void provideFacultyView_shouldChooseSaveOption_whenStatementCorrect() {
        List<Subject> subjects = new ArrayList<>();
        Subject subject = new Subject(1L, null, 0);
        subjects.add(subject);
        doReturn(2).doReturn(5).when(facultyView).provideView();
        when(facultyView.createEntity()).thenReturn(new Faculty(null, null, null));
        when(subjectService.findAll()).thenReturn(subjects);
        doReturn(1L).doReturn(0L).when(facultyView).chooseSubjects(subjects);
        when(subjectService.findById(1L)).thenReturn(subject);
        facultyController.provideView();
        verify(facultyView, times(2)).provideView();
        verify(facultyView).createEntity();
        verify(subjectService).findAll();
        verify(facultyView, times(2)).chooseSubjects(subjects);
        verify(subjectService).findById(1L);
    }
    
    @Test
    void provideFacultyView_shouldChooseUpdateOption_whenStatementCorrect() {
        doReturn(3).doReturn(5).when(facultyView).provideView();
        when(facultyView.updateEntity()).thenReturn(new Faculty(null, null, null));
        facultyController.provideView();
        verify(facultyView, times(2)).provideView();
        verify(facultyView).updateEntity();
    }
    
    @Test
    void provideFacultyView_shouldChooseDeleteByIdOption_whenStatementCorrect() {
        doReturn(4).doReturn(5).when(facultyView).provideView();
        when(facultyView.deleteEntity()).thenReturn(1L);
        facultyController.provideView();
        verify(facultyView, times(2)).provideView();
        verify(facultyView).deleteEntity();
    }

}
