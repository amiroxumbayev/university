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

import com.company.university.domain.Teacher;
import com.company.university.service.GroupService;
import com.company.university.service.TeacherService;
import com.company.university.domain.Group;
import com.company.university.view.TeacherView;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherView teacherView;
    @Mock
    private TeacherService teacherService;
    @Mock
    private GroupService groupService;
    @InjectMocks
    private TeacherController teacherController;
    
    @Test
    void provideTeacherView_shouldChooseFindAllOption_whenStatementCorrect() {
        doReturn(1).doReturn(5).when(teacherView).provideView();
        when(teacherService.findAll()).thenReturn(new ArrayList<>());
        teacherController.provideView();
        verify(teacherView, times(2)).provideView();
        verify(teacherService).findAll();
    }
    
    @Test
    void provideTeacherView_shouldChooseSaveOption_whenStatementCorrect() {
        List<Group> groups = new ArrayList<>();
        Group group = Group.builder().withId(1L).build();
        groups.add(group);
        doReturn(2).doReturn(5).when(teacherView).provideView();
        when(teacherView.createEntity()).thenReturn(Teacher.builder().build());
        when(groupService.findAll()).thenReturn(groups);
        doReturn(1L).doReturn(0L).when(teacherView).attachGroups(groups);
        when(groupService.findById(1L)).thenReturn(group);
        teacherController.provideView();
        verify(teacherView, times(2)).provideView();
        verify(teacherView).createEntity();
        verify(groupService).findAll();
        verify(teacherView, times(2)).attachGroups(groups);
        verify(groupService).findById(1L);
    }
    
    @Test
    void provideTeacherView_shouldChooseUpdateOption_whenStatementCorrect() {
        doReturn(3).doReturn(5).when(teacherView).provideView();
        when(teacherView.updateEntity()).thenReturn(Teacher.builder().build());
        teacherController.provideView();
        verify(teacherView, times(2)).provideView();
        verify(teacherView).updateEntity();
    }
    
    @Test
    void provideTeacherView_shouldChooseDeleteByIdOption_whenStatementCorrect() {
        doReturn(4).doReturn(5).when(teacherView).provideView();
        when(teacherView.deleteEntity()).thenReturn(1L);
        teacherController.provideView();
        verify(teacherView, times(2)).provideView();
        verify(teacherView).deleteEntity();
    }
}
