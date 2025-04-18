CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(512) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    available BOOLEAN NOT NULL,
    times_used BIGINT,
    CONSTRAINT pk_item PRIMARY KEY (id),
    FOREIGN KEY (owner_id) references users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id BIGINT NOT NULL,
    start_time TIMESTAMP(0),
    end_time TIMESTAMP(0),
    FOREIGN KEY (item_id) references items (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMAERY KEY,
    text varchar(512) NOT NULL,
    item_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created TIMESTAMP(0),
    FOREIGN KEY (item_id) references items (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) references users (id) ON DELETE CASCADE
);