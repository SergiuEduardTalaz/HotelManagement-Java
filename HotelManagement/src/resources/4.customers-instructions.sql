create table customers (
id integer primary key, 
firstname varchar (50),
lastname varchar (50),
date_of_birth date,
phone_number varchar (20) unique,
email varchar (60) unique,
reservation_id integer not null,
constraint fk_customers_reservations
foreign key (reservation_id) references reservations (id)
);

INSERT INTO customers (id, firstname, lastname, date_of_birth, phone_number, email, reservation_id) VALUES
(1027, 'Maria', 'Dumitru', '1985-06-14', '0765123456', 'maria.dumitru@yahoo.com', 3025),
(1028, 'Vladimir', 'Ionascu', '1992-01-25', '0742123456', 'vladimir.ionascu@yahoo.com', 3024),
(1029, 'Maria', 'Radulescu', '1979-10-09', '0727123456', 'ana.radulescu@yahoo.com', 3023),
(1030, 'Andrei', 'Gheorghe', '1995-09-03', '0759123456', 'andrei.gheorghe@yahoo.com', 3022),
(1031, 'Roxana', 'Cojocaru', '1988-12-28', '0712123456', 'roxana.cojocaru@yahoo.com', 3021),
(1032, 'Ionut', 'Dumitrescu', '1980-04-06', '0730123456', 'ionut.dumitrescu@yahoo.com', 3020),
(1033, 'Elena', 'Mihai', '1993-07-22', '0760123456', 'elena.mihai@yahoo.com', 3019),
(1034, 'Mihai', 'Radu', '1976-05-17', '0745123456', 'mihai.radu@yahoo.com', 3018),
(1035, 'Gabriela', 'Stanescu', '1998-02-11', '0724123456', 'gabriela.stanescu@yahoo.com', 3017),
(1036, 'Adrian', 'Popa', '1987-08-19', '0752123456', 'adrian.popa@yahoo.com', 3016),
(1037, 'Andreea', 'Florea', '1991-11-30', '0736123456', 'andreea.florea@yahoo.com', 3015),
(1038, 'Catalin', 'Vasilescu', '1989-06-10', '0767123456', 'catalin.vasilescu@yahoo.com', 3014),
(1039, 'Irina', 'Stoica', '1983-09-08', '0740123456', 'irina.stoica@yahoo.com', 3013),
(1040, 'Alexandru', 'Tudorache', '1997-04-22', '0711123456', 'alexandru.tudorache@yahoo.com', 3012),
(1041, 'Diana', 'Radulescu', '1984-01-15', '0738123456', 'diana.radulescu@yahoo.com', 3011),
(1042, 'Razvan', 'Munteanu', '1982-08-22', '0768123456', 'razvan.munteanu@yahoo.com', 3010),
(1043, 'Andreea', 'Ionescu', '1994-03-09', '0747123456', 'andreea.ionescu@yahoo.com', 3009),
(1044, 'Marius', 'Stanescu', '1978-12-10', '0725123456', 'marius.stanescu@yahoo.com', 3008),
(1045, 'Ana Maria', 'Popescu', '1986-07-18', '0732123456', 'ana.maria.popescu@yahoo.com', 3007),
(1046, 'Radu', 'Stefanescu', '1990-09-05', '0762123456', 'radu.stefanescu@yahoo.com', 3006),
(1047, 'Elena', 'Dumitrescu', '1977-02-25', '0714123456', 'elena.dumitrescu@yahoo.com', 3005),
(1048, 'Cristian', 'Mihai', '1981-11-14', '0751123456', 'cristian.mihai@yahoo.com', 3004),
(1049, 'Gabriela', 'Andreescu', '1996-04-08', '0729123456', 'gabriela.andreescu@yahoo.com', 3003),
(1050, 'Alexandru', 'Popa', '1988-10-20', '0732123455', 'alexandru.popa@yahoo.com', 3002),
(1051, 'Andreea', 'Ivanescu', '1992-05-07', '0766123456', 'andreea.ivanescu@yahoo.com', 3001);
