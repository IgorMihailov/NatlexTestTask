create table if not exists section (
    id int not null,
    name varchar(250) not null,
    primary key (id)
);

create table if not exists geological_class (
    id int not null,
    name varchar(250) not null,
    code varchar(250) not null,
    section_id int not null,
    primary key (id),
    foreign key (section_id)
    references section(id)
);