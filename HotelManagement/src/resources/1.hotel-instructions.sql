create table hotels (
id integer primary key,
name varchar (40),
address varchar (50),
phone_number varchar (20) unique,
services varchar (40)
);


INSERT INTO hotels (id, name, address, phone_number, services) VALUES
(1, 'Hotel Central', 'Bulevardul Revoluției nr. 1, București', '0711234567', 'restaurant'),
(2, 'Hotel Imperial', 'Strada Victoriei nr. 25, Cluj-Napoca', '0714123456', 'piscina'),
(3, 'Hotel Riviera', 'Strada Grigore Alexandrescu nr. 9, Constanța', '0741789011', 'centru SPA'),
(4, 'Hotel Tranzit', 'Strada Calea Dorobanților nr. 5, Timișoara', '0766234567', 'centru SPA'),
(5, 'Hotel România', 'Bulevardul Decebal nr. 23, Cluj-Napoca', '0765478901', 'parcare'),
(6, 'Hotel Unirea', 'Piața Unirii nr. 5, București', '0731234567', 'bar'),
(7, 'Hotel Athena', 'Strada Alexandru Ioan Cuza nr. 15, Iași', '0733234502', 'restaurant'),
(8, 'Hotel Continental', 'Bulevardul Mihai Viteazul nr. 3, Oradea', '0759987654', 'piscina'),
(9, 'Hotel Bulevard', 'Bulevardul Carol I nr. 12, Brașov', '0768876544', 'centru de fitness'),
(10, 'Hotel Park', 'Bulevardul Revoluției nr. 12, Timișoara', '0756234567', 'parcare'),
(11, 'Hotel Elegance', 'Strada Victoriei nr. 17, București', '0744234567', 'restaurant'),
(12, 'Hotel President', 'Bulevardul Iuliu Maniu nr. 12, Cluj-Napoca', '0764123456', 'centru de fitness'),
(13, 'Hotel Continental Forum', 'Strada Ştefan cel Mare nr. 1, Iași', '0733234501', 'restaurant'),
(14, 'Hotel Meridian', 'Strada Gheorghe Doja nr. 15, Arad', '0754123987', 'centru SPA'),
(15, 'Hotel Castle', 'Strada General Traian Moșoiu nr. 24, Bran', '0768345678', 'restaurant'),
(16, 'Hotel King', 'Bulevardul Mihai Viteazul nr. 3, Oradea', '0759987655', 'piscina'),
(17, 'Hotel Vest', 'Bulevardul Mamaia nr. 1, Constanța', '0741789012', 'restaurant'),
(18, 'Hotel Patria', 'Piața Unirii nr. 1, Alba Iulia', '0746578901', 'centru de fitness'),
(19, 'Hotel Traian', 'Strada 22 Decembrie 1989 nr. 1, Iași', '0733234503', 'parcare'),
(20, 'Hotel Alpin', 'Strada Poiana Doamnei nr. 30, Predeal', '0769345678', 'restaurant'),
(21, 'Hotel Royal', 'Bulevardul Unirii nr. 5, Bacău', '0735468901', 'centru SPA'),
(22, 'Hotel Cismigiu', 'Bulevardul Regina Elisabeta nr. 38, București', '0721234567', 'restaurant'),
(23, 'Hotel Aro Palace', 'Bulevardul Carol I nr. 27, Brașov', '0768876543', 'centru de fitness'),
(24, 'Hotel Astoria', 'Strada Victoriei nr. 19, Cluj-Napoca', '0764123455', 'bar'),
(25, 'Hotel Nord', 'Strada Cuza Vodă nr. 1, Timișoara', '0756234566', 'centru SPA');