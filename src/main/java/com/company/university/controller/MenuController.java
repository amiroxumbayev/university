package com.company.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.view.MenuView;

@Component
public class MenuController {

    private final MenuView menuView;
    
    @Autowired
    public MenuController(MenuView menuView) {
        this.menuView = menuView;
    }

    public int provideMenuView() {
        return menuView.provideView();
    }
}
