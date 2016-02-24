# CSE545 - Secure Banking Software
## Components:
1. ui_mocks - Contains html mockups for the system interface
2. sql - Contains sql files. Can be ignored once models are ported to hibernate completely
3. SunDevilBanking - Project code resides here
4. Certificates - Self signed certificates

## Routes:
1. `/login` - For the login form page
2. `/forgotpass` - For forgot password page
3. `/home/*` - For all routes once a user logs in (like /home/credit-debit, /home/fundtransfer etc)

## Resources:
1. [WebMystique tutorial used for Spring/Hibernate Setup](http://websystique.com/springmvc/spring-4-mvc-and-hibernate4-integration-example-using-annotations/)
2. We are using the Java/Hibernate Validator. Found [here](https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html)
3. SSL/HTTPS setup for tomcat server on local machine [here](https://dzone.com/articles/setting-ssl-tomcat-5-minutes)
4. Enabling Run-Jetty-Run to use SSL/HTTPS. Sample config I use: http://prntscr.com/8tx2pc
5. The same keystore file generated in pt. 3 above can be used for the jetty eclipse config.


## Project setup information
1. [Download and install MySql](https://dev.mysql.com/downloads/windows/)
2. Create a db `sbsgroup11`. Create a sql user `sbs` with pass `dbpasswdsbs`
2. `git clone https://github.com/rahulparekh/cse545group11.git`
2. Import project folder `SunDevilBanking` to eclipse as `Existing Maven Projects`
3. Next do `Run As -> Maven Install`
4. To Run the server - Run As -> Run Jetty (Need eclipse plugin for Jetty. I use [Run-Jetty-Run](https://marketplace.eclipse.org/content/run-jetty-run) )
5. This should get you up and running at http://localhost:8080/SunDevilBanking
6. Using any MySQL GUI client you should see the database has been populated with the schema
