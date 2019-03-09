# Microservice One

Microservicio que obtiene el refresh de sus propiedades con RabbitMQ (AMQP) y utiliza el patron Circuit Breaker.

1. Pre-Requisitos:
	* Java >= 1.8.x
	* Spring Boot 2.1.3.RELEASE
	* Spring Cloud Greenwich.RELEASE
	* RabbitMQ

2. Dependencias:
	* Actuator.
	* Cloud Config: Config Client.
	* Cloud Discovery: Eureka Discovery.
	* Cloud Routing: Ribbon, Feign.
	* Circuit Breaker: Hystrix, Hystrix Dashboard
	* Spring Bus AMQP.
	* Spring Web.
	* Swagger.

3. Run RabbitMQ with Docker:
```
docker image pull rabbitmq:3-management
docker run -d --name rabbitmq -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:3-management
```

4. Anotar con `@EnableDiscoveryClient` a la clase de configuración.

5. Definir beans en clase de configuración:
```[java]
@Configuration
public class AppConfiguration {
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	} 
}
```

6. Anotar bean o componente con `@RefreshScope` donde queremos que las propiedades se actualicen:
```[java]
@RefreshScope
@RestController
@RequestMapping(value = "/init")
public class InitController {
	
	@Value(value = "${properties.example}")
	private String exampleProperty;
	
	@GetMapping
	public ResponseEntity<String> init() {
		return ResponseEntity.ok().body(exampleProperty);
	}
}
```
 
7.  Cambiar `application.properties` a `bootstrap.yml` y agregar:
 
 ```[yaml]
 spring:  
  application:
    name: microservice-one
  cloud:
    config:
      discovery:
        enabled: true
        serviceId: config-server
 ```
 
8. Para utilizar el refresh mediante colas de mensajes con RabitMQ debemos crear un WebHook con nuestro remoto, esto presenta dificultades cuando trabajamos con localhost. 
Primero verificaremos el estado actual de la propiedad consumiendo en `GET http://localhost:8070/init`, luego realizaremos un cambio en la propiedad `${properties.example}` en `microservice-one.yml` y subiremos los cambios al remoto, luego simularemos un petición de un WebHook de la siguiente forma:
 
 ```
 curl -H "X-Github-Event: push" -H "Content-Type: application/json" -X POST -d '{"commits": [{"modified": ["microservice-one.yml"]}]}' http://localhost:8888/monitor
 ```
Más tarde podremos verificar `GET http://localhost:8070/init` con nuestro cliente Postman que los cambios se realizaron.

9. Anotar con *@EnableHystrix* y `@EnableHystrixDashboard` para habilitar el patron circuit breaker y la dashboard de hystrix.

10. Anotar método donde se utilizara el patron circuit breaker con `@HystrixCommand` (Esto solo se puede hacer en clases anotadas con `@Component` o `@Service`)

```[java]
@Service
public class GreetingServiceImp implements GreetingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GREETING_SERVICE_URL = "http://microservice-two/greeting";
	
	@HystrixCommand(fallbackMethod = "greetingFallback", 
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
			})
	@Override
	public GreetingResponse greeting(GreetingRequest request) {
		return restTemplate.postForObject(GREETING_SERVICE_URL, request, GreetingResponse.class);
	}
	
	public GreetingResponse greetingFallback(GreetingRequest request) {
		GreetingResponse response = new GreetingResponse();
		response.setMessage(request.getName() + ", an error occurred");
		return response;
	}
}
```

11. Habilitar Hystrix para FeignClient par el microservice-one en `microservice-one.yml` de [sb-config-repo](https://github.com/dabliuw22/sc-config-repo):
```[yml]
feign:
  hystrix:
	enabled: true
```

12. Verificar Swagger: `http://localhost:8070/swagger-ui.html` o `http://localhost:9090/microservice-one/swagger-ui.html`