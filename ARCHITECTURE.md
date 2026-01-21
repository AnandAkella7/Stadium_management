# Architecture & Design Decisions

## The "Why" Behind the Stack

We didn't just pick random cool tech here. The goal was to build a system that can handle high-concurrency stadium ticketing while remaining flexible enough for an AI agent interface.

### 1. Microservices Architecture
**Why we chose it:**
Stadium systems have distinct domains that scale differently. `TicketService` gets hammered during a sale; `EventService` is read-heavy. Splitting them lets us scale independently and keeps the codebases focused.
**Trade-off:** Complexity in deployment and inter-service communication. We mitigate this with gRPC and shared proto definitions.

### 2. Python (FastAPI) for the Gateway / Agent
**Why we chose it:**
If we want an "Agentic" system that can reason about API calls or integrate with LLMs, it needs to be in Python. FastAPI gives us high performance (async) and automatic GraphQL support (via Strawberry).
**Role:** It acts as a BFF (Backend for Frontend). Clients/Agents talk to this; this talks to the Java backend.

### 3. Java (Spring Boot) for Core Services
**Why we chose it:**
Robustness. Spring Boot is battle-tested. For the heavy lifting of transactional ticket processing and consistency, Java's strict typing and ecosystem are unbeatable.

### 4. gRPC vs REST
**Why we chose gRPC:**
REST is great for browsers, but for service-to-service (Python -> Java), it's chatty and slow. gRPC (Protobuf) gives us:
- **Strong contracts:** If the Java side changes a field, the build breaks. No more aggressive "runtime surprises."
- **Performance:** Binary format is lighter and faster than JSON.
**Trade-off:** Harder to debug with `curl`. We rely on unit tests and integration scripts (like `test_agent.py`) to verify.

### 5. GraphQL for the Client/Agent
**Why we chose it:**
Agents serve generic queries. An agent might ask "What's the price of the Taylor Swift concert?" or "Show me all events in Mumbai." GraphQL lets the agent request exactly what it needs without us writing 50 different REST endpoints for every permutation.

## Next Steps / Roadmap
1.  **Persistence**: Move from H2 (In-Memory) to **PostgreSQL**.
2.  **State Management**: Implement Redis for holding temporary seat reservations (the "5 minutes to buy" lock).
3.  **Deployment**: Dockerize services and push to ECS/Kubernetes.
