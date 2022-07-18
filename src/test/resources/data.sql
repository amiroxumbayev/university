INSERT INTO public.subject (name, credit) values ('test', 1), ('test1', 1);
INSERT INTO public.Faculty (title) values ('test'), ('test1');
INSERT INTO public.Group_ (name, faculty_id) values('test', 1), ('test1', 1);
INSERT INTO public.Teacher (name, email, password) values ('test', 'test', 'test'), ('test1', 'test1', 'test1');
INSERT INTO public.Teacher_Group (teacher_id, group_id) values (1, 1);
INSERT INTO public.Student (name, email, password, year, avg_gpa) values ('test', 'test', 'test', 2022, 4.), ('test1', 'test1', 'test1', 2022, 4.);
INSERT INTO public.Group_Student (group_id, student_id) values (1, 1);
INSERT INTO public.Task (group_id, subject_id, title, type, description) values (1, 1, 'test', 'laboratory', 'test');
INSERT INTO public.Schedule (group_id, subject_id, day_week, start_time, end_time) values (1, 1, 'MONDAY', '2022-06-02 01:01:00', '2022-06-02 02:01:00'), (1, 1, 'TUESDAY', '2022-06-02 01:01:00', '2022-06-02 02:01:00');