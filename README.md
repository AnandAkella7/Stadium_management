# Stadium Management System

This repo hosts the Microservices-based Stadium Management backend and the Agentic Gateway.

## Quick Start (Windows)

We've set up a PowerShell script to handle the multi-process startup for local development.

### 1. Start Services
Run the bootstrapper:
```powershell
.\start_system.ps1
```
This launches two new windows:
- **Java Event Service** (Port 8082 Web / 9090 gRPC)
- **Python Smart Gateway** (Port 8005)

Wait for both to initialize ("Started EventServiceApplication" / "Uvicorn running").

### 2. Run Verification
To verify the entire flow (Agent -> gRPC -> Java -> DB), run the test script:
```powershell
py test_agent.py
```
> Note: Use `py` instead of `python` if you have multiple python environments or Cygwin conflict.

You should see a successful JSON response containing event data.

## System Components

- **Smart Gateway (Python/FastAPI)**: The BFF (Backend for Frontend) and AI Agent entry point. Uses GraphQL for flexible queries and connects to backend services via gRPC.
- **Event Service (Java/Spring Boot)**: Core domain service for managing events. Exposes gRPC endpoints for internal communication.
- **Common Service**: Shared Protobuf definitions.

## Next Steps
- Implement `booking-service` for ticket reservations.
- Integrate PostgreSQL on AWS for production persistence.
- Add authentication flow (JWT) to the Gateway.
