# 🤖 MY_MARVIN -- Jenkins Configuration as Code (EPITECH Project)

## 📌 Présentation du projet

Ce projet, réalisé dans le cadre du cursus EPITECH, consiste à configurer une instance Jenkins entièrement automatisée via **Configuration as Code (JCasC)** et à créer des jobs via le **Job DSL**. L’objectif est de comprendre et maîtriser :

- La configuration automatisée d’une instance Jenkins LTS
- La gestion des utilisateurs et des rôles avec sécurité
- La création et la gestion de jobs via Job DSL
- La mise en place d’un environnement DevOps reproductible
- L’intégration sécurisée de mots de passe via variables d’environnement

Le projet doit respecter des critères précis de notation afin de réussir tous les tests automatisés d’Epitech.

---

## 🧩 Architecture globale

```
+----------------------+
|  Jenkins Instance    |
|  (LTS + Plugins)     |
+----------+-----------+
           |
           | Config via JCasC
           ▼
   +-----------------+
   | my_marvin.yml   | <-- Définit système, utilisateurs, rôles, dossiers
   +-----------------+
           |
           | Crée utilisateurs et rôles
           ▼
+----------------------+      +-----------------+
| Users: Hugo, Garance,|      | Roles: admin,   |
| Jeremy, Nassim       |<---->| ape, gorilla,   |
+----------------------+      | assist          |
                              +-----------------+
           |
           | Crée jobs via DSL
           ▼
+----------------------+      +-------------------+
| job_dsl.groovy       | ---> | Jobs: clone-repo, |
|                      |      | SEED & others     |
+----------------------+      +-------------------+
```

---

## 🏗️ Structure du dépôt

```
.
├── my_marvin.yml       # Fichier de configuration JCasC
├── job_dsl.groovy      # Script central pour la création des jobs
└── Tools/              # Dossier de jobs Jenkins
```

---

## 🚀 Démarrage du projet

### 1️⃣ Déploiement Jenkins

Pour tester localement, il est conseillé d’utiliser une instance Docker Jenkins :

```sh
docker run -d \
  -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -e USER_CHOCOLATEEN_PASSWORD=xxx \
  -e USER_VAUGIE_G_PASSWORD=xxx \
  -e USER_I_DONT_KNOW_PASSWORD=xxx \
  -e USER_NASSO_PASSWORD=xxx \
  jenkins/jenkins:lts
```

### 2️⃣ Déploiement de la configuration

Copier `my_marvin.yml` dans `JENKINS_HOME` ou configurer le plugin **Configuration as Code** pour l’utiliser automatiquement.

### 3️⃣ Exécution des jobs

- `clone-repository` : clone un dépôt Git à la main
- `SEED` : génère tous les autres jobs selon les paramètres

---

## 🐳 Détails de la configuration JCasC

### 🔹 Système

- Message système :  
  `"Welcome to the Chocolatine-Powered Marvin Jenkins Instance."`
- Sign-up : **désactivé**

### 🔹 Utilisateurs

| Nom     | ID           | Mot de passe via variable |
|---------|--------------|--------------------------|
| Hugo    | chocolateen  | USER_CHOCOLATEEN_PASSWORD |
| Garance | vaugie_g     | USER_VAUGIE_G_PASSWORD |
| Jeremy  | i_dont_know  | USER_I_DONT_KNOW_PASSWORD |
| Nassim  | nasso        | USER_NASSO_PASSWORD |

### 🔹 Rôles

| Nom      | Description                                                                       | Permissions                           | Assigné à |
|----------|-----------------------------------------------------------------------------------|--------------------------------------|-----------|
| admin    | Marvin master                                                                     | Toutes les permissions nécessaires    | Hugo      |
| ape      | Pedagogical team member                                                            | Build job, voir workspaces           | Jeremy    |
| gorilla  | Group Obsessively Researching Innovation Linked to Learning and Accomplishment     | Ape + créer/configurer/supprimer job | Garance   |
| assist   | Assistant                                                                         | View jobs only                        | Nassim    |

---

## 📦 Job DSL

- **clone-repository**  
  - Dans `Tools`  
  - Paramètre `GIT_REPOSITORY_URL`  
  - Clonage via un seul shell  
  - Nettoyage workspace avant build  
  - Execution manuelle uniquement

- **SEED**  
  - Dans `Tools`  
  - Paramètres : `GITHUB_NAME`, `DISPLAY_NAME`  
  - Crée automatiquement les jobs via `job_dsl.groovy`  
  - Jobs générés :  
    - Root  
    - GitHub project property configuré  
    - SCM poll toutes les minutes + trigger manuel  
    - Prébuild cleanup  
    - Exécute `make fclean`, `make`, `make tests_run`, `make clean`  

---

## ✔️ Tableau des points évalués

| Critère            | Description                                                             | Obtenu |
|--------------------|-------------------------------------------------------------------------|--------|
| 01-yaml-exist      | A my_marvin.yml file exists                                             | 🟥🟩  |
| 02-yaml-valid      | The my_marvin.yml file is valid JCasc YAML                              | 🟥🟩  |
| 03-no-vuln         | No security vulnerabilities are present                                 | 🟥🟩  |
| 04-system-message  | The instance is configured with a proper system message                 | 🟥🟩  |
| 05-no-signup       | The sign-up is disallowed                                               | 🟥🟩  |
| 06-user-hugo       | The user `Hugo` exists, proper id & password via env                    | 🟥🟩  |
| 07-user-garance    | The user `Garance` exists, proper id & password via env                 | 🟥🟩  |
| 08-user-jeremy     | The user `Jeremy` exists, proper id & password via env                  | 🟥🟩  |
| 09-user-nassim     | The user `Nassim` exists, proper id & password via env                  | 🟥🟩  |
| 10-role-essential  | Only 4 roles: admin, ape, gorilla, assist                               | 🟥🟩  |
| 11-role-admin      | Correct description, permissions, assigned to Hugo                      | 🟥🟩  |
| 12-role-ape        | Correct description, permissions, assigned to Jeremy                    | 🟥🟩  |
| 13-role-gorilla    | Correct description, permissions, assigned to Garance                   | 🟥🟩  |
| 14-role-assist     | Correct description, permissions, assigned to Nassim                    | 🟥🟩  |
| 15-dsl-exists      | job_dsl.groovy exists at root                                           | 🟥🟩  |
| 16-dsl-valid       | job_dsl.groovy is valid                                                 | 🟥🟩  |
| 17-tools           | Folder named `Tools` exists                                             | 🟥🟩  |
| 18-job-clone       | Job `clone-repository` respects specifications                          | 🟥🟩  |
| 19-job-seed        | Job `SEED` respects specifications                                      | 🟥🟩  |
| 20-job-dsl         | `SEED` job DSL is valid                                                 | 🟥🟩  |
| 21-job-specs       | Jobs created by SEED respect specifications                             | 🟥🟩  |
| 22-perfection      | All achievements obtained                                               | 🟥🟩  |

---

## 🏅 Note finale

> **Note : ../22**

---

## 👥 Contributeurs

- **Hugo EGRY** – Développement principal  
- **Rewann Tannou**  

---

## 📄 Licence -- MIT

```
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
