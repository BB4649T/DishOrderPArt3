-- Insertion d'une commande test
INSERT INTO orders (reference, creation_date, status)
VALUES ('CMD-001', NOW(), 'CREATED');

-- Insertion de plats associés
INSERT INTO order_dish (order_id, dish_id, quantity, status)
VALUES
    (1, 1, 2, 'CONFIRMED'), -- 2 hot dogs
    (1, 2, 1, 'CONFIRMED'); -- 1 omelette

-- Historique de statut exemple
INSERT INTO status_history (parent_type, parent_id, from_status, to_status, transition_date)
VALUES
    ('ORDER', 1, 'CREATED', 'CONFIRMED', NOW()),
    ('DISH', 1, 'CONFIRMED', 'IN_PREPARATION', NOW());