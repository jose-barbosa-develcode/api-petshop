CREATE table carrinhos(
                              id_carrinho bigint not null auto_increment,
                              valor_total decimal(50,2),
                              cliente_id bigint,



                              primary key(id_carrinho)



);

