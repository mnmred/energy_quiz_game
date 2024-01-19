from requests import post
import sys
import os
import json

if len(sys.argv) < 2:
    print("Provide input file")
    sys.exit(1)

input_file = sys.argv[1]

if not os.path.exists(input_file):
    print("Input file does not exist")
    sys.exit(1)

activities = []

with open(input_file, 'r') as file:
    activities = json.load(file)

for act in activities:
    resp = post(
        url="http://localhost:8080/api/activity",
        data=json.dumps({
            "description": act["title"],
            "energyConsumption": act["consumption_in_wh"],
            "picturePath": "activity/" + act["image_path"]}),
        headers={'Content-Type': 'application/json'})
    print(resp)
