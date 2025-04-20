# DhasuZone E-commerce Platform

DhasuZone is a complete e-commerce solution built with Java Spring Boot and pure HTML/CSS. This platform offers a modern, responsive shopping experience with a wide range of features for both customers and administrators.

## Features

- Responsive product catalog with categories and filters
- User authentication and profile management
- Shopping cart and checkout functionality
- Secure payment processing with Razorpay integration
- Order management and tracking
- Customer reviews and ratings
- AI-powered chatbot for customer assistance
- Admin dashboard for product and order management
- Responsive design for all devices

## Technology Stack

### Frontend
- HTML5
- CSS3
- JavaScript
- FontAwesome for icons

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Security
- Thymeleaf templating

### Database
- MySQL (configured in application properties)
- JSON-based repositories for development

### Payment Integration
- Razorpay API

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- MySQL (optional, can use JSON-based storage for development)

### Installation

1. Clone the repository
```
git clone https://github.com/yourusername/dhasuzone.git
```

2. Navigate to the project directory
```
cd dhasuzone
```

3. Build the project
```
mvn clean install
```

4. Run the application
```
mvn spring-boot:run
```

5. Open your browser and go to `http://localhost:8080`

## Configuration

### Database Configuration
To switch from JSON-based storage to MySQL:

1. Uncomment the database configuration in `src/main/resources/application.properties`
2. Create a MySQL database named `dhasuzone`
3. Update the username and password in the properties file

### Razorpay Configuration
Update the Razorpay API keys in `src/main/resources/application.properties`:
```
razorpay.key.id=your_key_id
razorpay.key.secret=your_key_secret
```

## Project Structure

```
dhasuzone/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dhasuzone/
│   │   │           └── ecommerce/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               └── DhasuZoneApplication.java
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   └── images/
│   │   │   ├── templates/
│   │   │   ├── data/
│   │   │   └── application.properties
│   │   └── webapp/
│   └── test/
├── pom.xml
└── README.md
```

