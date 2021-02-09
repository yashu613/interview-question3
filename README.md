Interview question
==================


This is a very basic spring-boot app. Run it (using `mvn spring-boot:run`) or your favorite IDE.
Try the url `http://localhost:5000/greeting?name=David`, it should return the string: "Hello David".

You should use this template to develop a forum system.

# Requirements
We want to develop 4 APIs:

### Post new question: `http://localhost:5000/questions`
with body:
```json
{
  "author": "Daniel",
  "message": "Message text"
}
```
Response should be 201:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": 0
}
```

### Post a reply to a message: `http://localhost:5000/questions/{questionId}/reply`
with body:
```json
{
  "author": "Reply author",
  "message": "Message reply text"
}
```
Response should be 201:
```json
{
  "questionId": 1,
  "id": 5,
  "author": "Reply author",
  "message": "Message reply text"
}
```

### Get a thread: `http://localhost:5000/questions/{questionId}`, 
the response should look like:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": [
    {
       "id": 5,
       "author": "Reply author",
       "message": "Message reply text"
    },
    ...
  ]
}
```

### Get a list of questions: `http://localhost:5000/questions`
The response should look like:
```json
[
    {
      "id": 1,
      "author": "Daniel",
      "message": "Message text",     
      "replies": 0
    },
    ...
]
```

## Guidelines
* Fork this repository and push your commits
* Use the spring-boot template given
* Write unit-tests, integration-tests 
  * Write in javadocs what scenarios are in test
  * Higher coverage is better
* Write code documentation
* All classes given are meant to used as reference - once they are not needed, they can be removed.
* This project uses [lombok](https://projectlombok.org/) - use it when possible
* Properly organize your project with `.gitignore` file, `readme` file explaining how to run the project, etc.

## Deliverables
* Send us a link to a repository fulfilling the requirements.
* Your code will be tested using different tests.
* Successful implementation will move to interview.
