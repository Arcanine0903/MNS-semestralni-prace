import requests  # type: ignore[import]
import time
import random

# Simulates RaceControl sending telemetry data to the application
url = "http://localhost:8080/api/telemetry/results"


racers = [
    {"racerId": 2, "kartId": 1},
    {"racerId": 3, "kartId": 2},
    {"racerId": 4, "kartId": 3},
    {"racerId": 2, "kartId": 1} # This will get marked as a duplicate and will be ignored by the application
]

print("Beginning RaceControl simulation...")

for racer in racers:
    best_time = random.randint(60000, 80000)
    
    payload = {
        "raceId": 1,
        "racerId": racer["racerId"],
        "kartId": racer["kartId"],
        "placingStart": 1,
        "placingEnd": 1,
        "bestTime": best_time
    }
    
    response = requests.post(url, json=payload)
    print(f"Sent time {best_time}ms for racer {racer['racerId']}. Response: {response.text}")

    # Waits 3 seconds to simulate time between telemetry updates
    time.sleep(3) 

print("Simulation complete.")