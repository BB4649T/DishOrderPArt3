-- db/migration/V2__insert_test_data.sql
INSERT INTO ingredient (id, name) VALUES
                                      (1, 'Bread'),
                                      (2, 'Sausage');

INSERT INTO price (ingredient_id, amount, date_value) VALUES
                                                          (1, 1000.0, '2023-10-01'),
                                                          (1, 1200.0, '2023-10-05'),
                                                          (2, 2000.0, '2023-10-01'),
                                                          (2, 2200.0, '2023-10-05');

INSERT INTO dish (id, name, price) VALUES
    (1, 'Hot Dog', 0.0);

INSERT INTO dish_ingredient (dish_id, ingredient_id, required_quantity, unit) VALUES
                                                                                  (1, 1, 2.0, 'U'),
                                                                                  (1, 2, 1.0, 'U');