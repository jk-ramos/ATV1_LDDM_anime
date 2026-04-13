INSERT INTO animes (id, title, genre, studio, episodes, release_year)
VALUES
    (1, 'Naruto', 'Shounen', 'Pierrot', 220, 2002),
    (2, 'Attack on Titan', 'Action', 'Wit Studio', 25, 2013),
    (3, 'Death Note', 'Suspense', 'Madhouse', 37, 2006)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO characters (id, anime_id, name, role, power)
VALUES
    (1, 1, 'Sasuke Uchiha', 'Rival', 'Sharingan'),
    (2, 1, 'Sakura Haruno', 'Kunoichi', 'Chakra Control'),
    (3, 1, 'Kakashi Hatake', 'Sensei', 'Sharingan'),

    (4, 2, 'Eren Yeager', 'Protagonist', 'Titan Shifter'),
    (5, 2, 'Mikasa Ackerman', 'Soldier', 'Ackerman Strength'),
    (6, 2, 'Levi Ackerman', 'Captain', 'Elite Combat'),

    (7, 3, 'Light Yagami', 'Protagonist', 'Death Note'),
    (8, 3, 'L', 'Detective', 'Deduction'),
    (9, 3, 'Ryuk', 'Shinigami', 'Death Note Owner')
    ON CONFLICT (id) DO NOTHING;