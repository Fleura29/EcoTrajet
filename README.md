# Projet Transversal - G14

## Lien vers le site web

VM inactive

Pour tester l’API du backend, un environnement de tests GraphiQL est disponible à l’adresse
suivante : VM inactive

## Dépendances & Compilation

### Frontend

Le frontend est une application React utilisant les dépendances suivantes:

- Leaflet pour la visualisation sous forme de carte
- Tailwind CSS pour les styles
- Vite en tant que bundler
- Apollo Client pour la gestion des requêtes GraphQL

L'ensemble des dépendances est spécifié dans le fichier `frontend/package.json`.

La compilation s’effectue avec npm:

```
cd frontend
npm install
npm run build
```

On obtient un dossier `frontend/dist/` contenant les fichiers qui seront servi par le serveur web.

### Backend

Le backend est écrit en Java Spring Boot. Il utilise principalement les dépendances suivantes :

- Spring GraphQL pour la gestion des requêtes GraphQL
- GraphHopper en tant que moteur de routage géographique
- Hibernate pour la persistance des entités Spring
- Lombok pour simplifier le code

L'ensemble des dépendances est spécifié dans le fichier `pom.xml`.

Le backend est accompagné d’une base de données MySQL gérée automatiquement par Hibernate.

La compilation s’effectue avec Maven:

```
mvn clean package
```

On obtient une archive WAR à déployer sur un serveur Tomcat.

## Crédits

- Ait-Belkacem Élisa
- Chouati Linda
- Gifu Ionut
- Josserand Léo
- Raveloson Maherinirina
- Ruiz Charlotte