# Ikodave - Coding Practice Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-green.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)

An open-source coding practice platform designed to help users master algorithms and prepare for technical interviews. Built with Java, Spring MVC, and modern web technologies.

## 🚀 Features

### Core Functionality
- **Problem Solving**: 15+ algorithmic problems with varying difficulty levels
- **Code Execution**: Real-time code compilation and execution using Docker containers
- **Multi-language Support**: Java, Python, C, C++ programming languages
- **User Authentication**: Secure login/registration system with password hashing
- **Progress Tracking**: User submission history and problem status tracking
- **Leaderboard**: Competitive ranking system based on problem solving
- **Admin Panel**: Problem management and user administration

### Problem Categories
- **Easy**: Basic algorithms and data structures
- **Medium**: Advanced algorithms and optimization
- **Hard**: Complex algorithmic challenges

### Supported Languages
- **Java**: Full support with compilation and execution
- **Python**: Script execution with safety measures
- **C/C++**: Compiled execution with memory management
- **JavaScript**: Browser-based execution (planned)

## Architecture

### Backend Stack
- **Java 17**: Core application language
- **Maven**: Dependency management and build tool
- **MySQL 8.0**: Primary database
- **Tomcat 9**: Web application server
- **Docker**: Code execution environment
- **JUnit 5**: Testing framework

### Frontend Stack
- **HTML5/CSS3**: Modern responsive design
- **JavaScript**: Dynamic user interactions
- **Bootstrap 5**: UI framework
- **AJAX**: Asynchronous data communication

### Database Schema
```
├── users (user management)
├── problems (problem definitions)
├── submissions (code submissions)
├── test_cases (problem test cases)
├── leaderboard (user rankings)
└── admin (administrative data)
```

## 📁 Project Structure

```
ikodave/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── admin/           # Admin panel functionality
│   │   │   ├── general_servlets/ # Core servlets and filters
│   │   │   ├── home/            # Home page functionality
│   │   │   ├── leaderboard/     # Leaderboard system
│   │   │   ├── problems/        # Problem management
│   │   │   ├── registration/    # User authentication
│   │   │   ├── submissions/     # Code execution system
│   │   │   ├── user_profile/    # User profile management
│   │   │   ├── util/            # Utility classes
│   │   │   └── listener/        # Application listeners
│   │   ├── webapp/
│   │   │   ├── static/          # Frontend assets
│   │   │   │   ├── admin/       # Admin panel UI
│   │   │   │   ├── authentication/ # Login/register pages
│   │   │   │   ├── leaderboard/ # Leaderboard UI
│   │   │   │   ├── problems/    # Problem listing UI
│   │   │   │   ├── problem/     # Individual problem UI
│   │   │   │   ├── profile/     # User profile UI
│   │   │   │   ├── submit/      # Code submission UI
│   │   │   │   └── submissions/ # Submission history UI
│   │   │   └── WEB-INF/         # Web configuration
│   │   └── sql/                 # Database scripts
│   └── test/                    # Unit tests
├── pom.xml                      # Maven configuration
└── README.md                    # This file
```

## Installation & Setup

### Prerequisites
- **Java 17** or higher
- **Maven 3.8+**
- **MySQL 8.0+**
- **Docker** (for code execution)
- **Git**

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/ikodave.git
   cd ikodave
   ```

2. **Set up the database**
   ```bash
   # Create MySQL database
   mysql -u root -p
   CREATE DATABASE Ikodave;
   
   # Run database scripts
   mysql -u root -p Ikodave < src/main/java/com/example/sql/database.sql
   mysql -u root -p Ikodave < src/main/java/com/example/sql/insertAdmin.sql
   mysql -u root -p Ikodave < src/main/java/com/example/sql/insertProblems.sql
   mysql -u root -p Ikodave < src/main/java/com/example/sql/insertTestCases.sql
   ```

3. **Configure database connection**
   ```java
   // Update src/main/java/com/example/util/DatabaseConstants.java
   public static final String DB_URL = "jdbc:mysql://localhost:3306/Ikodave";
   public static final String DB_USER = "your_username";
   public static final String DB_PASSWORD = "your_password";
   ```

4. **Build and run**
   ```bash
   mvn clean install
   mvn tomcat9:run
   ```

5. **Access the application**
   ```
   http://localhost:8081
   ```

### Docker Setup (Optional)
```bash
# Build Docker image for code execution
docker build -t ikodave-executor src/main/java/com/example/submissions/Docker/
```

##  Testing

### Run Unit Tests
```bash
mvn test
```

### Run Specific Test Categories
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProblemServletTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Coverage
- **Unit Tests**: 90%+ coverage
- **Integration Tests**: Database and servlet testing
- **End-to-End Tests**: User workflow testing

##  Database Schema

### Core Tables
- **`users`**: User accounts and authentication
- **`problems`**: Problem definitions and metadata
- **`submissions`**: Code submissions and results
- **`test_cases`**: Problem test cases
- **`leaderboard`**: User rankings and scores

### Relationships
```
users (1) ←→ (many) submissions
problems (1) ←→ (many) submissions
problems (1) ←→ (many) test_cases
users (1) ←→ (1) user_profile
```

## 🔧 Configuration

### Application Properties
- **Database**: MySQL connection settings
- **Docker**: Code execution container settings
- **Security**: Password hashing and session management
- **Performance**: Connection pooling and caching

### Environment Variables
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=Ikodave
export DB_USER=your_username
export DB_PASSWORD=your_password
export DOCKER_HOST=unix:///var/run/docker.sock
```

##  Deployment

### Production Deployment
1. **Build WAR file**
   ```bash
   mvn clean package
   ```

2. **Deploy to Tomcat**
   ```bash
   cp target/ikodave.war $TOMCAT_HOME/webapps/
   ```

3. **Configure production database**
    - Set up MySQL with proper security
    - Configure connection pooling
    - Set up monitoring and logging

### Docker Deployment
```bash
# Build application image
docker build -t ikodave .

# Run with Docker Compose
docker-compose up -d
```

## 👥 Contributing

### Development Workflow
1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
4. **Add tests for new functionality**
5. **Submit a pull request**

### Code Style
- **Java**: Follow Google Java Style Guide
- **JavaScript**: Use ESLint configuration
- **SQL**: Use consistent naming conventions
- **Documentation**: Update README for new features

### Testing Guidelines
- Write unit tests for all new functionality
- Ensure 90%+ code coverage
- Test edge cases and error conditions
- Include integration tests for database operations

##  Troubleshooting

### Common Issues

#### Database Connection
```bash
# Check MySQL service
sudo systemctl status mysql

# Verify database exists
mysql -u root -p -e "SHOW DATABASES;"
```

#### Docker Issues
```bash
# Check Docker service
sudo systemctl status docker

# Verify Docker permissions
sudo usermod -aG docker $USER
```

#### Port Conflicts
```bash
# Check if port 8081 is in use
netstat -tulpn | grep 8081

# Change port in pom.xml if needed
<port>8082</port>
```

##  Performance

### Optimization Features
- **Connection Pooling**: Database connection management
- **Caching**: Problem and user data caching
- **Async Processing**: Background code execution
- **Resource Management**: Memory and CPU optimization

### Monitoring
- **Application Metrics**: Response times and throughput
- **Database Performance**: Query optimization and indexing
- **System Resources**: CPU, memory, and disk usage

##  Security

### Security Features
- **Password Hashing**: BCrypt encryption
- **SQL Injection Prevention**: Prepared statements
- **XSS Protection**: Input sanitization
- **Session Management**: Secure session handling
- **Docker Isolation**: Code execution sandboxing

### Security Best Practices
- Regular security updates
- Input validation and sanitization
- Proper error handling
- Secure configuration management

##  API Documentation

### REST Endpoints
```
GET  /problems          # List all problems
GET  /problems/{id}     # Get specific problem
POST /submit            # Submit code solution
GET  /leaderboard       # Get leaderboard
GET  /profile           # Get user profile
POST /login             # User authentication
POST /register          # User registration
```

### Request/Response Examples
```json
// Submit solution
POST /submit
{
  "problemId": 1,
  "language": "java",
  "code": "public class Solution { ... }"
}

// Response
{
  "verdict": "ACCEPTED",
  "executionTime": 15,
  "memoryUsed": 256
}
```

## Authors

**K.N.S.S. Team**
- **K**onstantine Endeladze
- **N**ickolas Metreveli
- **S**andro Bolkvadze
- **S**aba Losaberidze

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **Bootstrap**: UI framework
- **Docker**: Containerization platform
- **MySQL**: Database system
- **JUnit**: Testing framework
- **Maven**: Build automation


**Ikodave** - Empowering developers to master algorithms through practice and competition.
