/*INSERIMENTI NELLA TABELLA CUOCO*/
INSERT INTO Cuoco (id, nome, cognome, data_nascita) VALUES (nextval('cuoco_seq'), 'Mario', 'Rossi', '01-01-1980');
INSERT INTO Cuoco (id, nome, cognome, data_nascita) VALUES (nextval('cuoco_seq'), 'Luigi', 'Bianchi', '15-05-1975');
INSERT INTO Cuoco (id, nome, cognome, data_nascita) VALUES (nextval('cuoco_seq'), 'Giovanni', 'Verdi', '22-09-1985');
/*INSERIMENTI NELLA TABELLA RICETTA*/
INSERT INTO Ricetta (id, nome, descrizione, cuoco_id) VALUES (nextval('ricetta_seq'), 'Spaghetti Carbonara', 'Una deliziosa carbonara.', (SELECT id FROM Cuoco WHERE nome = 'Mario' AND cognome = 'Rossi'));
INSERT INTO Ricetta (id, nome, descrizione, cuoco_id) VALUES (nextval('ricetta_seq'), 'Pizza Margherita', 'Una classica pizza margherita.', (SELECT id FROM Cuoco WHERE nome = 'Luigi' AND cognome = 'Bianchi'));
INSERT INTO Ricetta (id, nome, descrizione, cuoco_id) VALUES (nextval('ricetta_seq'), 'Tiramisu', 'Un dolce tiramisu tradizionale.', (SELECT id FROM Cuoco WHERE nome = 'Giovanni' AND cognome = 'Verdi'));
/*INSERIMENTI NELLA TABELLA RICETTA*/
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Spaghetti', '200g', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Uova', '4', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Pancetta', '100g', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));

INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Farina', '300g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Pomodoro', '200g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Mozzarella', '150g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));

INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Mascarpone', '250g', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Caffe', '100ml', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));
INSERT INTO Ingrediente (id, nome, quantita, ricetta_id) VALUES (nextval('ingrediente_seq'), 'Cacao', 'q.b.', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));