# MathLibrary

This is my (Eli Bradley's) computer science project, a Kotlin DSL focusing on various topics I've learned about
through school and just on my own. This includes:

- [X] Vector Algebra
- [X] Multivariable Calculus
  - [X] Gradient Descent
- [X] Basic Linear Algebra
- [X] Electrostatic Simulations
- [X] Analytic Transformations
  - [X] Taylor Series
  - [ ] Fourier Series and Transformations
- [X] Complex Algebra
  - [X] Complex Numbers
  - [X] Complex Vectors
  - [X] Complex Matrices
- [X] Quantum Computing
  - [X] Entangled Qubit States
  - [X] Quantum Gates
  - [X] Quantum Circuits
    - [X] Parallel Gates
    - [X] Sequential Gates
  - [X] Measuring

# Technologies Needed

## Java (I think all versions will work)

  - Visit https://www.java.com/en/download/ and download the most current version of java. I use a version that is 20+.

## Kotlin version 1.2.41

  - Visit https://kotlinlang.org/docs/command-line.html and determine how to download this specific version of Kotlin
  - Make sure that you have this version, as I tried to build the project without it and it didn't work.
  - If you cannot find the 1.2.41 build, you will have to go through the depreciated functions like toByte() and toShort() and swap them for equivalent logic. 
  - Make sure you configure the system's path variable with the directory of the Kotlin compiler

## MVN 3.9.6 or better

  - Visit https://maven.apache.org/download.cgi and download and install the suitable Maven installation for your machine.
  - Make sure you configure teh system's path variable with teh directory of Maven.

# Ensuring dependencies are up to date post cloning

## Review pom.xml to see what dependencies this project relies on

## Use Maven to test the project and it's dependencies

  - Run the following commands in the root directory in order:
    - `mvn clean`: cleans the project and removes compiled files
    - `mvn compile`: compiles the project
    - `mvn test`: runs tests for project
    - `mvn package`: packages compiled code into distributable format such as a jar file
    - `mvn dependency:tree`: displays the project's current dependency tree, showing all dependencies and their versions

## Check for dependency updates

  - run `mvn versions::display-dependency-updates`: searches for any updates to existing dependencies of your project and displays them.
  - run `mvn versions::display-plugin-updates`: This command checks for updates to Maven plugins used in the project.

## Remember:

  - This project is really old and might require some love to get it running. I also am not entirely sure that these above steps will work 100% of the time. However, if you become stuck, I would turn to sources like stack overflow and other online forums to look for people who have had similar problems. 





