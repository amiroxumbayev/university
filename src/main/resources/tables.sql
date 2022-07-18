DROP TABLE IF EXISTS public.Faculty_Subject;
DROP TABLE IF EXISTS public.Group_Student;
DROP TABLE IF EXISTS public.Teacher_Group;
DROP TABLE IF EXISTS public.Grade;
DROP TABLE IF EXISTS public.Student;
DROP TABLE IF EXISTS public.Schedule;
DROP TABLE IF EXISTS public.Task;
DROP TABLE IF EXISTS public.Teacher;
DROP TABLE IF EXISTS public.Group_;
DROP TABLE IF EXISTS public.Faculty;
DROP TABLE IF EXISTS public.Subject;

create table public.Subject(
    id SERIAL PRIMARY KEY,
    name varchar(255),
    credit int
);
create table public.Faculty(
    id SERIAL PRIMARY KEY,
    title varchar(255)
);

create table public.Faculty_Subject(
    id SERIAL PRIMARY KEY,
    faculty_id bigint not null,
    subject_id bigint not null
);

alter table public.Faculty_Subject
add foreign key (faculty_id) references public.Faculty(id);
alter table public.Faculty_Subject
add foreign key (subject_id) references public.Subject(id);

create table public.Group_(
    id SERIAL PRIMARY KEY,
    name varchar(255) not null,
    faculty_id bigint not null
);

alter table public.Group_
add foreign key (faculty_id) references public.Faculty(id);

create table public.Schedule(
    id SERIAL PRIMARY KEY,
    group_id bigint not null,
    subject_id bigint not null,
    day_week varchar(32) not null,
    start_time timestamp not null,
    end_time timestamp not null
);

alter table public.Schedule
add foreign key (subject_id) references public.Subject(id);
alter table public.Schedule
add foreign key (group_id) references public.Group_(id);

create table public.Task(
    id SERIAL PRIMARY KEY,
    group_id bigint not null,
    subject_id bigint not null,
    title varchar(255) NOT NULL,
    type varchar(32) not null,
    description varchar(255) NOT NULL
);

alter table public.Task
add foreign key (subject_id) references public.Subject(id);
alter table public.Task
add foreign key (group_id) references public.Group_(id);

create table public.Student(
    id SERIAL PRIMARY KEY,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    year int not null,
    avg_gpa float not null
);

create table public.Grade(
    id SERIAL PRIMARY KEY,
    student_id bigint not null,
    task_id bigint not null,
    score float NOT NULL
);

alter table public.Grade
add foreign key (student_id) references public.Student(id);
alter table public.Grade
add foreign key (task_id) references public.Task(id);

create table public.Group_Student(
    id SERIAL PRIMARY KEY,
    group_id bigint not null,
    student_id bigint not null
);

alter table public.Group_Student
add foreign key (group_id) references public.Group_(id);
alter table public.Group_Student
add foreign key (student_id) references public.Student(id);

create table public.Teacher(
    id SERIAL PRIMARY KEY,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);

create table public.Teacher_Group(
    id SERIAL PRIMARY KEY,
    teacher_id bigint not null,
    group_id bigint not null
);

alter table public.Teacher_Group
add foreign key (teacher_id) references public.Teacher(id);
alter table public.Teacher_Group
add foreign key (group_id) references public.Group_(id);