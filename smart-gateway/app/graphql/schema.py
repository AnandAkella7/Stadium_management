import strawberry
from typing import List
from app.services.stadium_client import stadium_client

@strawberry.type
class EventType:
    id: int
    title: str
    category: str
    date: str
    location: str
    price: float
    available_seats: int
    status: str

@strawberry.type
class Query:
    @strawberry.field
    def search_events(self, query: str) -> List[EventType]:
        print(f"Received GraphQL Search: {query}")
        results = stadium_client.search_events(query)
        return [EventType(**data) for data in results]

schema = strawberry.Schema(query=Query)
