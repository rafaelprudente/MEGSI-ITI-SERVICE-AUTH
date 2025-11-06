INSERT IGNORE INTO ITI.users (username, password,enabled)
VALUES ('aluno', '$2a$10$iErKNfA11N//GJC2qarsL.fOU/3QQ944PEV0.veR9c0foeKZWx/c2',true);

INSERT IGNORE INTO ITI.authorities (username, authority)
VALUES ('aluno', 'SYSTEM_ADMINISTRATOR');

INSERT IGNORE INTO ITI.user_details (id, username)
VALUES ('a34ae8c2-98ab-4777-873c-66de12f9175c', 'aluno');