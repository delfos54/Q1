CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
create table refresh_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario(id) ON DELETE CASCADE
);
insert into usuario (username ,password ,role) values 
("admin", 
"$2a$10$7Qy8n1s5z5Z5Z5Z5Z5Z5O5O5O5O5O5O5O5O5O5O5O5O5O5O",
 "ROLE_ADMIN");
insert into usuario (username ,password ,role) values 
("user", 
"$2a$10$7Qy8n1s5z5Z5Z5Z5Z5Z5O5O5O5O5O5O5O5O5O5O5O5O5O", 
"ROLE_USER");