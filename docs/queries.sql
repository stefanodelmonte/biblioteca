-- ============================================================
-- queries.sql — Biblioteca Personal
-- 5 consultes SQL no trivials (M02 Bases de Dades RA3)
-- Projecte Final DAM 1 — La Salle 2025-2026
-- ============================================================

USE biblioteca;

-- ── Consulta 1 ──────────────────────────────────────────────
-- Llistat de llibres amb el nombre total de lectures i la valoració mitjana.
-- Usa JOIN, GROUP BY i funció d'agregació AVG.
-- Mostra tots els llibres, fins i tot els que no tenen cap lectura (LEFT JOIN).

SELECT
    l.titol,
    l.autor,
    COUNT(le.id)          AS total_lectures,
    ROUND(AVG(le.valoracio), 2) AS valoracio_mitjana
FROM llibre l
LEFT JOIN lectura le ON le.llibre_id = l.id
GROUP BY l.id, l.titol, l.autor
ORDER BY valoracio_mitjana DESC;

-- ── Consulta 2 ──────────────────────────────────────────────
-- Rànquing de categories per nombre de llibres llegits (estat = 'LLEGIT').
-- Usa doble JOIN (a través de la taula intermèdia), filtre per estat i ORDER BY.

SELECT
    c.nom               AS categoria,
    COUNT(DISTINCT l.id) AS llibres_llegits
FROM categoria c
JOIN llibre_categoria lc ON lc.categoria_id = c.id
JOIN llibre l            ON l.id = lc.llibre_id
JOIN lectura le          ON le.llibre_id = l.id
WHERE le.estat = 'LLEGIT'
GROUP BY c.id, c.nom
ORDER BY llibres_llegits DESC;

-- ── Consulta 3 ──────────────────────────────────────────────
-- Llibres llegits en un rang de dates concret, amb títol, autor, dates i ressenya.
-- Usa filtre de rang de dates i condició sobre camp nullable (ressenya no buida).

SELECT
    l.titol,
    l.autor,
    le.data_inici,
    le.data_fi,
    le.valoracio,
    le.ressenya
FROM llibre l
JOIN lectura le ON le.llibre_id = l.id
WHERE le.estat = 'LLEGIT'
  AND le.data_inici BETWEEN '2026-01-01' AND '2026-12-31'
  AND le.ressenya IS NOT NULL
ORDER BY le.data_fi DESC;

-- ── Consulta 4 ──────────────────────────────────────────────
-- Estadístiques globals de la biblioteca:
-- total de llibres, total de lectures per estat, valoració mitjana global.
-- Usa subconsultes i UNION per combinar resultats en una sola vista resum.

SELECT 'Total llibres'       AS estadistica, COUNT(*)    AS valor FROM llibre
UNION ALL
SELECT 'Llegits',             COUNT(*) FROM lectura WHERE estat = 'LLEGIT'
UNION ALL
SELECT 'Llegint ara',         COUNT(*) FROM lectura WHERE estat = 'LLEGINT'
UNION ALL
SELECT 'Pendents',            COUNT(*) FROM lectura WHERE estat = 'PENDENT'
UNION ALL
SELECT 'Valoració mitjana',   ROUND(AVG(valoracio), 2) FROM lectura WHERE valoracio IS NOT NULL;

-- ── Consulta 5 ──────────────────────────────────────────────
-- Llibres amb valoració superior a la mitjana global.
-- Usa subconsulta correlacionada per comparar cada valoració amb la mitjana.

SELECT
    l.titol,
    l.autor,
    le.valoracio,
    le.data_fi
FROM llibre l
JOIN lectura le ON le.llibre_id = l.id
WHERE le.valoracio > (
    SELECT AVG(valoracio)
    FROM lectura
    WHERE valoracio IS NOT NULL
)
ORDER BY le.valoracio DESC;
