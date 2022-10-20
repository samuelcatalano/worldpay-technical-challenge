# WorldPay
WorldPay Technical Challenge

![Image description](https://i.imgur.com/HaPd3Is.png)

### Stack:
| Technology | Version |
|--|--|
| **JAVA** | 11.0.3-2018-01-14 |
| **Spring Boot** | 2.2.5.RELEASE |
| **Project Lombok** | 1.18.12 |
| **H2 Database** | 1.4.200 |
| **JUnit 5** | 5.6.2 |
| **Springfox Swagger 2** | 2.9.2 |

### Acessing Swagger | Open API:
Once with the application running:
http://localhost:8080/swagger-ui.html

### Docker :

Exists a Dockerfile prepared to download a OpenJDK 11 Slim and install the application.

- Run the command: `docker build -t worldpay/challenge:release .`
- Run the command: `docker run -p port:port <IMG_TAG>`
- Example: `docker run -p 8080:8080 8fb870f41548`
- Or download the image `docker pull samueldnc/samuelcatalano:worldpay`
