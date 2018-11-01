ALTER TABLE Prestation CHANGE prix prix DOUBLE NULL;

INSERT INTO Acte (libelle) VALUES ('Complet intérieur extérieur');
INSERT INTO Acte (libelle) VALUES ('Complet extérieur');
INSERT INTO Acte (libelle) VALUES ('Complet sans peinture');
INSERT INTO Acte (libelle) VALUES ('Peinture 1 élément');
INSERT INTO Acte (libelle) VALUES ('Peinture 2 éléments');
INSERT INTO Acte (libelle) VALUES ('Peinture 3 éléments');
INSERT INTO Acte (libelle) VALUES ('Peinture 4 éléments');
INSERT INTO Acte (libelle) VALUES ('Capot');
INSERT INTO Acte (libelle) VALUES ('Pavillon');
INSERT INTO Acte (libelle) VALUES ('Peinture d''un bouclier');
INSERT INTO Acte (libelle) VALUES ('Raccord adjacent à l''élément');
INSERT INTO Acte (libelle) VALUES ('Réfection de phares');
INSERT INTO Acte (libelle) VALUES ('Moto, scooter');
INSERT INTO Acte (libelle) VALUES ('Vélomoteur');
INSERT INTO Acte (libelle) VALUES ('Cadre de vélo');
INSERT INTO Acte (libelle) VALUES ('Réparation plastique');
INSERT INTO Acte (libelle) VALUES ('Rénovation VO complète');

INSERT INTO Finition (libelle) VALUES ('Opaque brillant direct');
INSERT INTO Finition (libelle) VALUES ('Opaque et métallisées revernies');
INSERT INTO Finition (libelle) VALUES ('Nacrées');
INSERT INTO Finition (libelle) VALUES ('Nacrées tricouches, teintes à effet');
INSERT INTO Finition (libelle) VALUES ('Pas de finition');

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (1, 1, 900.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (1, 2, 1100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (1, 3, 1300.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (1, 4, 1500.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (1, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (2, 1, 650.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (2, 2, 750.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (2, 3, 850.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (2, 4, 950.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (2, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (3, 1, 177.90);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (3, 2, 177.90);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (3, 3, 177.90);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (3, 4, 177.90);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (3, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (4, 1, 70.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (4, 2, 80.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (4, 3, 90.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (4, 4, 100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (4, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (5, 1, 140.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (5, 2, 150.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (5, 3, 160.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (5, 4, 170.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (5, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (6, 1, 190.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (6, 2, 210.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (6, 3, 230.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (6, 4, 250.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (6, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (7, 1, 240.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (7, 2, 270.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (7, 3, 300.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (7, 4, 350.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (7, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (8, 1, 90.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (8, 2, 100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (8, 3, 110.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (8, 4, 150.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (8, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (9, 1, 99.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (9, 2, 110.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (9, 3, 115.50);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (9, 4, 144.3);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (9, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (10, 1, 70.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (10, 2, 80.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (10, 3, 90.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (10, 4, 100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (10, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (11, 1, 40.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (11, 2, 50.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (11, 3, 60.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (11, 4, 80.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (11, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (12, 1, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (12, 2, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (12, 3, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (12, 4, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (12, 5, 15.00);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (13, 1, 200.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (13, 2, 300.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (13, 3, 350.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (13, 4, 450.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (13, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (14, 1, 100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (14, 2, 110.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (14, 3, 120.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (14, 4, 150.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (14, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (15, 1, 80.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (15, 2, 90.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (15, 3, 100.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (15, 4, 130.00);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (15, 5, 0.0);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (16, 1, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (16, 2, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (16, 3, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (16, 4, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (16, 5, 40.00);

INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (17, 1, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (17, 2, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (17, 3, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (17, 4, 0.0);
INSERT INTO Prestation (id_acte, id_finition, prix) VALUES (17, 5, 100.00);

INSERT INTO Client (id, adresse, email, nom ,num_afpa, prenom, telephone) VALUES (1,'rue Raimon de Trencavel 34070 Montpellier', 'test@test.com', 'DaRocha', 00001, 'Manuel', 0123456789);

INSERT INTO Voiture (id, categorie, code_couleur, immat, marque, modele, client_id) VALUES (1, 'fourgon',  'bleu', '1711YJ78', 'Fiotte', 'ours blanc et noir', 1);

