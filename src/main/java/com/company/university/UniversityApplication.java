package com.company.university;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import com.company.university.controller.FacultyController;
import com.company.university.controller.GroupController;
import com.company.university.controller.MenuController;
import com.company.university.controller.StudentController;
import com.company.university.controller.SubjectController;
import com.company.university.controller.TeacherController;

@ComponentScan
public class UniversityApplication {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UniversityApplication.class);
        MenuController menuController = applicationContext.getBean(MenuController.class);
        boolean isFinish = false;
        while (!isFinish) {
            int choice = menuController.provideMenuView();
            if (choice == 1) {
                SubjectController subjectController = applicationContext.getBean(SubjectController.class);
                subjectController.provideView();
            } else if (choice == 2) {
                FacultyController facultyController = applicationContext.getBean(FacultyController.class);
                facultyController.provideView();
            } else if (choice == 3) {
                StudentController studentController = applicationContext.getBean(StudentController.class);
                studentController.provideView();
            } else if (choice == 4) {
                GroupController groupController = applicationContext.getBean(GroupController.class);
                groupController.provideView();
            } else if (choice == 5) {
                TeacherController teacherController = applicationContext.getBean(TeacherController.class);
                teacherController.provideView();
            } else if (choice == 6) {
                isFinish = true;
            }
        }
    }
}
