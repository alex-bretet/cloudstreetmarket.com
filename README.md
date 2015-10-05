# cloudstreetmarket.com

![Build Status](https://travis-ci.org/alex-bretet/cloudstreetmarket.com.svg?branch=master)

This project is the application developed with the book [Spring MVC Cookbook](https://www.packtpub.com/web-development/spring-mvc-cookbook)

The latest Release of the application is running at: [cloudstreetmarket.com](http://cloudstreetmarket.com)

Read more in the [WIKI](https://github.com/alex-bretet/cloudstreetmarket.com/wiki)

## Quickstart

1. You must have a %JAVA_HOME% environment variable pointing to the root directory of a JDK8.
2. From the command line do:

```
git clone https://github.com/alex-bretet/cloudstreetmarket.com.git
cd cloudstreetmarket.com
mvn clean install
```

## To Continue
1. Install Tomcat 8 and deploy the 3 generated wars (portal, api & ws)
2. To emulate locally an OAuth2 authentication with Yahoo!
   Add the following host alias entry to your local machine 
   (In the file /etc/hosts or \WINDOWS\system32\drivers\etc\hosts if you are on windows)

```
  127.0.0.1			cloudstreetmarket.com
```

3. Install Apache HTTP Server 
4. In order to proxy Tomcat with Apache HTTP, use the versionned [httpd.conf](https://github.com/alex-bretet/cloudstreetmarket.com/blob/master/app/apache/httpd.conf): 

######At this point, you should be able to access the application from http://cloudstreetmarket.com

5. To enable WebSocket features, edit Tomcat /conf/context.xml file and add the following Valve configuration:
```
  <Valve className="edu.zipcloud.catalina.session.RedisSessionHandlerValve" asyncSupported="true"/>
	<Manager className="edu.zipcloud.catalina.session.RedisSessionManager"
			 host="localhost" 
			 port="6379" 
			 database="0" 
			 maxInactiveInterval="60"/>
```
6. Install the 3 following jars in Tomcat /lib directory
* [commons-pool2-2.2.jar](https://github.com/alex-bretet/cloudstreetmarket.com/blob/master/app/tomcat/lib/commons-pool2-2.2.jar)
* [jedis-2.5.2.jar](https://github.com/alex-bretet/cloudstreetmarket.com/blob/master/app/tomcat/lib/jedis-2.5.2.jar)
* [tomcat-redis-session-manager-2.0-tomcat-8.jar](https://github.com/alex-bretet/cloudstreetmarket.com/blob/master/app/tomcat/lib/tomcat-redis-session-manager-2.0-tomcat-8.jar) Generated from [the fork](https://github.com/alex-bretet/tomcat-redis-session-manager ) / Not tested in production yet

7. Install and run MySQL Server (HSQLDB wass supported until [v7.0.0.RELEASE](https://github.com/alex-bretet/cloudstreetmarket.com/releases/tag/v7.0.0.RELEASE)

8. Create a "csm" database
9. Create a technical user identified by the credentials defined in [config file](https://github.com/alex-bretet/cloudstreetmarket.com/blob/master/cloudstreetmarket-parent/cloudstreetmarket-core/src/main/resources/META-INF/spring/csmcore-config.xml)

10. Install RabbitMQ Server. Create a JMS_USER_ACTIVITY queue
11. Enable rabbitmq_stomp plugin with the command line:
```
$ rabbitmq-plugins enable rabbitmq_stomp
```
This will change the RabbitMQ port from /127.0.0.1:52144 => /127.0.0.1:61613
11. Install Redis Server

## IDE setup

For the usage inside an IDE do the following:

1. Make sure you have an Eclipse with m2e installed.
2. Import the checked out code through *File > Import > Existing Maven Project…*

## Project description

The project uses:

- [Spring (MVC)](http://github.com/spring-projects/spring-framework) - 4.2.1.RELEASE
- [Spring Data JPA](http://github.com/spring-projects/spring-data-jpa) - 1.8.0.RELEASE
- [Spring HATEOAS](http://github.com/spring-projects/spring-hateoas) - 0.17.0.RELEASE
- [Spring Security](http://github.com/spring-projects/spring-security) - 4.0.2.RELEASE
- [Spring Social](https://github.com/spring-projects/spring-social) - 1.1.0.RELEASE
- [Spring Session](https://github.com/spring-projects/spring-social) - 1.0.2.RELEASE

## The Book

[![spring-mvc-cookbook-img]](http://www.amazon.co.uk/Spring-MVC-Cookbook-Alex-Bretet/dp/1784396419) 

<!---
Link References
-->

[spring-mvc-cookbook-img]:http://ecx.images-amazon.com/images/I/518gBtl%2BMpL.jpg
