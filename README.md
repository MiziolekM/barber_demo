
### BARBER DEMO

## Spis treści

* [Informacje ogólne] (#informacje-ogólne)
* [Technologie] (#technologie)
* [Funkcjonalności] (#funkcjonalności)
* [Uruchomienie] (#uruchomienie)

## Informacje Ogólne

Aplikacja webowa do rejestracji usługi fryzjerskiej przez klienta.

## Technologie

* Spring Boot
* Spring MVC
* Spring Security
* Hibernate
* MySql
* JSP
* CSS
* Tomcat

## Funkcjonalności

* Logowanie / Rejestracja
* Dla zwykłego klienta -> zapisanie się na usługę fryzjerskiej (wybór rodzaju usługi, dnia i godziny)
* Dla moderatora -> zarządzanie zamówniami (edycja, usunięcie)
* Dla administratora -> crm


## Uruchomienie	

Aplikacja ma status rozwojowym. Jako IDE używałem Eclipse. Wszystko czego potrzeba to dwa skrypty znajdujące się w katalogu sql-scripts. Do pracy z bazą danych używałem programu MySql Workbench. Jako server użyłem Apache Tomcat.

1. Uruchomić skrypt barber_demo_model.sql 
2. Uruchomić skrypt insert.sql

3. Dostosować poniższe adnotacje względem swojej bazy danych w pliku application.properties w katalogu src/main/resources
*spring.datasource.url
*spring.datasource.username
*spring.datasource.password
*Oraz ewentualnie zmienić port adnotacja - "server.port="

4. Domyślne loginy i hasła"
*Dla administratora to "admin", hasło to "test123"
*Dla moderatora to "moderator", hasło to "test123"
*Dla klienta to "usercustomer" hasło to "test123"

## Status

Projekt jest: _w fazie rozwoju_

Plany: 
* potwierdzenie rejestracji emailem
* odzyskiwanie hasła emailem
* potwierdzenie zapisania się do fryzjera z wykorzystaniem SMS API

