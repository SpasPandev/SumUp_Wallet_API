-- Insert Users
INSERT INTO user (id, username, password, first_name, last_name) VALUES
                                                                     (1, 'pesho', '$2a$10$R.Z1YNyHglWDj7.aA0vt0uxd9Uw8SOw0WcyRnqPb9ojCFvmBOIouS', 'Pesho', 'Peshov'),
                                                                     (2, 'dimo', '$2a$10$R.Z1YNyHglWDj7.aA0vt0uxd9Uw8SOw0WcyRnqPb9ojCFvmBOIouS', 'Dimo', 'Dimov'),
                                                                     (3, 'simo', '$2a$10$R.Z1YNyHglWDj7.aA0vt0uxd9Uw8SOw0WcyRnqPb9ojCFvmBOIouS', 'Simo', 'Simov');

-- Insert Wallets for Pesho
INSERT INTO wallet (id, name, balance, user_id) VALUES
                                                    (1, 'Pesho Wallet 1', 1000.00, 1),
                                                    (2, 'Pesho Wallet 2', 2000.00, 1),
                                                    (3, 'Pesho Wallet 3', 3000.00, 1);

-- Insert Wallets for Dimo
INSERT INTO wallet (id, name, balance, user_id) VALUES
                                                    (4, 'Dimo Wallet 1', 1500.00, 2),
                                                    (5, 'Dimo Wallet 2', 2500.00, 2);

-- Insert Wallet for Simo
INSERT INTO wallet (id, name, balance, user_id) VALUES
    (6, 'Simo Wallet 1', 500.00, 3);
