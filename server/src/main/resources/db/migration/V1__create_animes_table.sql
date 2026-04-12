CREATE TABLE IF NOT EXISTS animes (
                                      id SERIAL PRIMARY KEY,
                                      title TEXT NOT NULL,
                                      genre TEXT NOT NULL,
                                      studio TEXT NOT NULL,
                                      episodes INTEGER NOT NULL,
                                      release_year INTEGER NOT NULL
);