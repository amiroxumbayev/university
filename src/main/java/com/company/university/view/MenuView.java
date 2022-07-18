package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.company.university.annotation.View;

@View
public class MenuView {
    
    public int provideView() {
        System.out.println("University");
        System.out.println("Enter number to select:");
        drawMenu();
        int choice = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        if (!(choice >= 1 && choice <= 6)) {
            System.out.println("Incorrect input");
        }
        return choice;
    }

    private void drawMenu() {
        System.out.println("1. Subject");
        System.out.println("2. Faculty");
        System.out.println("3. Student");
        System.out.println("4. Group");
        System.out.println("5. Teacher");
        System.out.println("6. Finish");
    }
}
