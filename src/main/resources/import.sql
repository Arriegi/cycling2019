INSERT INTO Role (id, name) VALUES (1,"ADMIN");
INSERT INTO Role (id, name) VALUES (2,"USER");
INSERT INTO Privilege (id, name) VALUES (1,"READ");
INSERT INTO Privilege (id, name) VALUES (2,"WRITE");
INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (5001,"admin@imputador.eus",1,"admin","{bcrypt}$2a$10$tK0jNkJorPu8/LCXUz1ju.wFFk75t6MstuwCXo2u9DCKHbuORZ5m.",0, 1);
//INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (1,"jon@elkarmedia.eus",1,"Jon","{bcrypt}$2a$10$/TZi21GpVTe51RhTd1k8m.UYmrXnGxWRcxrC8mhxe9lQkO9Dw7OVq",0, 1);
//INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (2,"ander@elkarmedia.eus",1,"Ander","{bcrypt}$2a$10$/TZi21GpVTe51RhTd1k8m.UYmrXnGxWRcxrC8mhxe9lQkO9Dw7OVq",0, 1);
//INSERT INTO User (id, email, enabled, name, password, tokenExpired, role_id) VALUES (3,"usuario1@elkarmedia.eus",1,"Usuario1","{bcrypt}$2a$10$/TZi21GpVTe51RhTd1k8m.UYmrXnGxWRcxrC8mhxe9lQkO9Dw7OVq",0, 2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1,1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1,2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2,1);
//General configuration
INSERT INTO generalConfiguration (id, description, value) VALUES (10001, "TASA_KILOMETRAJE", "Tasa kilometraje", "0.37");
