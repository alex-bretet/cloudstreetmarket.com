INSERT INTO market (id, name) values ('europe', 'Europe');
INSERT INTO market (id, name) values ('usa', 'US');
INSERT INTO index_value (code, name, market_id) values ('^FTSE','FTSE 100','europe');
INSERT INTO index_value (code, name, market_id) values ('^GDAXI','DAX','europe');
INSERT INTO index_value (code, name, market_id) values ('^FCHI','CAC 40','europe');
INSERT INTO index_value (code, name, market_id) values ('^STOXX50E','EURO STOXX 50','europe');

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

insert into user(loginName, password, profileImg) values ('happyFace8', '123456', 'img/young-lad.jpg');
insert into user(loginName, password, profileImg) values ('actionMan9', '123456', '');
insert into user(loginName, password, profileImg) values ('other9', '123456', 'img/santa.jpg');
insert into user(loginName, password, profileImg) values ('randomGuy34', '123456', '');
insert into user(loginName, password, profileImg) values ('traderXX', '123456', '');
insert into user(loginName, password, profileImg) values ('userB', '123456', '');

insert into stock_product(code, name,currency, market_id) values ('NXT.L', 'Next Plc','EUR', 'europe');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (1, TIMESTAMP '2014-11-15 11:12:00', 3, 2.9, 2.8, 3, 2.9, 'NXT.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (1, 6, 'BUY', 1 , 'happyFace8');

insert into stock_product(code, name,currency, market_id) values ('CCH.L', 'COCA-COLA HBC N','USD', 'usa');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (2, TIMESTAMP '2014-11-15 10:46:00', 13, 12, 12, 13, 12, 'CCH.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (2, 6, 'SELL', 2 , 'actionMan9');

insert into stock_product(code, name,currency, market_id) values ('KGF.L', 'Kingfisher plc','EUR', 'europe');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (3, TIMESTAMP '2014-11-15 10:46:00', 9.5, 9, 9, 9.5, 9, 'KGF.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (3, 6, 'BUY', 3 , 'other9');

insert into stock_product(code, name,currency, market_id) values ('III.L', '3i Ord','GBP', 'europe');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (4, TIMESTAMP '2014-11-15 09:55:00', 32, 30, 30, 32, 30, 'III.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (4, 6, 'BUY', 4 , 'randomGuy34');

insert into stock_product(code, name,currency, market_id) values ('BLND.L', 'The British Land Company Public Limited Company','GBP', 'europe');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (5, TIMESTAMP '2014-11-15 09:50:00', 15, 14, 14, 15, 14, 'BLND.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (5, 6, 'BUY', 5 , 'traderXX');

insert into stock_product(code, name,currency, market_id) values ('AA.L', 'AA','GBP', 'europe');
insert into stock_quote(id, date, last, open, previous_close, ask, bid, stock_code) values (6, TIMESTAMP '2014-11-15 09:46:00', 7, 6, 6, 7, 6, 'AA.L');
insert into transaction(id, quantity, type, stock_quote_id, user_name) values (6, 6, 'BUY', 6 , 'userB');

insert into index_quote(id, date, last, open, previous_close, index_code) values (1, TIMESTAMP '2014-11-15 09:46:00', 6796.63, 6796.63, 6796.63, '^FTSE');
insert into index_quote(id, date, last, open, previous_close, index_code) values (2, TIMESTAMP '2014-11-15 10:46:00', 6547.80, 6547.80, 6547.80, '^GDAXI');

