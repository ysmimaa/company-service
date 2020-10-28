
create table company (id int8 not null, activity varchar(255), name varchar(255), primary key (id))

create table driver (id int8 not null, contract_type varchar(255), firstname varchar(255), lastname varchar(255), phone varchar(255), id_journey int8, primary key (id))

create table journey (id int8 not null, address varchar(255), city varchar(255), end_at timestamp, nbr_of_places int4, start_at timestamp, id_company int8, id_driver int8, primary key (id))

alter table driver add constraint FK3qekey0270skda1dx0c8540nx foreign key (id_journey) references journey

alter table journey add constraint FKb81x3ccq1ua2bup2nualyoks5 foreign key (id_company) references company

alter table journey add constraint FKguvof0jfdox6lecdhs2veoxb0 foreign key (id_driver) references driver