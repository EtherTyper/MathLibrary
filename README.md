# MathLibrary

## Project Description

MathLibrary is a Kotlin DSL project created by Eli Bradley that encompasses applications of the following mathematical computations:

- [X] Vector Algebra
- [X] Multivariable Calculus
  - [X] Gradient Descent
- [X] Basic Linear Algebra
- [X] Electrostatic Simulations
- [X] Analytic Transformations
  - [X] Taylor Series
  - [X] Fourier Series and Transformations
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
  
## Table of Contents

1. [How to Install and Run the Project](#how-to-install-and-run-the-project)
2. [How to Use the Project](#how-to-use-the-project)
3. [Project Structure](#project-structure)
4. [Testing](#testing)
5. [How to Contribute to the Project](#how-to-contribute-to-the-project)
6. [Credits](#credits)
7. [License](#license)

## How to Install and Run the Project

1. **Clone the repository:**
```console
git clone https://github.com/EtherTyper/MathLibrary.git
```

2. **Build the new library:**
- Ensure you are in the MathLibrary directory using the command
```console
cd MathLibrary
```
- Run the following command in the terminal to compile and install the new library to your local Maven repository:
```console
  mvn install
```
3. **Add dependencies:**
- Add the following code to the **<dependencies>** section in your pom.xml file:
```XML
<dependency>
  <groupId>mathLibrary</groupId>
  <artifactId>MathLibrary</artifactId>
  <version>1.0</version>
</dependency>
```
4. **Update project dependencies:**
- If you are coding your project using an IDE such as IntelliJ IDEA or NetBeans, update the dependencies accordingly to ensure the new library is available for use.

5. **Import and use MathLibrary:**
- Once the library is recognized, you can import classes and functions available in MathLibrary for mathematical computations in your Kotlin project.

## How to Use the Project

Detailed instructions and examples are provided in the src folder, categorized into subdirectories based on each mathematical topic.

## Project Structure

The project is structured as follows:

- **applications:** Contains specific applications of the MathLibrary, such as electrostatics, mechanics, and quantum computations.
- **core:** Core mathematical functionalities, including complex numbers, linear algebra, transformations, and vectors.
- **tests:** Unit tests for various components, covering complex numbers, matrices, quantum computations, vectors, and more.

## Testing

The tests directory located under **/MathLibrary/src/tests** includes comprehensive unit tests for different mathematical components. Execute the tests by running the corresponding main functions in the test files. Feel free to write additional test cases.
- **AllTests.kt:** Executes all unit tests in one go.
- **ComplexTests.kt, MatrixTests.kt, QuantumTests.kt, VectorTests.kt:** Individual test files for specific components.

## How to Contribute to the Project

If would like to contribute to the MathLibrary functionality by implementing additional features or fixing errors, follow the steps below:

1. Fork the MathLibrary repository.
2. Implement additional changes.
3. Initiate a pull request.

Alternatively, you may [create an issue](https://github.com/EtherTyper/MathLibrary/issues).

## Credits

Project by Eli Joseph Bradley

## License

This project is licensed under the terms of the [License](LICENSE).
