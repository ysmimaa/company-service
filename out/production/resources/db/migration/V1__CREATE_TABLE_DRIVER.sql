
create table t_company(
    id int8 not null,
    activity varchar(255),
    name varchar(255),
    primary key (id)
)

create table t_driver (
    id int8 not null,
    contract_type varchar(255),
    firstname varchar(255),
    lastname varchar(255),
    phone varchar(255),
    journey_id int8,
    primary key (id)
)
create table t_journey (
    id int8 not null,
    address varchar(255),
    city varchar(255),
    end_at timestamp,
    nbr_of_places int4,
    start_at timestamp,
    company_id int8,
    primary key (id)
    )

alter table t_driver
add constraint FKg2pext2o8fqiwiu4seg84fokm foreign key (journey_id) references t_journey
alter table t_journey
add constraint FKkv1lfv93evgwihks49l204tfa foreign key (company_id) references t_company
alter table t_journey
add constraint FK9inoeoh32965er1ke1utcw60h foreign key (driver_id) references t_driver

create sequence hibernate_sequence start 1 increment 1
