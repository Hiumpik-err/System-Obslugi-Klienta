# System Obsługi Klienta

A comprehensive **Customer Service Management System** built with modern .NET technologies, featuring multiple frontend implementations and a robust backend architecture.

## 📋 Project Overview

System Obsługi Klienta (Customer Service System) is a full-stack application designed to streamline customer service operations. The project demonstrates a professional multi-layered architecture with multiple client implementations supporting different platforms.

## 🏗️ Architecture

The project is organized into multiple branches, each handling different aspects of the application:

| Branch | Purpose | Technology |
|--------|---------|-----------|
| **main** | Primary stable branch | - |
| **backend** | API & Business Logic | .NET Backend Services |
| **wpf** | Desktop Application | WPF (Windows Presentation Foundation) |
| **maui** | Cross-Platform Mobile | MAUI (.NET Multi-platform App UI) |

## 🛠️ Technology Stack

- **Backend**: .NET Framework/Core
  - REST API
  - Business Logic Layer
  - Data Access Layer

- **Frontend - Desktop**: WPF
  - Windows-native application
  - Rich user interface components
  - Direct API integration

- **Frontend - Mobile**: MAUI
  - Cross-platform support (Windows, iOS, Android, macOS)
  - Modern UI framework
  - Responsive design

## 🚀 Getting Started

### Prerequisites
- .NET SDK (version as per branch requirements)
- Visual Studio 2022 or Visual Studio Code
- Git

### Building from Source

#### Backend Setup
```bash
git clone https://github.com/Hiumpik-err/System-Obslugi-Klienta.git
git checkout backend
dotnet restore
dotnet build
dotnet run
```

#### WPF Desktop Client
```bash
git clone https://github.com/Hiumpik-err/System-Obslugi-Klienta.git
git checkout wpf
dotnet restore
dotnet build
# Run the application
```

#### MAUI Mobile/Cross-Platform Client
```bash
git clone https://github.com/Hiumpik-err/System-Obslugi-Klienta.git
git checkout maui
dotnet restore
dotnet build
dotnet run
```

## 📦 Project Structure

```
System-Obslugi-Klienta/
├── main/                 # Stable release branch
├── backend/              # API and business logic
├── wpf/                  # Desktop application
└── maui/                 # Cross-platform client
```

## ✨ Features

- **Multi-Client Architecture**: Desktop and mobile implementations
- **RESTful API**: Scalable backend services
- **Cross-Platform Support**: Windows, iOS, Android, and macOS via MAUI
- **Professional UI**: WPF for desktop, MAUI for mobile
- **Modular Design**: Separation of concerns across branches

## 🔄 Development Workflow

1. **Feature Development**: Create feature branches from respective component branches
2. **Testing**: Ensure all tests pass before merging
3. **Code Review**: Submit pull requests for peer review
4. **Integration**: Merge to main branch upon approval

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch from the appropriate branch (backend, wpf, maui)
3. Commit your changes with clear messages
4. Push to your fork
5. Submit a pull request

## 📝 License

This project is provided as-is. Add appropriate license information if needed.

## 👤 Author

**Hiumpik-err**

## 📧 Contact

For questions or support, please open an issue on GitHub.

---

**Last Updated**: June 2026
