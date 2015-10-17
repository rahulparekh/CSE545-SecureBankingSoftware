# cse545group11
## Components:
1. ui_mocks - Contains html mockups for the system interface
2. sql - Contains sql files. Can be ignored once models are ported to hibernate completely
3. SunDevilBanking - Project code resides here

## Resources:
1. [WebMystique tutorial used for Spring/Hibernate Setup](http://websystique.com/springmvc/spring-4-mvc-and-hibernate4-integration-example-using-annotations/)
2. Java/Hibernate Validator [here](https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html)


## Project setup information
1. [Download and install MySql](https://dev.mysql.com/downloads/windows/)
2. Create a db `sbsgroup11`. Create a sql user `sbs` with pass `dbpasswdsbs`
2. `git clone https://github.com/rahulparekh/cse545group11.git`
2. Import project to eclipse
3. Run As -> Maven Install
4. To Run the server - Run As -> Run Jetty (Configure it in case you don't see this option. Jetty plugin is part of the maven dependencies)
5. This should get you up and running at http://localhost:8080/SunDevilBanking
6. Using any MySQL GUI client you should see the database has been populated with the schema
