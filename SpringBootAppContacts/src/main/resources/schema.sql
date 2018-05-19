create table contact (
  id identity,
  firstName varchar(30) not null,
  lastName varchar (50) not null,
  phoneNumber varchar(13),
  email varchar(30)
);

insert into contact values
              (1, 'John', 'Doe', '736134787', 'john_d@gmail.com'),
              (2, 'Cristina', 'White', '723814563', 'cristina@mail.com'),
              (3,'Anna', 'Sunshine', '51777211', 'anna@gmail.com');