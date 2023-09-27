/**
 * Author: Badreddine TIRGANI
 */
use smartCities;
INSERT INTO sector
(id, create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(1, '2023-04-11', null, 0, 1, null, 'secteur 1');

INSERT INTO client_info
(id, create_at, delete_at, deleted, is_active, updated_at, address, cin, client_name, phone, tour_number, sector_id)
VALUES(1, '2023-04-11', null, 0, 1, null, 'Agadir', 'EE735639', 'tirgani', '0602834831', '889898', 1);

INSERT INTO branch
(id, create_at, delete_at, deleted, is_active, updated_at, description, name)
VALUES(1, '2023-04-11 00:00:00', null, 0, 1, null, 'Electricité', 'Electricité');
INSERT INTO smartCities.branch
(id, create_at, delete_at, deleted, is_active, updated_at, description, name)
VALUES(2, '2023-04-11 00:00:00', null, 0, 1, null, 'Eau Potable', 'Eau Potable');
INSERT INTO smartCities.branch
(id, create_at, delete_at, deleted, is_active, updated_at, description, name)
VALUES(3, '2023-04-11 00:00:00', null, 0, 1, null, 'Assainissement', 'Assainissement');

INSERT INTO branch
(id, create_at, delete_at, deleted, is_active, updated_at, description, name)
VALUES(2, '2023-04-11 00:00:00', null, 0, 1, null, 'Eau Potable', 'Eau Potable');
INSERT INTO branch
(id, create_at, delete_at, deleted, is_active, updated_at, description, name)
VALUES(3, '2023-04-11 00:00:00', null, 0, 1, null, 'Assainissement', 'Assainissement');

INSERT INTO family_type
(id, create_at, delete_at, deleted, is_active, updated_at, code, name, branch_id)
VALUES(1, '2023-04-11', null, 0, 1, null, 3998, 'family 1', 1);

INSERT INTO type
(id, create_at, delete_at, deleted, is_active, updated_at, libelle, family_type_id)
VALUES(1, '2023-04-11', null, 0, 1, null, 'type 1', 1);

INSERT INTO roles
(id, create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(1, '2023-04-11', null, 0, 1, null, 'ROLE_USER');

INSERT INTO roles
(id, create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(2, '2023-04-11', null, 0, 1, null, 'ROLE_ADMIN');
INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(3, null, 0, 1, null, 'ROLE_OKSA_CHEF_DEP');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_OR_INT');
INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_OPERATOR');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_CHEF');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_CHEF_EQP');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_EQP_MEMBER');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_OKSA_OR');

INSERT INTO roles
(create_at, delete_at, deleted, is_active, updated_at, name)
VALUES(null, null, 0, 1, null, 'ROLE_CLIENT');

INSERT INTO users
(create_at, delete_at, deleted, is_active, updated_at, email, enabled, first_name, last_login, last_name, locked, password, phone, user_name, branch_id, team_id, cin, charge_de_travaux, matricule)
VALUES(null, null, 0, 1, null, 'tirgani@gmail.com', 1, 'badreddine', null, 'tirgani', 0, '$2a$10$6aIup0xOEEsPTb9.oIDP.ej9bE5SFyOx9hhNWvVvDsSLOKq2EY0bS', '0602834831', 'tirgani', null, null, null, 1, null);
INSERT INTO user_roles
(users_id, roles_id)
VALUES(1, 2);
UPDATE client_info
SET create_at='2023-04-11 00:00:00', delete_at=NULL, deleted=0, is_active=1, updated_at=NULL, address='Agadir 1', cin='EE735639', client_name='badreddine tirgani', phone='067894830', police=NULL, tour_number='889007', editeur_id=1, sector_id=1, client_cin='CIN3', code_localite='local1', num_bloc='23', num_ordre='876', num_police='1999', num_secteur='1', ref_compteur='598673'
WHERE id=1;
