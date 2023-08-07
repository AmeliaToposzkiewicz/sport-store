create table if not exists product
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        varchar(100),
    price       decimal(9, 2) not null,
    image       varchar(45),
    description varchar(600)  not null
);

create table if not exists indent
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_full_name varchar(100)  not null,
    customer_email     varchar(100)  not null,
    city               varchar(100)  not null,
    zip_code           varchar(6)    not null,
    street             varchar(100)  not null,
    street_no          varchar(10)   not null,
    home_no            varchar(5),
    price              decimal(9, 2) not null not null
);

create table if not exists indent_product
(
    indent_id   bigint not null,
    product_id bigint not null,
    FOREIGN KEY (indent_id) REFERENCES indent (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

create table if not exists user
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    username        varchar(100) not null,
    password        varchar(100) not null,
    enabled         bit(1)       not null,
    last_login_date datetime,
    authority       varchar(100) not null
);