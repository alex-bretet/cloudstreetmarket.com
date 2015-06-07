
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

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (8, TIMESTAMP '2014-11-14 10:46:00', 13, 12, 12, 13, 12,13, 12, 'REN.AS');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (8, 6, 'SELL', 8 , 'actionMan10', TIMESTAMP '2014-11-14 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (9, TIMESTAMP '2014-11-14 10:46:00', 9.5, 9, 9, 9.5, 9, 9.5, 9, 'AXN');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (9, 6, 'BUY', 9 , 'other10', TIMESTAMP '2014-11-14 10:46:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (10, TIMESTAMP '2014-11-14 09:55:00', 32, 30, 30, 32, 30,32, 30, 'CMOXF');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (10, 6, 'BUY', 10 , 'randomGuy35', TIMESTAMP '2014-11-14 09:55:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (11, TIMESTAMP '2014-11-14 09:50:00', 15, 14, 14, 15, 14,15, 14, 'WIMHF.PK');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (11, 6, 'BUY', 11 , 'traderYY', TIMESTAMP '2014-11-14 09:50:00', 'trans');

insert into stock_quote(id, date, last, open, previous_close, ask, bid, high, low, stock_code) values (12, TIMESTAMP '2014-11-14 09:46:00', 7, 6, 6, 7, 6, 7, 6, 'DXIN');
insert into user_action(id, quantity, type, stock_quote_id, user_name, date, action_type) values (12, 6, 'BUY', 12 , 'userC', TIMESTAMP '2014-11-14 09:46:00', 'trans');

insert into index_quote(id, date, last, open, previous_close, high, low, index_code) values (1, TIMESTAMP '2014-11-15 09:46:00', 6796.63, 6796.63, 6796.63, 6796.63, 6796.63, '^FTSE');
insert into index_quote(id, date, last, open, previous_close, high, low, index_code) values (2, TIMESTAMP '2014-11-15 10:46:00', 6547.80, 6547.80, 6547.80, 6796.63, 6796.63, '^GDAXI');
