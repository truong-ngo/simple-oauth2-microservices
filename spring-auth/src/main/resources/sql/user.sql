create table role
(
    id   bigint auto_increment primary key,
    name varchar(20) not null,
    constraint role__idx unique (name)
);

create table user
(
    id                 bigint auto_increment primary key,
    username           varchar(255)         not null,
    password           text                 not null,
    account_expired    tinyint(1) default 0 null,
    account_locked     tinyint(1) default 0 null,
    credential_expired tinyint(1) default 0 null,
    enabled            tinyint(1) default 1 null,
    constraint user_username_idx unique (username)
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    constraint user_role_role_id_fk foreign key (role_id) references role (id) on delete cascade,
    constraint user_role_user_id_fk foreign key (user_id) references user (id) on delete cascade
);