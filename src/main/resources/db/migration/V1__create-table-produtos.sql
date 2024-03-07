CREATE table produtos(
                         id_produtos bigint not null auto_increment,
                         nome varchar(100) NOT NULL,
                         descricao varchar(100),
                         valor decimal(10,2),
                         tipos varchar(100),


                         primary key(id_produtos)



);