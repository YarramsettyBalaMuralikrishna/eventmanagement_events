**Dependency injection and inversion control**

**Aspect oriented programming**



**groupID** is the organization name following reverse domain name

**artifactID** is the individual name/ project Name whose jar file you plan to download 

version is the one which we want to download





learn abt context.getBean();



org.springframwork.boot - in spring boot





tightly couping -> loose coupling we can do that by using interface





for using spring core we must have spring-context, spring-context-support dependency should be present in pom.xml





ApplicationContext ctx = new ApplicationConfigApplicationContext(ApplicationCOnfig.class);



kknow abot **classPathXMLApplicationContext** -> fro xml



here ApplicationContext is an interface and ApplicationCon..... implements it





<cosntructor-arg > for constructor based injection



<property> for seter based injection









autowired by type, by name read abt it



@Configutration

@Bean

@Lazy

@Autowired

@Scope

@ComponentScan(basePackage="com.spring.package")

@EnableAspectJAutoProxy

scanning of components; no need to create a bean explicitly



@Component, @Service, @Repository, @Controller, @RestController..





**Spring AOP**



Aspect \_> class annotated with @Aspect,

&nbsp;advice -> action taken by aspect at a particular join point

&nbsp;jointPoint-> event which will lead that method to get executed 



Before advice @Before

After advice

@After

@AfterReturning will work iff there is no exception thrown

@AfterThrowing will work on if there is an exception raised

@Around



learn abt PointCut expression.

do an example program



@Transactional annotation leverages SpringAOP to provide a decleartive management.





https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/EnableAspectJAutoProxy.html

