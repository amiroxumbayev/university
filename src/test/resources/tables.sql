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
    id int auto_increment,
    name varchar(255),
    credit int,
    primary key(id)
);
create table public.Faculty(
    id int auto_increment,
    title varchar(255),
    primary key(id)
);

create table public.Faculty_Subject(
    id int auto_increment,
    faculty_id bigint not null,
    subject_id bigint not null,
    primary key(id)
);

alter table public.Faculty_Subject
add foreign key (faculty_id) references public.Faculty(id);
alter table public.Faculty_Subject
add foreign key (subject_id) references public.Subject(id);

create table public.Group_(
    id int auto_increment,
    name varchar(255) not null,
    faculty_id bigint not null,
    primary key(id)
);

alter table public.Group_
add foreign key (faculty_id) references public.Faculty(id);

create table public.Schedule(
    id int auto_increment,
    group_id bigint not null,
    subject_id bigint not null,
    day_week varchar(32) not null,
    start_time timestamp not null,
    end_time timestamp not null,
    primary key(id)
);

alter table public.Schedule
add foreign key (subject_id) references public.Subject(id);
alter table public.Schedule
add foreign key (group_id) references public.Group_(id);

create table public.Task(
    id int auto_increment,
    group_id bigint not null,
    subject_id bigint not null,
    title varchar(255) NOT NULL,
    type varchar(32) not null,
    description varchar(255) NOT NULL,
    primary key(id)
);

alter table public.Task
add foreign key (subject_id) references public.Subject(id);
alter table public.Task
add foreign key (group_id) references public.Group_(id);

create table public.Student(
    id int auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    year int not null,
    avg_gpa float not null,
    primary key(id)
);

create table public.Grade(
    id int auto_increment,
    student_id bigint not null,
    task_id bigint not null,
    score float NOT NULL,
    primary key(id)
);

alter table public.Grade
add foreign key (student_id) references public.Student(id);
alter table public.Grade
add foreign key (task_id) references public.Task(id);

create table public.Group_Student(
    id int auto_increment,
    group_id bigint not null,
    student_id bigint not null,
    primary key(id)
);

alter table public.Group_Student
add foreign key (group_id) references public.Group_(id);
alter table public.Group_Student
add foreign key (student_id) references public.Student(id);

create table public.Teacher(
    id int auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key(id)
);

create table public.Teacher_Group(
    id int auto_increment,
    teacher_id bigint not null,
    group_id bigint not null,
    primary key(id)
);

alter table public.Teacher_Group
add foreign key (teacher_id) references public.Teacher(id);
alter table public.Teacher_Group
add foreign key (group_id) references public.Group_(id);