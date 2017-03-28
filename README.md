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

We have a module [word-server-solution] which can be used for running the word services based on profile like below

    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=subject"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=verb"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=article"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=adjective"
    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=noun"

or we can also create a jar for the [word-server-solution] module and run the same providing the profiles, this would be the suggested way.

The order for running the modules

1. [config-server-solution] this has the config-server
    Looks up config data placed at https://github.com/nandakumar82/configdata
    Contains data used by all the services
2. [eureka-server-solution] this has the eureka-server.
3. [word-server-solution] this has the word services run as a profile (Subject, Verb, Article, Verb, Noun) 5 instances ahve to be spawn with different profiles
4. [sentence-server-solution-using-hystrix] this has the sentence service which consumes all the word services to generate a meaningful sentence.
5. [turbine-solution] this has the turbine integration

http://localhost:8010/ is the URL to the Eureka Server [All the services are registered here]
http://localhost:8020/sentence is the URL to the Sentence Service
http://localhost:8020/hystrix is the URL to the Hystrix Dashboard

For teh purpose of demoing fallback and load balancing using Feign and Ribbon, I have created another module [word-server-solution-1] which has the same code as in the module [word-server-solution] but there is an exception thrown here.

We will run  all the word services as mentioned earlier along with a another Subject instance which is run from the [word-server-solution-1] module as above

mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=subject"

Now if you examine Eureka , you will notice that all the instances are up along with Subject which has 2 instances which are running.

Now let us place a request to the URL

http://localhost:8020/sentence

Examine the logs generated in the console for the Subject and Subject2 Instances.
The requests will be following the round robbin pattern, whenever there is a failure in the Subject instance, the Hystrix Code fallsback kicks in to provide a placeholder which is 'Someone' as the Subject.


Some Sentences


You knew a reasonable book.

Someone knew a ordinary seat.

You knew a ordinary book.

Someone had the leaky groundhog.

She had the suspicious book.

Elapsed time (ms): 1280







