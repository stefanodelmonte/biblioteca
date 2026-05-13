[README.md](https://github.com/user-attachments/files/27591908/README.md)
# Biblioteca Personal

**Aplicació web per gestionar una biblioteca personal de lectura.**
Permet registrar llibres, afegir lectures amb ressenyes i valoracions, organitzar per categories i etiquetes, i consultar estadístiques de lectura.

Projecte Final — **DAM 1** · Curs 2025-2026 · **La Salle Tarragona**

---

## Descripció

Biblioteca Personal és una aplicació web pensada per a lectors que volen portar un registre organitzat dels llibres que han llegit, estan llegint o tenen pendents.

L'usuari pot:

- **Registrar llibres** amb les seves dades (títol, autor, pàgines, idioma, ISBN...).
- **Crear lectures** associades a cada llibre amb data d'inici/fi, valoració (1-5) i ressenya.
- **Classificar** llibres per categories (fantasia, ciència-ficció, història...).
- **Etiquetar** lectures amb tags personals (favorit, recomanat, rellegir...).
- **Consultar estadístiques**: total de llibres llegits, pàgines totals, mitjana de valoracions, llibres per categoria.

---

## Tecnologies

| Tecnologia | Versió | Ús |
|---|---|---|
| **Java** | 21 | Llenguatge base |
| **Spring Boot** | 3.5.x | Framework de l'aplicació |
| **JPA + Hibernate** | — | Mapeig objecte-relacional (ORM) |
| **MySQL** | 8.x | Base de dades relacional |
| **Thymeleaf** | — | Motor de plantilles (vistes HTML) |
| **Maven** | — | Gestió de dependències i build |
| **Git + GitHub** | — | Control de versions |

---

## Com posar-ho en marxa

### 1. Requisits previs

- Java 21 (JDK)
- MySQL 8 instal·lat i en marxa
- Maven (o usar el wrapper `./mvnw` inclòs)

### 2. Crear la base de dades

```sql
CREATE DATABASE biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'biblioteca_user'@'localhost' IDENTIFIED BY 'biblioteca_pass';
GRANT ALL PRIVILEGES ON biblioteca.* TO 'biblioteca_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar la connexió

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=biblioteca_user
spring.datasource.password=biblioteca_pass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Executar

```bash
git clone https://github.com/stefanodelmonte/biblioteca.git
cd biblioteca
./mvnw spring-boot:run
```

L'aplicació estarà disponible a **http://localhost:8080**.

### 5. Carregar dades de prova (opcional)

```bash
mysql -u biblioteca_user -p biblioteca < docs/dump.sql
```

---

## Estructura del projecte

```
biblioteca/
├── pom.xml
├── README.md
├── .gitignore
├── docs/
│   ├── diagrama-ER.png
│   ├── diagrama-classes.png
│   ├── dump.sql
│   └── queries.sql
└── src/main/
    ├── java/com/example/biblioteca/
    │   ├── BibliotecaApplication.java
    │   ├── model/
    │   │   ├── Llibre.java
    │   │   ├── Lectura.java
    │   │   ├── Categoria.java
    │   │   └── Etiqueta.java
    │   ├── repository/
    │   ├── service/
    │   └── controller/
    └── resources/
        ├── application.properties
        ├── templates/
        └── static/
```

---

## Model de dades
![Diagrama E-R](docs/diagrama-ER-visual.png)
### Entitats i relacions

- **Llibre** — Entitat principal (CRUD complet): títol, autor, ISBN, pàgines, idioma, any de publicació.
- **Lectura** — Registre personal de cada lectura: data inici, data fi, valoració (1-5), ressenya, estat (`LLEGIT` / `LLEGINT` / `PENDENT`). Relació `@ManyToOne` amb Llibre.
- **Categoria** — Gènere o temàtica del llibre. Relació `@ManyToMany` amb Llibre.
- **Etiqueta** — Tags personals per a lectures. Relació `@ManyToMany` amb Lectura.

El diagrama E-R complet es troba a `docs/diagrama-ER.png`.

---

## Consultes SQL destacades

Consultes no trivials disponibles a `docs/queries.sql`:

1. Llistat de llibres amb el nombre total de lectures i la valoració mitjana.
2. Rànquing de categories per nombre de llibres llegits.
3. Llibres llegits en un rang de dates concret amb la seva ressenya.
4. Estadístiques globals: total de pàgines, mitjana de valoració, total de llibres per estat.
5. Llibres que tenen una valoració superior a la mitjana global.

---

## Dades personals i seguretat

> Apartat requerit per al mòdul 1665 — Digitalització · RA5

Aquesta aplicació emmagatzema dades de lectura personal (títols, ressenyes, valoracions). **No es recullen dades personals sensibles** (ni noms reals d'usuaris, ni correus, ni contrasenyes d'usuari final).

**Mesures de protecció aplicades:**

- Les credencials de la base de dades es configuren a `application.properties`, que **no ha de contenir contrasenyes de producció** al repositori públic. En un entorn real s'utilitzarien variables d'entorn o un fitxer `application-local.properties` exclòs al `.gitignore`.
- El fitxer `.gitignore` exclou arxius sensibles, carpetes de build (`/target`) i configuració d'IDE.
- La base de dades MySQL requereix autenticació per accedir-hi.
- L'aplicació no exposa cap API pública sense control; només funciona en local.

En un desplegament real, caldria aplicar la **LOPDGDD / RGPD** si es gestionessin dades personals d'usuaris.

---

## Per què digitalitzar aquest procés

> Apartat requerit per al mòdul 1665 — Digitalització · RA6

Molts lectors porten el registre dels seus llibres en llistes de paper, fulls d'Excel o simplement de memòria. Això comporta:

- **Pèrdua d'informació** — Oblidar què s'ha llegit, quan, o què se'n pensava.
- **Dificultat per descobrir patrons** — Sense dades estructurades, no es pot saber quins gèneres es llegeixen més o quina és la mitjana de valoració.
- **Cap capacitat de cerca** — Trobar "aquell llibre que vaig llegir l'estiu passat que em va agradar molt" és impossible sense un sistema.

Digitalitzar aquest procés amb una aplicació web aporta:

- **Persistència fiable** — Les dades queden a una base de dades, no es perden.
- **Estadístiques automàtiques** — Pàgines totals, valoració mitjana, llibres per categoria.
- **Accés ràpid i cerca** — Trobar qualsevol llibre o ressenya en segons.
- **Escalabilitat** — Es pot ampliar amb recomanacions, compartir lectures, o integrar amb APIs de llibres.

---

## Autor

**Stefano Delmonte** — DAM 1 · La Salle Tarragona · Curs 2025-2026
