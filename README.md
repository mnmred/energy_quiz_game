# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development environment, and who worked on it.
This information can be added throughout the course, except for the names of the group members.
Add your own name (do not add the names for others!) to the section below.

## Description of project

## Group members

| Profile Picture | Name | Email |
|---|---|---|
| ![](https://secure.gravatar.com/avatar/60cc0ca6ca48b85e072af402acd471a6?s=800&d=identicon&length=4&size=50) | Vian Robotin | V.Robotin@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/6f9749b354d325c57c85b6c97ffb1384?s=800&d=identicon&length=4&size=50) | Reinier Schep | R.J.H.Schep@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/ab806692677aacd979553d1a9142ed4d?s=800&d=identicon&length=4&size=50) | Mana Mahmoudi | M.Mahmoudi-1@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/7a88d88d5709b14830d314f2e4a1565f?s=800&d=identicon&length=4&size=50) | Laimonas Lipinskas | L.Lipinskas@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/2fa64bbae34c0f39d39ed3d160db856a?s=180&d=identicon&length=4&size=50) | Kuba Trzykowski | J.A.Trzykowski@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/6c8d3b8b928dbeb3ffb992135a115c32?s=800&d=identicon&length=4&size=50) | Simona Lupșa | S.C.Lupsa@student.tudelft.nl |



## How to run it

### **Important!**

This tutorial assumes that:
- You have cloned the latest main branch of this repo to a local folder on your machine.
- You have python3 installed on your machine. 

### Setting up the server (this has to be done only once).

1. Open the project in Intellij.
2. You should get a notification that says that a Gradle build script has been found, load it.
3. Navigate to `repository-template/server/src/main/java/server/Main.java` and click `setup JDK`.
4. Click `Add Configuration` (located top right to the right of the green hammer icon)
5. Click `Add New Configuration` (the '+' sign top left)
6. Select `Spring Boot` from the list.
7. Set the name to `Main` and Main class to `server.Main`.
8. Click `OK`.
9. Run the server by selecting the `Main` configuration and clicking the green play button, this is necessary
for the following step! 
10. Navigate to `repository-template/helper-scripts` and open your terminal in that directory.
11. Copy the absolute path of the activities.json file, it can be found in `repository-template/server/src/main/resources/activities.json`
12. Execute the following command in your terminal:
```python3 add_activities.py "C:\Users\YourPath\activities.json"```
13. You should see a bunch of `<Response [200]>` printing to your terminal, once this finishes the database 
has been successfully populated with activities

### Running the server
1. Select the `Main` configuration.
2. Click the green play button to the right of it.
3. The server will start.

### Running the client
1. Click on the Gradle logo located on the right.
2. Navigate to `quizzzz/client/Tasks/application`
3. Once here, double click run, this will load the client.


### FAQ
**Question**

When i run the client on my laptop, it's bigger than my screen, how can i make it fit properly?

**Answer**

Assuming you're on Windows 10, go to Settings > System > Display > Scale and Layout 
and set your scaling setting to 100%.


## How to contribute to it

1. Pick an issue you'd like to work on.
2. Make branch off the dev branch with an appropriate name.
3. Complete the feature on this branch.
4. Make a merge request to merge your branch back into dev.

## Copyright / License (opt.)
