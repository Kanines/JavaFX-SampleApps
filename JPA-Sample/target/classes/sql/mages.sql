drop table spell;
drop table mage;

create table mage (
    id int not null primary key
        generated always as identity
        (start with 1, increment by 1),
    circle varchar(255),
    level int,
    name varchar(255)
);

create table spell (
    id int,
    name varchar(255),
    mage int,
    primary key (id),
    foreign key (mage) references mage(id)
);

delete from spell;
delete from mage;

insert into mage (circle, level, name) values ('FIRST', 3, 'Deruvish');
insert into mage (circle, level, name) values ('SECOND', 9, 'Apostle');
insert into mage (circle, level, name) values ('THIRD', 20, 'Balrog');

insert into spell (id, name, mage) values (1, 'Chain lightning', 1);
insert into spell (id, name, mage) values (2, 'Ride the lightning', 1);
insert into spell (id, name, mage) values (3, 'Ice shards', 2);
insert into spell (id, name, mage) values (4, 'Frozen burst', 2);
insert into spell (id, name, mage) values (5, 'Hellfire', 3);
