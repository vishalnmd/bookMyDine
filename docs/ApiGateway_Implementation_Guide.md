# API Gateway Implementation Guide (Project-specific)

This guide is tailored to the current project (BookMyDine). It documents mistakes found, fixes applied, and step-by-step reference for implementing Spring Cloud Gateway with Eureka in this repository.

---

## 1) Mistakes observed (project-specific)

- YAML syntax error: stray `discovery;` token in ApiGateway's `application-local.yaml` prevented parsing.
- Duplicate / mis-indented keys: extra nested `cloud:` under `spring` caused properties to be ignored.
- Wrong profile activation initially (nonstandard property), so `application-local.yaml` wasn't being loaded. Fixed by setting `spring.profiles.active: local` in `application.yaml`.
- Malformed Eureka URL previously had angle brackets — now set to `http://localhost:8761/eureka/` (no angle brackets).
- Gateway routing mismatch: gateway used `lb://USERSERVICE` while the registered `spring.application.name` is `UserService` — pay attention to case or enable lower-case mapping.

---

## 2) Project dependencies (exact used)

This project uses the WebFlux-flavored gateway starter:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-gateway-server-webflux</artifactId>
</dependency>
```

Recommendation: add the configuration processor to improve IDE YAML suggestions (optional, but helpful):

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-configuration-processor</artifactId>
  <optional>true</optional>
</dependency>
```

---

## 3) Current relevant config files (project)

ApiGatewayService/src/main/resources/application.yaml

```yaml
spring:
  application:
    name: ApiGatewayService
  profiles:
    active: local
```

ApiGatewayService/src/main/resources/application-local.yaml (current project file)

```yaml
spring:
  application:
    name: ApiGatewayService

  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USERSERVICE
          predicates:
            - Path=/users/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

management:
  endpoints:
    web:
      exposure:
        include: "*"

logging:
  level:
    org.springframework.cloud.gateway: TRACE
```

UserService/src/main/resources/application.yaml

```yaml
spring:
  application:
    name: UserService
  profiles:
    active: local

server:
  port: 8082
```

UserService/src/main/resources/application-local.yaml

```yaml
# Eureka Client Configuration
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

---

## 4) Why the gateway might not route to UserService

1. Profile not active: if ApiGateway didn't load `application-local.yaml`, the configured routes won't be applied. Ensure `spring.profiles.active: local` is set in `application.yaml` (done).
2. YAML parse errors: any invalid token (e.g., `discovery;`) stops parsing the file — fix syntax errors.
3. Service name mismatch: Gateway uses `lb://USERSERVICE` but UserService registers as `UserService`. By default service IDs are case-sensitive in some lookups. Solutions:
   - Use the exact registered name: `lb://UserService` in gateway routes.
   - Or enable discovery locator with `lower-case-service-id: true` so discovery maps registrations to lowercase.
4. Eureka problems: ensure Eureka server reachable at `http://localhost:8761/eureka/` and both services can connect.
5. If using discovery locator (recommended), enable `spring.cloud.gateway.discovery.locator.enabled=true` instead of static routes.

---

## 5) Recommended gateway configs (two approaches)

A) Static routes (current project uses this):

- Keep current `routes` entry, but ensure `uri` uses the exact serviceId matching `spring.application.name` from UserService. Example:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://UserService   # match spring.application.name exactly (case-sensitive)
          predicates:
            - Path=/users/**
```

B) Discovery-based routes (auto-generated from Eureka) — simpler and less brittle:

```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

Notes: discovery locator auto-creates routes of the form `/{serviceId}/**` by default and uses `lb://<serviceId>` internally.

---

## 6) Verification steps (project)

1. Start Eureka server (http://localhost:8761) and open dashboard.
2. Start UserService (port 8082). Confirm it appears in Eureka UI (look for `UserService`).
3. Start ApiGateway. Confirm gateway logs show Eureka client fetching registry or route registration.
4. Test via curl:

```bash
# Directly to service
curl -v http://localhost:8082/actuator/health

# Via gateway (default gateway port usually 8080)
curl -v http://localhost:8080/users/health
```

5. If 404 or routing issues:
- Check gateway logs at TRACE for `org.springframework.cloud.gateway` and `org.springframework.cloud.client.discovery`.
- Confirm service name casing: try `lb://UserService` vs `lb://USERSERVICE`.
- Confirm Eureka registration using `http://localhost:8761/`.

---

## 7) Troubleshooting checklist

- [ ] YAML valid (use `yamllint` or IDE). No stray semicolons.
- [ ] Profiles active: `spring.profiles.active: local` or start with `--spring.profiles.active=local`.
- [ ] Eureka URL reachable and correct (no angle brackets).
- [ ] Service names match or enable `lower-case-service-id`.
- [ ] Gateway dependencies present (WebFlux gateway + eureka client).
- [ ] If using discovery locator, enable it and remove conflicting static routes.

---

## 8) IDE and YAML suggestions

- Add `spring-boot-configuration-processor` to improve IDE completion for Spring properties.
- Ensure files are in `src/main/resources`.
- Invalidate caches and restart IntelliJ after adding dependencies and building the project.

---

## 9) Next steps for repository

- (Optional) Move this file into the repo `docs/` (done).
- Consider switching to discovery locator for less maintenance.
- Add tests for gateway endpoints (integration tests) to catch routing regressions early.

---

If you'd like, update the gateway to use discovery locator now (I can change `application-local.yaml`), or change the gateway `uri` to `lb://UserService` to match current service registration. Which do you prefer?

