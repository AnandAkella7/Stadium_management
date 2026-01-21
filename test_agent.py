import requests
import json
import sys

# 1. Define the GraphQL Query
# We use an empty query string to fetch ALL events
query = """
query {
  searchEvents(query: "") {
    title
    price
    availableSeats
    location
  }
}
"""

url = "http://localhost:8005/graphql"

print(f"Testing connectivity to {url}...")

try:
    # 2. Add longer timeout because first run might be slow
    response = requests.post(url, json={"query": query}, timeout=10)
    
    if response.status_code == 200:
        print("[SUCCESS] Agent responded:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"[FAILED] with Status {response.status_code}")
        print(response.text)

except requests.exceptions.ConnectionError:
    print("[CONNECTION FAILED] Is the Python Gateway running on port 8005?")
except Exception as e:
    print(f"[ERROR] {e}")
