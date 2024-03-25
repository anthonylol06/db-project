# CSCI 3170 Project Group

## GROUP LIST
```
----------------------
|        NAME        |
----------------------
|     Anthony LO     |
|      Gary MAN      |
|     David KWOK     |
----------------------
```


## How it works
### ./Main.java
- connect to the database by calling `./models/Database.java` and start the Main CLI in `./cli/MainCLI.java`
### ./cli/xxxCLI.java
- CLI for admin, salespersons and managers
- call functions to implement the SQL statements in `./models/Database.java`
### ./models/Database.java
- all SQL statements including functions of admin, salesperson and manager
- connect to database
### ./model/file/xxxFileModel.java
- formatting `xxx.txt` files
- pass the formatted data to corresponding `./model/db/xxxModel.java`
### ./model/db/xxxModel.java
- receive data from `./model/file/xxxFileModel.java` add tuples to the data
### ./models/DateConvert.java
- change the date to specific format (yyyy-mm-dd) 
- change the string from `xxx.txt` files (dd/mm/yyyy) to date format

## How to make
`make`

## How to run
`make run`
- please ensure running this with CUHK CSE VPN!!!

## How to clean all .class files
`make clean`

## How to edit
- connect to CUHK CSE VPN
- Open VSCode 
- click the blue "><" button
- choose Remote-SSH
- server: {username}@linux10.cse.cuhk.edu.hk
- pw: CSE VPN password
- click Open in VSCode
- choose this file
- EDIT
