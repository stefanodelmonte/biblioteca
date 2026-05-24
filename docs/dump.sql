-- ============================================================
-- dump.sql — Biblioteca Personal
-- Esquema + dades de prova per a MySQL 8
-- Projecte Final DAM 1 — La Salle 2025-2026
-- ============================================================

-- Creem la base de dades si no existeix
CREATE DATABASE IF NOT EXISTS biblioteca
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE biblioteca;

-- ── Taules (Hibernate les crea automàticament amb ddl-auto=update,
--    però les definim aquí per a la documentació i dades de prova) ──

-- Taula: categoria
CREATE TABLE IF NOT EXISTS categoria (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom        VARCHAR(255) NOT NULL,
    descripcio TEXT
);

-- Taula: etiqueta
CREATE TABLE IF NOT EXISTS etiqueta (
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

-- Taula: llibre (entitat principal)
CREATE TABLE IF NOT EXISTS llibre (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    titol           VARCHAR(255) NOT NULL,
    autor           VARCHAR(255) NOT NULL,
    isbn            VARCHAR(20),
    pagines         INT,
    idioma          VARCHAR(50),
    any_publicacio  INT
);

-- Taula: lectura (relació ManyToOne amb llibre)
CREATE TABLE IF NOT EXISTS lectura (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    llibre_id   BIGINT NOT NULL,
    estat       VARCHAR(20) NOT NULL,
    valoracio   INT,
    data_inici  DATE,
    data_fi     DATE,
    ressenya    TEXT,
    CONSTRAINT fk_lectura_llibre FOREIGN KEY (llibre_id) REFERENCES llibre(id)
);

-- Taula intermèdia: llibre_categoria (relació ManyToMany)
CREATE TABLE IF NOT EXISTS llibre_categoria (
    llibre_id    BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (llibre_id, categoria_id),
    CONSTRAINT fk_lc_llibre    FOREIGN KEY (llibre_id)    REFERENCES llibre(id),
    CONSTRAINT fk_lc_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Taula intermèdia: lectura_etiqueta (relació ManyToMany)
CREATE TABLE IF NOT EXISTS lectura_etiqueta (
    lectura_id  BIGINT NOT NULL,
    etiqueta_id BIGINT NOT NULL,
    PRIMARY KEY (lectura_id, etiqueta_id),
    CONSTRAINT fk_le_lectura  FOREIGN KEY (lectura_id)  REFERENCES lectura(id),
    CONSTRAINT fk_le_etiqueta FOREIGN KEY (etiqueta_id) REFERENCES etiqueta(id)
);

-- ── Dades de prova ──────────────────────────────────────────

-- Categories
INSERT INTO categoria (nom, descripcio) VALUES
    ('Fantasia',        'Obres de ficció amb elements màgics i mons imaginaris'),
    ('Ciència-ficció',  'Ficció especulativa basada en avenços científics i tecnologia'),
    ('Història',        'Assajos i novel·les ambientades en períodes històrics reals'),
    ('Thriller',        'Novel·les de suspens i tensió narrativa'),
    ('Clàssics',        'Obres fonamentals de la literatura universal');

-- Etiquetes
INSERT INTO etiqueta (nom) VALUES
    ('Favorit'),
    ('Recomanat'),
    ('Rellegir'),
    ('Pendent de comprar'),
    ('Préstec biblioteca');

-- Llibres
INSERT INTO llibre (titol, autor, isbn, pagines, idioma, any_publicacio) VALUES
    ('El Senyor dels Anells',         'J.R.R. Tolkien',    '978-84-450-7179-9', 1200, 'Català',   1954),
    ('Dune',                          'Frank Herbert',     '978-84-9867-430-1',  896, 'Castellà', 1965),
    ('1984',                          'George Orwell',     '978-84-9793-181-0',  328, 'Castellà', 1949),
    ('El Nom del Vent',               'Patrick Rothfuss',  '978-84-9800-583-8',  872, 'Català',   2007),
    ('Crim i Càstig',                 'Fiódor Dostoievski','978-84-376-0398-7',  671, 'Català',   1866),
    ('Neuromancer',                   'William Gibson',    '978-84-450-7294-9',  271, 'Castellà', 1984),
    ('La Sombra del Viento',          'Carlos Ruiz Zafón', '978-84-08-04364-5',  567, 'Castellà', 2001),
    ('Sapiens',                       'Yuval Noah Harari', '978-84-9992-525-5',  493, 'Castellà', 2011);

-- Assignació de categories als llibres
INSERT INTO llibre_categoria (llibre_id, categoria_id) VALUES
    (1, 1),  -- El Senyor dels Anells → Fantasia
    (2, 2),  -- Dune → Ciència-ficció
    (3, 2),  -- 1984 → Ciència-ficció
    (3, 3),  -- 1984 → Història
    (4, 1),  -- El Nom del Vent → Fantasia
    (5, 5),  -- Crim i Càstig → Clàssics
    (6, 2),  -- Neuromancer → Ciència-ficció
    (7, 4),  -- La Sombra del Viento → Thriller
    (8, 3);  -- Sapiens → Història

-- Lectures
INSERT INTO lectura (llibre_id, estat, valoracio, data_inici, data_fi, ressenya) VALUES
    (1, 'LLEGIT',  5, '2025-10-01', '2025-11-15', 'Una obra mestra absoluta. La construcció del món és incomparable.'),
    (2, 'LLEGIT',  4, '2025-12-01', '2026-01-10', 'Fascinant a nivell filosòfic. La primera lectura pot ser densa però val la pena.'),
    (3, 'LLEGIT',  5, '2026-01-15', '2026-01-28', 'Absolutament vigent. Inquietant i necessari.'),
    (4, 'LLEGINT', NULL, '2026-04-01', NULL, NULL),
    (5, 'PENDENT', NULL, NULL, NULL, NULL),
    (7, 'LLEGIT',  4, '2026-02-01', '2026-02-20', 'Barcelona com a personatge. Una lectura molt entretinguda.'),
    (8, 'LLEGIT',  5, '2025-09-01', '2025-09-20', 'Canvia la perspectiva sobre la història de la humanitat.');

-- Assignació d'etiquetes a les lectures
INSERT INTO lectura_etiqueta (lectura_id, etiqueta_id) VALUES
    (1, 1),  -- El Senyor dels Anells (lectura) → Favorit
    (1, 2),  -- El Senyor dels Anells (lectura) → Recomanat
    (1, 3),  -- El Senyor dels Anells (lectura) → Rellegir
    (3, 1),  -- 1984 → Favorit
    (3, 2),  -- 1984 → Recomanat
    (6, 2),  -- La Sombra del Viento → Recomanat
    (7, 1),  -- Sapiens → Favorit
    (7, 2);  -- Sapiens → Recomanat
