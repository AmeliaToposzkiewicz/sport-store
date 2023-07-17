drop table if exists product;
create table product
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        varchar(100),
    price       decimal(9, 2) not null,
    image       varchar(45),
    description varchar(600)  not null
);
drop table if exists `order`;
create table `order`
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
drop table if exists order_product;
create table order_product
(
    order_id   bigint not null,
    product_id bigint not null,
    FOREIGN KEY (order_id) REFERENCES `order` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);