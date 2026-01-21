import grpc
from app.proto import stadium_pb2
from app.proto import stadium_pb2_grpc

class StadiumClient:
    def __init__(self, host="localhost", port=9090):
        # We will need to configure the Java service to run on 9090 or change this
        self.channel = grpc.insecure_channel(f"{host}:{port}")
        self.stub = stadium_pb2_grpc.StadiumEventServiceStub(self.channel)

    def search_events(self, query: str):
        request = stadium_pb2.EventSearchRequest(query=query)
        try:
            response = self.stub.SearchEvents(request)
            return [
                {
                    "id": event.id,
                    "title": event.title,
                    "category": event.category,
                    "date": event.date_time,
                    "location": event.location,
                    "price": event.price,
                    "available_seats": event.available_seats,
                    "status": event.status
                }
                for event in response.events
            ]
        except grpc.RpcError as e:
            print(f"gRPC Error: {e}")
            return []

# Singleton instance
stadium_client = StadiumClient()
