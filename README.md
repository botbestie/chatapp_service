# Chat App Service

A Spring Boot application that provides a REST API for integrating with Anthropic's Claude AI models. This service implements proper project structure with Spring Security, interface/implementation pattern, and reactive programming using WebClient.

## Features

- **Spring Boot 3.2.0** with Java 17
- **Spring Security** for authentication and authorization
- **WebClient** for reactive HTTP calls to Anthropic API
- **Interface/Implementation** pattern for clean architecture
- **Gradle** build system
- **Lombok** for reducing boilerplate code
- **Comprehensive error handling** with global exception handler
- **Validation** for request DTOs
- **Actuator** endpoints for monitoring
- **Unit tests** with JUnit 5 and Reactor Test

## Project Structure

```
src/
├── main/
│   ├── java/com/mychatbot/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Exception handling
│   │   ├── service/         # Service interfaces
│   │   └── service/impl/    # Service implementations
│   └── resources/
│       └── application.yml  # Application configuration
└── test/
    ├── java/com/mychatbot/  # Test classes
    └── resources/
        └── application-test.yml  # Test configuration
```

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher
- Anthropic API key

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd chatapp_service
   ```

2. **Set your Anthropic API key**
   ```bash
   export ANTHROPIC_API_KEY="your-actual-api-key-here"
   ```
   
   Or update the `application.yml` file directly.

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`

## Configuration

### Anthropic API Settings

Update `src/main/resources/application.yml`:

```yaml
anthropic:
  api:
    key: ${ANTHROPIC_API_KEY:your-api-key-here}
    base-url: https://api.anthropic.com
    model: claude-3-sonnet-20240229
    max-tokens: 1000
    temperature: 0.7
```

### Security Settings

Default credentials:
- Username: `admin`
- Password: `admin123`

## API Endpoints

### Public Endpoints (No Authentication Required)

- `GET /api/public/info` - Service information
- `GET /api/public/ping` - Health check
- `GET /actuator/health` - Spring Boot health check

### Protected Endpoints (Authentication Required)

- `POST /api/chat/send` - Send a chat message
- `POST /api/chat/send-simple` - Send a simple message
- `GET /api/chat/health` - Chat service health check

## Usage Examples

### Send a Chat Message

```bash
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "message": "Hello, how are you?",
    "userId": "user123",
    "sessionId": "session456"
  }'
```

### Send a Simple Message

```bash
curl -X POST "http://localhost:8080/api/chat/send-simple?message=Hello%20world" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### Get Service Info

```bash
curl http://localhost:8080/api/public/info
```

## Testing

Run the tests:

```bash
./gradlew test
```

Run with coverage:

```bash
./gradlew test jacocoTestReport
```

## Development

### Adding New Features

1. **Service Layer**: Add new methods to the service interface in `src/main/java/com/mychatbot/service/`
2. **Implementation**: Implement the methods in `src/main/java/com/mychatbot/service/impl/`
3. **Controller**: Add new endpoints in the appropriate controller
4. **DTOs**: Create new DTOs if needed in `src/main/java/com/mychatbot/dto/`
5. **Tests**: Write tests for new functionality

### Code Style

- Use Lombok annotations to reduce boilerplate
- Follow Spring Boot best practices
- Use reactive programming with Mono/Flux where appropriate
- Implement proper error handling
- Add comprehensive logging

## Security Considerations

- API key is stored in environment variables
- Basic authentication is enabled for protected endpoints
- CSRF is disabled for API endpoints
- Input validation is implemented for all requests
- Error messages don't expose sensitive information

## Monitoring

The application includes Spring Boot Actuator for monitoring:

- Health checks: `/actuator/health`
- Application info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Troubleshooting

### Common Issues

1. **API Key Error**: Ensure `ANTHROPIC_API_KEY` environment variable is set
2. **Port Already in Use**: Change port in `application.yml` or stop conflicting service
3. **Build Failures**: Ensure Java 17+ is installed and `JAVA_HOME` is set correctly

### Logs

Check application logs for detailed error information. Logging is configured at DEBUG level for development.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.
