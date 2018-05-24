--Add Here datas 
INSERT INTO pays (VERSION, NOM) VALUES (1, 'France');
INSERT INTO pays (VERSION, NOM) VALUES (1, 'United Kingdom');

INSERT INTO ville (VERSION, NOM, PAYS_ID) VALUES (1, 'Paris', 1);
INSERT INTO ville (VERSION, NOM, PAYS_ID) VALUES (1, 'Lyon', 1);
INSERT INTO ville (VERSION, NOM, PAYS_ID) VALUES (1, 'Marseille', 1);
INSERT INTO ville (VERSION, NOM, PAYS_ID) VALUES (1, 'London', 2);

INSERT INTO transport(dtype, version, date_depart, heure_arrivee, heure_depart, nb_sieges_dispo, nb_sieges_total, prix, nb_wagons, voiture_bar, ville_a, ville_d) VALUES ('Train', 1, STR_TO_DATE('15/04/2018', '%d/%m/%Y'), '9:00', '11:00', 145, 156, 129.30, 9, 0, 1, 2);
INSERT INTO transport(dtype, version, date_depart, heure_arrivee, heure_depart, nb_sieges_dispo, nb_sieges_total, prix, nb_wagons, voiture_bar, ville_a, ville_d) VALUES ('Train', 1, STR_TO_DATE('16/05/2018', '%d/%m/%Y'), '9:00', '11:00', 145, 156, 129.30, 9, 0, 1, 2);