# Labb 2

## Beskrivning
Databasteknik

## Krav
- JDK 22+
- JDBC
- Maven
- Git
- MongoDB

## Miljö
- macOS / Windows
- IntelliJ Community Edition
- MongoDB Compass

### Miljövariabler

#### Intellij

`host=exempel.se; port=3306; usernane=NAMN; password=LÖSENORD`

Om `host` och `port` är standard, dvs `localhost` och port `3306` så behöver du inte ange dessa.

Alltså räcker `usernane=NAMN; password=LÖSENORD`.

#### Windows

```cmd
set usernane=NAMN
set password=LÖSENORD
```

Alternativt

```cmd
set host=exempel.se
set port=3306
set usernane=NAMN
set password=LÖSENORD
```

#### Unix

_.bashrc_
```shell
export usernane="NAMN"
export password="LÖSENORD"
```

Alternativt

_.bashrc_
```shell
export host=exempel.se
export port=3306
export usernane="NAMN"
export password="LÖSENORD"
```

## Installation
1. Installera JDK 22+ från [Oracle](https://www.oracle.com/java/technologies/javase-jdk22-downloads.html).
2. Installera Maven från [Maven](https://maven.apache.org/download.cgi).
3. Klona repositoryt med Git: `git clone https://github.com/potatisgrottan/DB_Labb1.git`
