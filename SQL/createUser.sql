CREATE USER 'dummy'@'%' IDENTIFIED BY 'dummy_password';

-- REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'dummy'@'%';

GRANT SELECT, INSERT ON Library.* TO 'dummy'@'%';

FLUSH PRIVILEGES;

SHOW GRANTS FOR 'dummy'@'%';
