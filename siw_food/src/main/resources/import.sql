/*DEFINIZIONE DELLE SEQUENZE*/




/*INSERIMENTI NELLA TABELLA CUOCO*/
insert into cuoco(id,nome,cognome,data_nascita,url_image) values(nextval('cuoco_seq'),'Gordon','Ramsey','08/11/1966','/image/foto_cuochi/gordon.jpg');
insert into cuoco(id,nome,cognome,data_nascita,url_image) values(nextval('cuoco_seq'),'Antonino','Cannavacciuolo','16/04/1975','/image/foto_cuochi/antonino.jpg');
insert into cuoco(id,nome,cognome,data_nascita,url_image) values(nextval('cuoco_seq'),'Gino','D''Acampo','17/07/1976','/image/foto_cuochi/gino.jpg');

/*INSERIMENTI NELLA TABELLA RICETTA*/

-- Inserimento delle ricette
INSERT INTO ricetta(id,titolo, cuoco_id) VALUES (nextval('ricetta_seq'),'Pasta al pomodoro', (SELECT id FROM Cuoco WHERE nome = 'Gordon'));

INSERT INTO ricetta(id,titolo, cuoco_id) VALUES (nextval('ricetta_seq'),'Tiramisu', (SELECT id FROM Cuoco WHERE nome = 'Gino'));

