CREATE table compra(
                           id bigint not null auto_increment,
                           carrinho_id bigint,
                           cliente_id bigint,
                           valor_total decimal(50,2),
                           data_hora_finalizacao datetime,


                           primary key(id)

                           );