CREATE TABLE example
(
    id        serial primary key,
    name      text      not null,
    create_at timestamp not null default current_timestamp,
    update_at timestamp
)
