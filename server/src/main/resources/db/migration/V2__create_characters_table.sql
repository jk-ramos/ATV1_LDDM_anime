CREATE TABLE IF NOT EXISTS characters (
                                          id SERIAL PRIMARY KEY,
                                          anime_id INTEGER NOT NULL REFERENCES animes(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    role TEXT NOT NULL,
    power TEXT
    );