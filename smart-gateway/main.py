from fastapi import FastAPI
from strawberry.fastapi import GraphQLRouter
from app.graphql.schema import schema
import sys
import asyncio

# Fix for Windows Event Loop Issue
if sys.platform.startswith('win'):
    asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())

app = FastAPI(title="Smart Stadium Gateway")

graphql_app = GraphQLRouter(schema)

app.include_router(graphql_app, prefix="/graphql")

@app.get("/")
def read_root():
    return {"message": "Smart Gateway is running. Go to /graphql"}

if __name__ == "__main__":
    import uvicorn
    print("--- STARTING GATEWAY ON PORT 8005 ---")
    uvicorn.run(app, host="0.0.0.0", port=8005)
