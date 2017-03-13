The project set up here is a template for the purpose of understanding how all the components related to Spring Cloud coexist.

The components in question are

1. Config Server
2. Eureka Server
3. Load Balancing using Ribbon
4. Using Feign as rest client which provides load balancing through Ribbon hence abstracting the compexities of load balancing
5. Using hystrix as a circuit breaker and enabling Hystrix dashboards
6. Using Turbine for integrating multiple hystrix dashboards

Our project here is a simple application which hosts multiple word services in the order(Subject, Verb, Article, Verb, Noun)
There is a sentence service [sentence-server-solution-using-hystrix] or [sentence-server-solution-using-feign] or [sentence-server-solution-client-load-balancer] which requests the (Subject, Verb, Article, Verb, Noun) services to generate a meaningful sentence.

We have a module [eureka-client-solution] which can be used for running the word services based on profile like below

    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=subject"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=verb"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=article"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=adjective"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=noun"

or we can also create a jar for the [eureka-client-solution] module and run the same providing the profiles, this would be the suggested way.

The order for running the modules

1. [config-server-solution] this has the config-server
    Looks up config data placed at https://github.com/nandakumar82/configdata
    Contains data used by all the services
2. [eureka-server-solution] this has the eureka-server.
3. [eureka-client-solution] this has the word services run as a profile (Subject, Verb, Article, Verb, Noun) 5 instances ahve to be spawn with different profiles
4. [sentence-server-solution-using-hystrix] this has the sentence service which consumes all the word services to generate a meaningful sentence.
5. [turbine-solution] this has the turbine integration

http://localhost:8010/ is the URL to the Eureka Server [All the services are registered here]
http://localhost:8020/sentence is the URL to the Sentence Service
http://localhost:8020/hystrix is the URL to the Hystrix Dashboard





