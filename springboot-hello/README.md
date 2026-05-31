# hello - Spring Boot 3.2 Conversion

Converted from the original `hello.py` script:

```python
#!/usr/bin/env python3
print("Hello World!!")
```

## Project Structure

- **Spring Boot 3.2.5** + Java 17 + Maven
- `spring-boot-starter-web`
- Prints "Hello World!!" on application startup
- REST endpoints:
  - `GET /` → "Hello World!!"
  - `GET /hello` → "Hello World!! (from /hello endpoint)"

## Run

```bash
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

Then open http://localhost:8080/

## Notes

- This is a minimal but complete Spring Boot 3.2 project ready for expansion (add JPA, security, etc. as needed).
- Original Python script was a trivial POC; this version adds both CLI and HTTP capabilities.
