# akka-http-workshop

This is the repository for the Akka HTTP workshop.

The slides are under the `docs` folder, the main folder contains the sbt project with the examples and exercises shown in the slides.

## Exercises
There are a total of 7 exercises focusing on different aspects of Akka HTTP:
- Composing Routes
- Processing parameters
- Handling Rejections and Exceptions
- HTTP Client
- How to deal with blocking code
- Interacting with Akka Typed
- Implications of Streaming

To run them switch to the `exercises` branch!
 
## How to run this code
To run this code just head over the project main folder and run:
```
>sbt
>~test
```
With this previous commands you can code the solutions and you'll see the outcome of the tests.

If you would like to run some of the servers, just run:
```
>run
```
And pick the right main application to run.
