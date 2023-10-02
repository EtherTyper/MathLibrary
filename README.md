MathLibrary
===================
This is a Kotlin library to assist you with various mathematical topics. This includes:

- Vector Algebra
- Multivariable Calculus
  - Gradient Descent
- Basic Linear Algebra
- Electrostatic Simulations
- Analytic Transformations
  - Taylor Series
  - Fourier Series and Transformations
- Complex Algebra
  - Complex Numbers
  - Complex Vectors
  - Complex Matrices
- Quantum Computing
  - Entangled Qubit States
  - Quantum Gates
  - Quantum Circuits
    - Parallel Gates
    - Sequential Gates
  - Measuring

## Installation and Usage

1. Clone this repository to a preferred location.
2. Build the library:
    - Navigate to the directory and type "nvm clean install" in the terminal
    - This command will compile the library and its dependencies, generate any necessary artifacts, and install the library in your local   Maven repository. After running this command, the library should be available for use in your projects.
3. Adding the dependency to your project:
    - Open your project's 'pom.xml' file
    - Inside the <dependencies> section, add a dependency element such as:
   ```
     <dependency>
        <groupId>mathLibrary</groupId>
        <artifactId>MathLibrary</artifactId>
        <version>1.0-SNAPSHOT</version>
     <dependency>
   ```

4. If you are using an IDE such as IntellijIDEA or Eclipse, sync or update your project dependencies. This will ensure that your project recognizes the newly added library.
5. Finally, you can freely import classes and functions from the library into your Kotlin code and use them as needed!
6. There are some unit test cases in /MathLibrary/src/tests that you can use to test whether the functions are correctly working or not!

## Contributing to MathLibrary

If you are interested in contributing to this project, whether that be implementing additional functions or fixing mistakes, feel free to fork this repository. Once you have made your additions, send me the pull request. You can also create an issue on the project GitHub site. 

## Credits

List and pictures of all of the contributors in the project.

## License
[License](LICENSE) 
