-- Adding users
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser1@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser2@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser3@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser4@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser5@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser6@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser7@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser8@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');
INSERT INTO app_user (id, email, password) VALUES (default, 'DummyUser9@dummy.com', '8da960acaf3090eb4b7ff3b853d622be');

-- Adding accounts
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 50000, 'User_1_dummy_account_1', 1);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 50, 'User_1_dummy_account_2', 1);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 5000, 'User_2_dummy_account_1', 2);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 4000, 'User_3_dummy_account_1', 3);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 10, 'User_4_dummy_account_1', 4);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 3333, 'User_6_dummy_account_1', 6);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 250, 'User_6_dummy_account_2', 6);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 2141, 'User_6_dummy_account_3', 6);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 1200, 'User_5_dummy_account_1', 5);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 2000, 'User_7_dummy_account_1', 7);
INSERT INTO app_account (id, balance, name, user_id) VALUES (default, 1000, 'User_8_dummy_account_1', 8);

-- Adding categories
INSERT INTO app_category (id, name) VALUES (default, 'Account transfer');
INSERT INTO app_category (id, name) VALUES (default, 'Salary');
INSERT INTO app_category (id, name) VALUES (default, 'Entertainment');
INSERT INTO app_category (id, name) VALUES (default, 'Insurance');
INSERT INTO app_category (id, name) VALUES (default, 'Health');
INSERT INTO app_category (id, name) VALUES (default, 'Food');
INSERT INTO app_category (id, name) VALUES (default, 'Cafe\Restaurant');
INSERT INTO app_category (id, name) VALUES (default, 'Rent');
INSERT INTO app_category (id, name) VALUES (default, 'Groceries');
INSERT INTO app_category (id, name) VALUES (default, 'Utilities');
INSERT INTO app_category (id, name) VALUES (default, 'Transportation');

-- Adding transactions
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-18', 500, 1, 1);
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-18', -500, 2, 1);
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-17', -2000, 4, 3);
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-17', -2000, 4, 5);
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-16', 2000, 4, 1);
INSERT INTO app_transaction (id, date, result, account_id, category_id) VALUES (default, '2022-10-17', -2000, 5, 1);
