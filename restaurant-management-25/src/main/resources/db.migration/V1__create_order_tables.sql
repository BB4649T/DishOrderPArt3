-- V1__create_order_tables.sql
DO $$
    BEGIN
        -- Création des types ENUM s'ils n'existent pas déjà
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'order_process_status') THEN
            CREATE TYPE order_process_status AS ENUM ('CREATED', 'CONFIRMED', 'IN_PREPARATION', 'COMPLETED', 'SERVED');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'dish_order_status') THEN
            CREATE TYPE dish_order_status AS ENUM ('PENDING', 'PREPARING', 'READY', 'SERVED');
        END IF;
    END$$;

-- Table des commandes
CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      reference VARCHAR(255) UNIQUE NOT NULL,
                                      creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table des statuts des commandes (renommée pour éviter le conflit)
CREATE TABLE IF NOT EXISTS order_status_history (
                                                    id SERIAL PRIMARY KEY,
                                                    order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                                                    status order_process_status NOT NULL,
                                                    creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                    CONSTRAINT unique_order_status UNIQUE (order_id, creation_datetime)
);

-- Table des plats commandés
CREATE TABLE IF NOT EXISTS dish_order (
                                          id SERIAL PRIMARY KEY,
                                          order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                                          dish_id INT NOT NULL REFERENCES dish(id) ON DELETE RESTRICT,
                                          quantity DOUBLE PRECISION NOT NULL CHECK (quantity > 0),
                                          CONSTRAINT unique_dish_per_order UNIQUE (order_id, dish_id)
);

-- Table des statuts des plats commandés (renommée pour éviter le conflit)
CREATE TABLE IF NOT EXISTS dish_order_status_history (
                                                         id SERIAL PRIMARY KEY,
                                                         dish_order_id INT NOT NULL REFERENCES dish_order(id) ON DELETE CASCADE,
                                                         status dish_order_status NOT NULL,
                                                         creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                         CONSTRAINT unique_dish_status_time UNIQUE (dish_order_id, creation_datetime)
);