CREATE table clientes(
                         id bigint not null auto_increment,
                         nome varchar(100) NOT NULL,
                         cep varchar(9),
                         idade integer(3),


                         primary key(id)



);



alter table clientes add ativo tinyint;
update clientes set ativo = 1;

