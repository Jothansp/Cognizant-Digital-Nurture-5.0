# Week 5 - Spring Framework Exercises (Cognizant Digital Nurture Java FSE)

Spring exercises are inherently multi-file Maven projects, so the single-.java-file
convention from Weeks 1-4 does not apply here. The nine exercises collapse into three
runnable projects:

| Project                         | Covers exercises              | Style                                   |
|---------------------------------|-------------------------------|-----------------------------------------|
| LibraryManagement-XML           | Ex 1, 2, 3, 4, 5, 7, 8        | Classic XML config, DI, AOP             |
| LibraryManagement-Annotations   | Ex 6                          | Component scanning + annotations        |
| LibraryManagement-Boot          | Ex 9                          | Spring Boot + JPA + H2 + REST           |

## Exercise -> file map

- Ex 1  Basic Spring app .............. XML/pom.xml, applicationContext.xml, BookService, BookRepository, main
- Ex 2  Dependency Injection (setter) .. XML/applicationContext.xml (<property> wiring) + setter in BookService
- Ex 3  AOP logging (exec time) ........ XML/aspect/LoggingAspect.java (@Around) + <aop:aspectj-autoproxy/>
- Ex 4  Maven project setup ............ XML/pom.xml (Context, AOP, WebMVC deps; compiler plugin -> Java 1.8)
- Ex 5  IoC container config ........... XML/applicationContext.xml (same base as Ex 1)
- Ex 6  Annotation-based config ........ Annotations project (@Service, @Repository, @Autowired, component-scan)
- Ex 7  Constructor + setter injection . XML/applicationContext.xml (<constructor-arg> + <property>)
- Ex 8  Basic AOP (before/after) ....... XML/aspect/LoggingAspect.java (@Before, @After)
- Ex 9  Spring Boot app ................ Boot project (entity, JpaRepository, RestController, H2)

## Build / run (PowerShell)

XML project (Ex 1-5, 7, 8):
    cd LibraryManagement-XML
    mvn clean compile exec:java -D"exec.mainClass=com.library.LibraryManagementApplication"
  or:
    mvn clean package
    java -cp "target/classes;<deps-on-classpath>" com.library.LibraryManagementApplication

Annotations project (Ex 6):
    cd LibraryManagement-Annotations
    mvn clean compile exec:java -D"exec.mainClass=com.library.LibraryManagementApplication"

Spring Boot project (Ex 9):
    cd LibraryManagement-Boot
    mvn spring-boot:run
    # then: GET/POST/PUT/DELETE http://localhost:8080/api/books
    # H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:librarydb)

## Version notes
- XML/Annotations projects: Spring 5.3.39 (last line supporting Java 8; runs fine on your JDK 21).
  Compiler target is 1.8 as the brief requires; the JDK running the build can still be 21.
- Boot project: Spring Boot 3.3.4 requires Java 17+, so it targets Java 17 and uses
  jakarta.persistence.* (not javax.*).
- To use exec:java without extra config, add the exec-maven-plugin, or just run mvn package
  and launch with java. mvn spring-boot:run is only for the Boot project.
