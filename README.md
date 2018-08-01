# SpringRestCommon
 
 This project basically explains the steps to create a jar file of a spring boot application. Include the jar file in some other project as a dependency and use the aop functions of the jar project.
 
 ## Steps 
 ## Step 1 : Create a Spring boot application project
 Add the below dependencies.
 
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.8.13</version>
    </dependency>
 
 ## Step 2 : Add the below in the POM. For more details check the pom.xml file
 
    <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-source-plugin</artifactId>
                    <executions>
                        <execution>
                            <id>attach-sources</id>
                            <goals>
                                <goal>jar</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>

## Step 3
Create the below annotations for logging

    package com.arun.rc.config.annotation;
    
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
     * Created by Adwiti on 7/31/2018.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RsLogging {
    }


    package com.arun.rc.config.annotation;
    
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
     * Created by Adwiti on 7/31/2018.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RsTimeTraker {
    }


## Step 4

    create a class having @Aspect
    
    package com.arun.rc.config;
    
    import org.aspectj.lang.JoinPoint;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Before;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.context.annotation.Configuration;
    
    /**
     * Created by Adwiti on 7/31/2018.
     */
    @Aspect
    @Configuration
    public class LoggingConfig {
    
        private Logger logger = LoggerFactory.getLogger(this.getClass());
    
        @Before("@annotation(com.arun.rc.config.annotation.RsLogging)")
        public void beforeAnnotation(JoinPoint joinPoint) {
            logger.info("class called {} with method {} ", joinPoint.getSignature(), joinPoint.getSignature().getName());
        }
    
    
        @Around("@annotation(com.arun.rc.config.annotation.RsTimeTraker)")
        public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
            return proceed;
        }
    
    
    }


## Step 5

    Add the below dependency in the pom of a spring boot project, where you want to log for different methods. this info can be obtained from the pom of the project for which we have created the jar.
    
    <dependency>
        <groupId>com.arun</groupId>
        <artifactId>SpringRestCommon</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
## step 6

Annotate your methods with the 

    @RsLogging
    @RsTimeTraker