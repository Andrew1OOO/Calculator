## Calculator

this project provides two calculator solutions. They can be used to solve mathematical expressions
according to the rules of PEMDAS, and using the operators:
- ! : Integer Factorials
- ^ : Exponents
- \* : Multiplication
- / : Division
- \+ : Addition
- \- : Subtraction


## Recursive

This folder contains the code that recursively solves an equation by looking at expressions
found in pairs/sets of parentheses. It's two primary methods, `solve()` and `simpleSolve()`, achieve this.

- `Equation("expression").simpleSolve()`
    - This method takes an expression with no order (no parentheses) and can solve it following PEMDAS and the
    accepted operators
- `Equation("expression").solve()`
    - This method drives the recursion of the solving. It instantiates an `Equation()` object on every "outer layer"
    of parentheses. This created object then calls the `solve()` method and the process repeats while there are parentheses.
    If `solve()` is called and there are no parentheses, `simpleSolve()` is used. All of the outputs from the instantiated
    `Equation()` objects created from the original `Equation()` object are then combined for the final equation.

## Dijkstra (Shunting Yard Algorithm)

This folder contains the code that solves the equations using the Shunting Yard Algorithm, using arraylists instead of the typical stacks. It's two primary methods are also `solve()` and `simpleSolve()`, although the code inside the two `solve()` methods are completely different.

- `Equation("expression").simpleSolve()`
    - This method takes an expression with no order (no parentheses) and can solve it following PEMDAS and the
    accepted operators
- `Equation("expression").solve()`
    - This method goes through the Shunting Yard Algorithm
        - The Shunting Yard Algorithm is an effcient way of solving equations
        - It uses stacks and queues for peak efficency.
        - There is an Output queue and an Operator Stack, the method uses Arraylists instead of queues and stacks.
    - [Pseudocode](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)