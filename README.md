# Jumia-task

This project is hosted on AWS EC2 machine URL http://18.223.169.130/.

If you are going to run locally URL is http://localhost:8080/ using `mvn spring-boot:run`

To build the docker file 

`docker build . -t jumia:task`

To run

`docker run -dit -p 8080:8080 jumia:task`

There's two approaches for the implementations of this task [MVC and REST].

## MVC

 if you try to access http://18.223.169.130/ you will automatically be redirected to http://18.223.169.130/customers

where you are going to find a friendly HTML page with a search bar and a table.

There's three querey parameters to use none of them is mandatory:

- CountryName e.g. Cameroon, Morocco.
- CountryISOCode: which is an ISO representation of the country e.g. CM, ET, MA, MZ, UG
- State which only allow two values [VALID, INVALID].

If you pass no paramters you will get the full list of customers http://18.223.169.130/customers

If you pass the state as VALID you will get a list of all customers who have valid phone numbers respective to their countries http://18.223.169.130/customers?State=VALID and *vice versa* for INVALID.

If you pass no state just the country you will get a filtered list of all customers from that country http://18.223.169.130/customers?CountryName=Morocco

If you pass a state VALID with the country. it will responed with a list of customers who have valid phobe numbers from that country http://18.223.169.130/customers?CountryName=Cameroon&State=VALID

the opposite will be http://18.223.169.130/customers?CountryName=Cameroon&State=INVALID

You can also use CountryISOCode instead of CountryName e.g. http://18.223.169.130/customers?CountryISOCode=CM&State=VALID

### *But you can't use both even if they both represent the same country you will get an error message*

###  http://18.223.169.130/customers?CountryName=Cameroon&CountryISOCode=CM

### The MVC approach doesn't support pagination.

## REST:

All the above works the same but there's a prefix the word "api" in the url e.g. http://18.223.169.130/api/customers?CountryName=Cameroon&State=VALID and instead of an html page the response is JSON.

The rest implementation supports pagination the defualt page is 1 and max of each page is 5 records.

http://18.223.169.130/api/customers?CountryName=Morocco&page=1 and this http://18.223.169.130/api/customers?CountryName=Morocco will return the same response.

If you send a page less than 1 you will get and error http://18.223.169.130/api/customers?CountryName=Morocco&page=-1

Any combination of paramaters can be paged.

To handle the SQLite hibernate dialect and to able to use in an ORM way I found this open source library which implements it. https://github.com/ZsoltFabok/sqlite-dialect

MVN dependancy is

```xml
<dependency>
    <groupId>com.zsoltfabok</groupId>
    <artifactId>sqlite-dialect</artifactId>
    <version>1.0</version>
</dependency>
```
