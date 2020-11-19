-- Enregistrements pour la table "BUG"
INSERT INTO BUG (id, title, `description`, creation_date, priority, progress) VALUES
    (1, 'Syntaxe Erreur', 'Un erreur de syntaxe génère un messsage d''erreur de l''application', '2019-09-24', 0, 2),
    (2, 'Lancement XP', 'Le programme ne se lance pas sur une machine exécutant Windows XP', '2019-09-25', 3, 1);

-- Enregistrements pour la table "DEVELOPER"
INSERT INTO DEVELOPER (id, handle, display_name) VALUES
    (1, 'qcarry', 'Stelario'),
    (2, 'aBalieu', 'Banto');