SELECT 'Initialization script executed';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'menta';
FLUSH PRIVILEGES;
CREATE DATABASE IF NOT EXISTS fly_delivery;

