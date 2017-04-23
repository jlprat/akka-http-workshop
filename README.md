# akka-http-workshop

This is the repository for the Akka HTTP workshop.

The slides are under the `docs` folder, the main folder contains the sbt project with the examples and exercises shown in the slides.

## Exercises
There are a total of 4 exercises focusing on different aspects of Akka HTTP:
- Composing Routes
- Processing parameters
- Handling Rejections and Exceptions
- How to deal with blocking code

## Code Together
Additionally to this, there is a hands-free exercise under the `bookstore` package.
 The main idea of this is to practice what has been learned during the workshop.
 The Book Shop should handle the book catalog as well as the reviews for each book.
 The requirements for this exercise are:
 - Consult the Catalog:
    - All the titles in the catalog
    - A specific book in the catalog
 - Manage the Catalog:
    - Only admins can perform actions
    - Add a book
    - Remove a book
 - Reviews per book:
    - List reviews for a given book
    - Add a review for a given book
        - Only authenticated users can perform this action
        
The application is layered in 3 packages:
 - model: That contains all model classes used in this project
 - routes: They hold the routes, but hold no business logic
 - actor: They hold the business logic of the application
 
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
