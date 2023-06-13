-- liquibase formatted sql

-- changeset icaro-dias:create-table-products

create table tb_products (
    id bigint not null auto_increment,
    name varchar(30) not null unique,
    amount decimal(38,2) not null,
    price decimal(38,2) not null,
    is_visible_in_catalog bit not null,
    category enum ('BIRD','FISH','MEAT','OTHER','SEAFOOD','SHRIMP','SNACK'),
    status enum ('ABSENT','AVAILABLE','CRITICAL'),
    product_scale enum('KILOGRAM', 'UNIT'),

    primary key (id)
) engine = InnoDB;

-- changeset icaro-dias:create-table-orders

create table tb_orders (
    id bigint not null auto_increment,
    amount decimal(38,2) not null,
    price decimal(38,2) not null,
    is_sell bit not null,
    created_at date not null,
    product_id bigint not null,

    primary key (id),
    constraint fk_order_product foreign key (product_id) references tb_products (id)
) engine = InnoDB;

-- changeset icaro-dias:create-table-wallet

create table tb_wallet (
    id bigint not null auto_increment,
    value decimal(38,2) not null,

    primary key (id)
) engine = InnoDB;

-- changeset icaro-dias:create-table-users

create table tb_users (
    id bigint not null auto_increment,
    username varchar(20) not null unique,
    name varchar(40) not null,
    password varchar(60) not null,
    role enum ('ADMIN', 'MANAGER', 'EMPLOYEE'),

    primary key (id)
) engine = InnoDB;