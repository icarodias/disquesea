-- This script defines the wallet value to 0.00 and set the first user ADMIN

INSERT INTO tb_wallet (id, value) VALUES (1, 0.00);

INSERT INTO disquesea.tb_users (id, username, password, name, role) VALUES (1, 'icaro', '$2a$10$Jy.YeFtUCzlvC1a2xt5sC.RFfXzsCH2Tn63C9trkt3qb9VxUY7crO', 'Icaro', 'ADMIN');