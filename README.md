# Rent-A-Car
## About the project
A car renting platform implemented using **HSQLDB** (Hyper SQL Database) with **JDBC** (Java Database Connectivity) which contains information about and helps interact between the companies involved, cars owned, and customers' rented cars.
## Project Mechanism
When the project is initiated a user menu appears on the console where the user is asked to log in as a manager or a customer. If the customer table is empty then the third option helps insert a customer in the customer table. When logged in as a manager the user can view a list of all the companies and can also add a desired company as well. The companies can also be selected and the cars within the companies can be viewed and a desired car can also be added to a company. After all the company related queries are done the user can select the back option and go back to the main menu and log in as a customer. A list of all the customer name prompts and asks the user to select him/her self from the list, after customer selection a customer menu with "Rent a car", "Return a rented car", and "My rented car" option appears. From the customer menu the customer can rent a desired car from a desired company, view the rented car along with the company it was rented from, and return the rented car. After a car is rented it will no longer appear on the list of cars available.
## Project Implementation
The project was implemented using a [HSQLDB](http://hsqldb.org/) which is a relational database management system written in Java. It has a [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity#:~:text=Java%20Database%20Connectivity%20(JDBC)%20is,Edition%20platform%2C%20from%20Oracle%20Corporation.) driver and supports both embedded and server modes. The **URL** used in this project initiates the database in server mode [(click here for more information)](https://razorsql.com/docs/help_hsqldb.html). To get started with HSQLDB using JDBC [click here](https://www.tutorialspoint.com/hsqldb/hsqldb_create_table.htm).
### Note
Make sure the project's JDK is **15 or higher** as ***textblocks*** are implemented else before running turn the ***textblocks*** into string literals. Don't run the code without downloading the HSQLDB driver jar files. This file can be downloaded from [here](https://sourceforge.net/projects/hsqldb/files/). See [HSQLDB's site](http://hsqldb.org/) for more information on obtaining the HSQLDB drivers. Finally, add the jar files to the project libraries and run the project.
