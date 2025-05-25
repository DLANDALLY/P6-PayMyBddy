# 💸 Pay My Buddy

Projet 6 – Formation Développeur d'application Java - OpenClassrooms  
Auteur : Daniel LANDALLY

## 📚 Description du projet

**Pay My Buddy** est une application web permettant à un utilisateur d'envoyer de l'argent à ses amis de manière simple et rapide.  
Le projet simule une plateforme de transfert d'argent entre particuliers avec gestion de contacts, visualisation du solde et historique des transactions

## 🎯 Fonctionnalités principales

- Création de compte utilisateur
- Connexion sécurisée (Spring Security)
- Ajout de contacts (amis)
- Transfert d'argent avec frais de 0.5%
- Affichage du solde du compte
- Affichage de l’historique des transactions

## 🛠️ Technologies utilisées

- Java 21
- Spring Boot 3.4.4  
- Spring Security  
- Spring Data JPA  
- Thymeleaf
- Boostrap  
- PostgreSQL  
- Maven
- Docker

## ⚙️ Prérequis

Avant de lancer l'application, assurez-vous d'avoir installé :

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Docker](https://www.docker.com/)

## 💾 Installation et lancement

### Cloner le dépôt

```bash
git clone https://github.com/DLANDALLY/P6-PayMyBddy.git
cd P6-PayMyBddy

Configurer la base de données

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/mypaybuddy
spring.config.import=optional:classpath:/application-local.properties
spring.jpa.hibernate.ddl-auto=update
server.port=8090

Lancer l’application
mvn spring-boot:run

👥 Compte de test
Vous pouvez utiliser ce compte utilisateur pour tester rapidement l'application :

Email : alice@example.com
Mot de passe : pass1234
```

## Modèle physique de données

![mypaybuddy MPD- public](https://github.com/user-attachments/assets/22d814bf-51d6-400a-8df5-2df1c027f698)
