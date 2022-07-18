package com.company.university.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.view.MenuView;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuView menuView;
    @InjectMocks
    private MenuController menuController;
    
    @Test
    void provideMenuView_shouldReturnChoice_whenStatementCorrect() {
        when(menuView.provideView()).thenReturn(1);
        int actual = menuController.provideMenuView();
        int expected = 1;
        assertThat(actual, equalTo(expected));
    }
}
