# JustTraditions

<p align = "center">
  <img src = "https://github.com/losor2002/GreenBottle/blob/master/src/main/resources/static/img/Logo.png" width="256" height="256">
</p>

<p align = "center">
  Spring Boot Web App to sell
  <br>
  A project for selling drinks in glass bottles and recycling glass bottles
  <br>
  Software Engineering and Software Project Management 
  <br>
  courses of Computer Science at University of Salerno.
</p>

# Project description and introduction

In this section we introduce context information for the project.

## Introduction

Spring Boot Web App to support artisans. Produced for the Software Engineering and Software Project Management
courses of Computer Science at University of Salerno.

## Authors

* **Lorenzo Sorrentino**             - *Project Manager*   - [losor2002](https://github.com/losor2002)
* **Francesco Maria Puca**           - *Project Manager*   - [FraPCA](https://github.com/FraPCA)
* **Michael Marino**                 - *Developer*         - [Miky1071](https://github.com/Miky1071)
* **Giovanni Ruocco**                - *Developer*         - [GiRuni](https://github.com/GiRuni)
* **Pasquale Muraca**                - *Developer*         - [PasqualeMuraca](https://github.com/PasqualeMuraca)
* **Pietro D'antuono**               - *Developer*         - [PDant17](https://github.com/PDant17)
* **Stefano Cozzolino**              - *Developer*         - [Ser1zard](https://github.com/Ser1zard)
* **Fabio Catello Ponticelli**       - *Developer*         - [Boh0263](https://github.com/Boh0263)
* **Giuseppe Pastena**               - *Developer*         - [GiuseppePastena23](https://github.com/GiuseppePastena23)
* **Salvatore Conte**                - *Developer*         - [DeusTuberosa](https://github.com/DeusTuberosa)

## Documentation

* Project's javadoc can be found in *docs* directory and at the following
  link: [Javadoc Link](https://losor2002.github.io/GreenBottle/).
* Process documentation can be found in *projectDocs*  directory.

# Technical information

In this section we introduce technical information and installing guides!

## Installing and running the project

Follow these steps:

1. Download and start MySQL 8.0.31;
2. Download Java SE 17;
3. Clone this repo;
4. Go into JustTraditions directory;
5. Run `./mwnw (or .\mvnw.cmd if you're on windows) clean package` and wait for maven to build;
6. Open and run configurationFiles/databaseInit.sql on MySQL workbench;
7. Run `java -jar target/justTraditions-1.0.jar –spring.datasource.password="<password_database>"
   –spring.datasource.username="<username_database>"`;
8. Open and run configurationFiles/defaultadmin.sql on MySQL workbench;
9. Open your browser at `localhost:8080`.

## Built With

* [Java](https://jdk.java.net/17/) - The programming language used for the back-end development.
* [Spring Framework](https://spring.io/) - The java framework used to develop (Spring Web and Spring Data JPA).
* [Maven](https://maven.apache.org/) - Dependency Management.
* [HTML5](https://www.w3schools.com/html/default.asp) - The programming language used for the front-end development.
* [Bootstrap](https://getboostrap.com/) - Front-end framework.
* [Thymeleaf](https://www.thymeleaf.org/) - Java template to render static pages into dynamic.

# Contributors

<a href="https://github.com/losor2002/GreenBottle/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=losor2002/GreenBottle" />
</a>

Made with [contrib.rocks](https://contrib.rocks).
