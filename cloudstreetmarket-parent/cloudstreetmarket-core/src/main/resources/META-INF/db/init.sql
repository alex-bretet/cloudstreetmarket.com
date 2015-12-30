
INSERT INTO market (code, name) values ('EUROPE', 'Europe');
INSERT INTO market (code, name) values ('US', 'US');
INSERT INTO market (code, name) values ('ASIA_PACIFIC', 'Asia / Pacific');
INSERT INTO market (code, name) values ('AMERICAS', 'Americas');
INSERT INTO market (code, name) values ('AFRICA_MIDDLE_EAST', 'Africas / Middle East');

INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('^FTSE','FTSE 100','EUROPE', 18684.58, -610, -3.27, 19295.58, 19295.58, 18554.32);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('^GDAXI','DAX','EUROPE', 9622.43, -481.12, -0.05, 10103.55, 10103.55, 9622.43);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('^FCHI','CAC 40','EUROPE', 4179.07, -79.4, -1.90, 4258.47, 4258.47, 4179.07);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('^STOXX50E','EURO STOXX 50','EUROPE', 3128.42, -91.97, -2.94, 3220.39, 3220.39, 3128.42);

INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^DJI','30 Industrials','US', 			17985.77, 			-44.08, 				-0.24, 				 18029.85, 			18028.67, 	17924.60);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^OEX','S&P 100 INDEX','US', 			921.34, 			-1.83, 				-0.20, 				 923.17, 			923.41, 	918.45);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^NDX','NASDAQ-100','US', 				4411.86, 			20.95, 				 0.48, 				 	 4390.91, 			4415.79, 	4388.44);

INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^N225','Nikkei 225','ASIA_PACIFIC', 				18332.30, 			 67.51, 				0.37, 				 18029.85, 			18360.92, 	18297.67);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^HSI','Hang Seng','ASIA_PACIFIC', 					24832.08, 			 47.20, 				0.19, 				 24784.88, 			24871.79, 	24806.06);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^STI','STI Index','ASIA_PACIFIC', 					3435.66, 			 19.75, 				0.58, 				 3415.91, 			3443.05, 	3425.89);

INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^MERV','MERVAL BUENOS AIRES','AMERICAS', 				9388.58, 			 -124.58, 				-1.31, 				 9513.16, 			9508.79, 9233.48);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^BVSP','IBOVESPA','AMERICAS', 				51294.03, 			 13.67, 				0.03, 				 51280.36, 		51638.40,	50869.51);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, 	high, 		low) 
values ('^GSPTSE','S&P/TSX Composite index','AMERICAS', 				15180.33, 			 -32.42, 				-0.21, 				 15212.75, 			 15211.81, 15099.91);

INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, 						previous_close, 	high, 		low) 
values ('^CCSI','EGX 70 PRICE INDEX','AFRICA_MIDDLE_EAST', 				574.03, 			 -3.62, 				-0.63, 				 574.03, 			581.07, 	573.98);
INSERT INTO index_value (code, name, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, 						previous_close, 	high, 		low) 
values ('^TA100','TEL AVIV TA-100 IND','AFRICA_MIDDLE_EAST', 				1310.82, 			 3.21, 				0.25, 				 1310.82, 			1315.06, 1305.01);

INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',3, 3042.90, 9813.99, TIMESTAMP '2014-11-15 08:30:00', TIMESTAMP '2014-11-15 09:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -2.37, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',4, 3042.90, 9823.65, TIMESTAMP '2014-11-15 09:00:00', TIMESTAMP '2014-11-15 09:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.24, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',5, 3042.90, 9832.74, TIMESTAMP '2014-11-15 09:30:00', TIMESTAMP '2014-11-15 10:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.15, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',6, 3042.90, 9841.89, TIMESTAMP '2014-11-15 10:00:00', TIMESTAMP '2014-11-15 10:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.05, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',7, 3042.90, 9816.16, TIMESTAMP '2014-11-15 10:30:00', TIMESTAMP '2014-11-15 11:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.33, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',8, 3042.90, 9812.43, TIMESTAMP '2014-11-15 11:00:00', TIMESTAMP '2014-11-15 11:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.35, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',9, 3042.90, 9783.31, TIMESTAMP '2014-11-15 11:30:00', TIMESTAMP '2014-11-15 12:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.65, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',10, 3042.90, 9794.27, TIMESTAMP '2014-11-15 12:00:00', TIMESTAMP '2014-11-15 12:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.54, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',11, 3042.90, 9782.48, TIMESTAMP '2014-11-15 12:30:00', TIMESTAMP '2014-11-15 13:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.66, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',12, 3042.90, 9779.11, TIMESTAMP '2014-11-15 13:00:00', TIMESTAMP '2014-11-15 13:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.67, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',13, 3042.90, 9785.61, TIMESTAMP '2014-11-15 13:30:00', TIMESTAMP '2014-11-15 14:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.63, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',14, 3042.90, 9785.82, TIMESTAMP '2014-11-15 14:00:00', TIMESTAMP '2014-11-15 14:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.62, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',15, 3042.90, 9819.03, TIMESTAMP '2014-11-15 14:30:00', TIMESTAMP '2014-11-15 15:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.29, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',16, 3042.90, 9823.62, TIMESTAMP '2014-11-15 15:00:00', TIMESTAMP '2014-11-15 15:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -0.24, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',17, 3042.90, 9724.61, TIMESTAMP '2014-11-15 15:30:00', TIMESTAMP '2014-11-15 16:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -1.25, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',18, 3042.90, 9684.22, TIMESTAMP '2014-11-15 16:00:00', TIMESTAMP '2014-11-15 16:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -1.66, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',19, 3042.90, 9675.03, TIMESTAMP '2014-11-15 16:30:00', TIMESTAMP '2014-11-15 17:00:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -1.75, '^GDAXI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',20, 3042.90, 9622.43, TIMESTAMP '2014-11-15 17:00:00', TIMESTAMP '2014-11-15 17:30:00', 'MINUTE_30', 3042.90, 3042.90, 3042.90, 0, -2.32, '^GDAXI');

INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',21, 18177.09, 18177.09, TIMESTAMP '2014-11-15 17:00:00', TIMESTAMP '2014-11-15 17:30:00', 'MINUTE_30', 18684.58, 18684.58, 18684.58, 0, -3.27, '^FTSE');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',22, 4179.07, 4179.07,   TIMESTAMP '2014-11-15 17:00:00', TIMESTAMP '2014-11-15 17:30:00', 'MINUTE_30', 4179.07,  4249.26, 4179.07,  0, -1.90, '^FCHI');
INSERT INTO historic (historic_type, id, adj_close, close, from_date, to_date, interval, high, low, open, volume, change_percent, index_code)
values ('idx',23, 3042.90, 3042.90, TIMESTAMP '2014-11-15 17:00:00', TIMESTAMP '2014-11-15 17:30:00', 'MINUTE_30', 3042.90, 3128.42, 3128.42, 0, -2.94, '^STOXX50E');

insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('happyFace8', '', 'fake1@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', 'img/young-lad.jpg', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('actionMan9', '', 'fake2@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('other9', '', 'fake3@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', 'img/santa.jpg', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('randomGuy34', '', 'fake4@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('traderXX', '', 'fake5@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('userB', '', 'fake6@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('happyFace9', '', 'fake7@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('actionMan10', '', 'fake8@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('other10', '', 'fake9@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('randomGuy35', '', 'fake10@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('traderYY', '', 'fake11@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('userC', '', 'fake12@fake.com', '$2a$10$Qz5slUkuV7RXfaH/otDY9udROisOwf6XXAOLt4PHWnYgOhG59teC6', '', true, true, true);
insert into users(username, full_name, email, password, profile_img, enabled, not_expired, not_locked) values ('admin', '', 'admin@fake.com', '$2a$10$VLKFFxRChNwxyciHMKYcvOsrCzy4HcucDen2aTS7oQO84ZhN96.0G', '', true, true, true);

insert into authorities(username, authority) values ('happyFace8', 'ROLE_BASIC');
insert into authorities(username, authority) values ('actionMan9', 'ROLE_BASIC');
insert into authorities(username, authority) values ('other9', 'ROLE_BASIC');
insert into authorities(username, authority) values ('randomGuy34', 'ROLE_BASIC');
insert into authorities(username, authority) values ('traderXX', 'ROLE_BASIC');
insert into authorities(username, authority) values ('userB', 'ROLE_BASIC');
insert into authorities(username, authority) values ('happyFace9', 'ROLE_BASIC');
insert into authorities(username, authority) values ('actionMan10', 'ROLE_BASIC');
insert into authorities(username, authority) values ('other10', 'ROLE_BASIC');
insert into authorities(username, authority) values ('randomGuy35', 'ROLE_BASIC');
insert into authorities(username, authority) values ('traderYY', 'ROLE_BASIC');
insert into authorities(username, authority) values ('userC', 'ROLE_BASIC');
insert into authorities(username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities(username, authority) values ('admin', 'ROLE_BASIC');

insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('ABF.L', 'Associated British Foods PLC','GBP', 'EUROPE', 2.948, -22, -0.75, 2.970, 2.970, 2.948);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('III.L', '3i Group PLC','GBP', 'EUROPE', 472.50, 1.90, 0.40, 470.6, 472.50, 470.6);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('DGE.L', 'Diageo PLC','GBP', 'EUROPE', 1.885, 46, 2.5, 1839, 1885, 1839);

insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('NXT.L', 'Next Plc','EUR', 'EUROPE', 10.12, 0.248, 2.45, 9.87, 10.12, 9.87);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('CCH.L', 'COCA-COLA HBC N','USD', 'US', 6.45, 0.1, 1.55, 6.35, 6.45, 6.35);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('C6L.SI', 'SIA','SGD', 'ASIA_PACIFIC', 3.25, 0.04, 1.23, 3.21, 3.25, 3.21);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('ALV.DE', 'Allianz SE','EUR', 'EUROPE', 147.2, 2.78, 1.89, 144.42, 147.2, 144.42);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('MM.IN', 'Mahindra & Mahindra Ltd','INR', 'ASIA_PACIFIC', 23.48, -0.06, -0.25, 23.54, 23.54, 23.48);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('000917.CH', 'Hunan TV & Broadcast Intermediary Co Ltd','CNY', 'ASIA_PACIFIC', 12.09, -0.296, -2.45, 12.386, 12.386, 12.09);

insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('H78.SI', 'Hongkong Land Holdings Limited','USD', 'ASIA_PACIFIC', 7.81, 0.182, 2.34, 7.62, 7.81, 7.62);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('N21.SI', 'Noble Group Limited','SGD', 'ASIA_PACIFIC', 1.05, 0.036, 3.42, 1.01, 1.05, 1.01);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('CSCO', 'Cisco Systems, Inc.','USD', 'US', 55.12, 1.21, 2.1, 53.91, 55.12, 53.91);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('MCD', 'McDonald''s Corp.','USD', 'US', 32.68, -2.25, -6.68, 34.93, 34.93, 32.68);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('600660.CH', 'Fuyao Glass Industry Group Co Ltd','CNY', 'ASIA_PACIFIC', 45.89, -1.25, -2.7, 45.89, 47.14, 45.89);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('V', 'Visa Inc.', 'USD', 'US', 98.14, 2.25, 2.3, 98.14, 100.39, 98.14);

insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('2802.JP', 'Ajinomoto Co Inc', 'JPY', 'ASIA_PACIFIC', 2243.5, -36.5, -1.6, 2290.0, 2290.0, 2243.5);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('4568.JP', 'Daiichi Sankyo Co Ltd', 'JPY', 'ASIA_PACIFIC', 1841.5, -12.5, -0.67, 1859.5, 1859.5, 1841.5);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('WPRO.IN', 'Wipro Ltd', 'INR', 'ASIA_PACIFIC', 650.55, -8.95, -1.36, 659.50, 659.50, 650.55);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('SSABA.SS', 'SSAB AB', 'SEK', 'EUROPE', 50.0000, -0.4000, -0.79, 50.5000, 50.5000, 50.0000);
insert into stock_product(code, name, currency, market_id, daily_latest_value, daily_latest_change, daily_latest_change_pc, previous_close, high, low) 
values ('NDA.SS', 'Nordea Bank AB', 'SEK', 'EUROPE', 110.2000, -0.4000, -0.36, 110.5000, 110.5000, 110.2000);

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (1, TIMESTAMP '2014-11-15 11:12:00', 3, 2.9, 2.8, 3, 2.9, 3, 2.9, 'NXT.L');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (1, 6, 'BUY', 1 , 'happyFace8', TIMESTAMP '2014-11-15 11:12:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (2, TIMESTAMP '2014-11-15 10:46:00', 13, 12, 12, 13, 12, 13, 12, 'ABF.L');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (2, 6, 'SELL', 2 , 'actionMan9', TIMESTAMP '2014-11-15 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (3, TIMESTAMP '2014-11-15 10:46:00', 9.5, 9, 9, 9.5, 9, 9.5, 9, 'CCH.L');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (3, 6, 'BUY', 3 , 'other9', TIMESTAMP '2014-11-15 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (4, TIMESTAMP '2014-11-15 09:55:00', 32, 30, 30, 32, 30,32, 30, 'ALV.DE');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (4, 6, 'BUY', 4 , 'randomGuy34', TIMESTAMP '2014-11-15 09:55:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (5, TIMESTAMP '2014-11-15 09:50:00', 15, 14, 14, 15, 14, 15, 14, 'H78.SI');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (5, 6, 'BUY', 5 , 'traderXX', TIMESTAMP '2014-11-15 09:50:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (6, TIMESTAMP '2014-11-15 09:46:00', 7, 6, 6, 7, 6,7, 6, 'N21.SI');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (6, 6, 'BUY', 6 , 'userB', TIMESTAMP '2014-11-15 09:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (7, TIMESTAMP '2014-11-14 11:12:00', 3, 2.9, 2.8, 3, 2.9,3, 2.9, 'CCH.L');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (7, 6, 'BUY', 7 , 'happyFace9', TIMESTAMP '2014-11-14 11:12:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (8, TIMESTAMP '2014-11-14 10:46:00', 13, 12, 12, 13, 12,13, 12, '600660.CH');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (8, 6, 'SELL', 8 , 'actionMan10', TIMESTAMP '2014-11-14 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (9, TIMESTAMP '2014-11-14 10:46:00', 9.5, 9, 9, 9.5, 9, 9.5, 9, 'MM.IN');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (9, 6, 'BUY', 9 , 'other10', TIMESTAMP '2014-11-14 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (10, TIMESTAMP '2014-11-14 09:55:00', 32, 30, 30, 32, 30,32, 30, 'V');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (10, 6, 'BUY', 10 , 'randomGuy35', TIMESTAMP '2014-11-14 09:55:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (11, TIMESTAMP '2014-11-14 09:50:00', 15, 14, 14, 15, 14,15, 14, 'MCD');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (11, 6, 'BUY', 11 , 'traderYY', TIMESTAMP '2014-11-14 09:50:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (12, TIMESTAMP '2014-11-14 09:46:00', 7, 6, 6, 7, 6, 7, 6, 'N21.SI');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (12, 6, 'BUY', 12 , 'userC', TIMESTAMP '2014-11-14 09:46:00', 'trans');

insert into index_quote(id, date, last, open, previous_close, high, low, index_code) values (1, TIMESTAMP '2014-11-15 09:46:00', 6796.63, 6796.63, 6796.63, 6796.63, 6796.63, '^FTSE');
insert into index_quote(id, date, last, open, previous_close, high, low, index_code) values (2, TIMESTAMP '2014-11-15 10:46:00', 6547.80, 6547.80, 6547.80, 6796.63, 6796.63, '^GDAXI');


