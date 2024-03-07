CREATE table item_carrinho(
                         id_itens_carrinho bigint not null auto_increment,
                         quantidade integer,
                         preco_unitario decimal(50,2),
                         carrinho_id bigint,
                         produto_id bigint,
                         cliente_id bigint,




                         primary key(id_itens_carrinho)



);

