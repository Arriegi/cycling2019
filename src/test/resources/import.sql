INSERT INTO Role (id, name) VALUES (1,"ADMIN");
INSERT INTO Role (id, name) VALUES (2,"USER");
INSERT INTO Privilege (id, name) VALUES (1,"READ");
INSERT INTO Privilege (id, name) VALUES (2,"WRITE");
INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (1,"jon@elkarmedia.eus",1,"Jon","{bcrypt}$2a$10$/TZi21GpVTe51RhTd1k8m.UYmrXnGxWRcxrC8mhxe9lQkO9Dw7OVq",0, 1);
INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (2,"ander@elkarmedia.eus",1,"Ander","{bcrypt}$2a$10$/TZi21GpVTe51RhTd1k8m.UYmrXnGxWRcxrC8mhxe9lQkO9Dw7OVq",0, 1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1,1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1,2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2,1);