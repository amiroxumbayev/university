package com.company.university.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.domain.Faculty;
import com.company.university.domain.Group;
import com.company.university.domain.Subject;
import com.company.university.service.FacultyService;
import com.company.university.service.GroupService;
import com.company.university.service.SubjectService;
import com.company.university.view.GroupView;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {
    
    @Mock
    private GroupView groupView;
    @Mock
    private GroupService groupService;
    @Mock
    private FacultyService facultyService;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private GroupController groupController;

    @Test
    void provideGroupView_shouldChooseFindAllOption_whenStatementCorrect() {
        doReturn(1).doReturn(5).when(groupView).provideView();
        when(groupService.findAll()).thenReturn(new ArrayList<>());
        groupController.provideView();
        verify(groupView, times(2)).provideView();
        verify(groupService).findAll();
    }

    @Test
    void provideGroupView_shouldChooseSaveOption_whenStatementCorrect() {
        List<Subject> subjects = new ArrayList<>();
        Subject subject = new Subject(1L, null, 0);
        subjects.add(subject);
        doReturn(2).doReturn(5).when(groupView).provideView();
        when(groupView.createEntity()).thenReturn(Group.builder().build());
        mockProvideChooseFaculty();
        when(subjectService.findAll()).thenReturn(subjects);
        when(groupView.makeSchedule(subjects)).thenReturn(1L);
        when(subjectService.findById(1L)).thenReturn(subject);
        when(groupView.fillSubjectSchedule(subject)).thenReturn(new ArrayList<>());
        when(groupView.makeSubjectSchedule()).thenReturn("no");
        when(groupView.makeWorkPrograms(subjects)).thenReturn(1L);
        when(groupView.fillSubjectWorkProgram(subject)).thenReturn(new ArrayList<>());
        when(groupView.makeSubjectWorkProgram()).thenReturn("no");
        groupController.provideView();
        verify(groupView, times(2)).provideView();
        verify(groupView).createEntity();
        verifyProvideChooseFaculty();
        verify(subjectService, times(2)).findAll();
        verify(groupView).makeSchedule(subjects);
        verify(subjectService, times(2)).findById(1L);
        verify(groupView).fillSubjectSchedule(subject);
        verify(groupView).makeSubjectSchedule();
        verify(groupView).makeWorkPrograms(subjects);
        verify(groupView).fillSubjectWorkProgram(subject);
        verify(groupView).makeSubjectWorkProgram();
    }

    @Test
    void provideGroupView_shouldChooseUpdateOption_whenStatementCorrect() {
        doReturn(3).doReturn(5).when(groupView).provideView();
        when(groupView.updateEntity()).thenReturn(Group.builder().build());
        mockProvideChooseFaculty();
        groupController.provideView();
        verify(groupView, times(2)).provideView();
        verify(groupView).updateEntity();
        verifyProvideChooseFaculty();
    }

    @Test
    void provideGroupView_shouldChooseDeleteByIdOption_whenStatementCorrect() {
        doReturn(4).doReturn(5).when(groupView).provideView();
        when(groupView.deleteEntity()).thenReturn(1L);
        groupController.provideView();
        verify(groupView, times(2)).provideView();
        verify(groupView).deleteEntity();
    }
    
    private  List<Faculty> getFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        Faculty faculty = new Faculty(1L, null, null);
        faculties.add(faculty);
        return faculties;
    }
    
    private  void mockProvideChooseFaculty() {
        List<Faculty> faculties = getFaculties();
        when(facultyService.findAll()).thenReturn(faculties);
        when(groupView.chooseFaculty(faculties)).thenReturn(1L);
        when(facultyService.findById(1L)).thenReturn(faculties.get(0));
    }
    
    private void verifyProvideChooseFaculty() {
        verify(facultyService).findAll();
        verify(groupView).chooseFaculty(getFaculties());
        verify(facultyService).findById(1L);
    }

}
