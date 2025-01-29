# Read Me First
The following was discovered as part of building this project:

* The original package name 'org.stadium.stadium-management' is invalid and this project uses 'org.stadium.stadium_management' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.8/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.8/maven-plugin/build-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.3.8/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.8/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-boot/3.3.8/reference/web/spring-security.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.3.8/reference/messaging/kafka.html)
* [Spring for GraphQL](https://docs.spring.io/spring-boot/3.3.8/reference/web/spring-graphql.html)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.3.8/reference/data/nosql.html#data.nosql.mongodb)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.8/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [OAuth2 Client](https://docs.spring.io/spring-boot/3.3.8/reference/web/spring-security.html#web.security.oauth2.client)
* [Config Client](https://docs.spring.io/spring-cloud-config/reference/client.html)
* [Config Server](https://docs.spring.io/spring-cloud-config/reference/server.html)
* [Java Mail Sender](https://docs.spring.io/spring-boot/3.3.8/reference/io/email.html)
* [Eureka Server](https://docs.spring.io/spring-cloud-netflix/reference/spring-cloud-netflix.html#spring-cloud-eureka-server)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.3.8/reference/actuator/index.html)
* [Thymeleaf](https://docs.spring.io/spring-boot/3.3.8/reference/web/servlet.html#web.servlet.spring-mvc.template-engines)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Centralized Configuration](https://spring.io/guides/gs/centralized-configuration/)
* [Service Registration and Discovery with Eureka and Spring Cloud](https://spring.io/guides/gs/service-registration-and-discovery/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

