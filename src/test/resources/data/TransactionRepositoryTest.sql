INSERT INTO app_user (id, email, password) VALUES (1, 'DummyUser1@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_ACCOUNT (id, balance, name, user_id) VALUES (1, 50000, 'User_1_dummy_account_1',1);
INSERT INTO app_ACCOUNT (id, balance, name, user_id) VALUES (2, 100, 'User_1_dummy_account_2',1);
INSERT INTO app_category (id, name) VALUES (1, 'Category1');
INSERT INTO app_category (id, name) VALUES (2, 'Category2');
INSERT INTO app_transaction (id,date,result,account_id,category_id) VALUES (1,'2022-10-12',500,1,1);
INSERT INTO app_transaction (id,date,result,account_id,category_id) VALUES (2,'2022-10-13',500,1,2);
INSERT INTO app_transaction (id,date,result,account_id,category_id) VALUES (3,'2022-10-14',2000,1,1);
INSERT INTO app_transaction (id,date,result,account_id,category_id) VALUES (4,'2022-10-19',1200,2,1);