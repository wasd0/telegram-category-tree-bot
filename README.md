# Telegram bot for manage categories

## Features:
- [x] Role destruction [User, Admin, Super admin]
- [x] Base commands
- [x] Download categories as .xlsx
- [x] Tests
- [ ] Upload categories as .xlsx
- [x] Docker

## Commands:
 - ``` /help ``` - Show a list of all available commands and a brief description of them.
 - ``` /addElement ``` ```<parent>``` ```<child>``` - Add new child category to parent
 - ``` /addElement ``` ```<category>```  - Add new category
 - ``` /viewTree ``` - Show category  tree
 - ``` /removeElement ``` ```<category> ``` - Remove category if exists
 - ``` /download ``` - Download categories as Excel file

## Getting started:

### 1. Set up SQL:

- POM Depednency:
```xml
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.7.1</version>
</dependency>
```
- Properties
```
spring:
  datasource:
    username: postgres
    password: postgres
    url: jdbc:postgresql://localhost:5435/postgres
    driver-class-name: org.postgresql.Driver
```

### 2. Set up bot:

- Token and username
```
categoryBot.username=SomeUsername
categoryBot.token=54544455:AAFPFDFD:fggeFSF8fd88fa
```

### Contact me: 
- mail: kirshvedov13@gmail.com
- telegram: @wasd_0
