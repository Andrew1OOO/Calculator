## Calculator

this project provides two calculator solutions. They can be used to solve mathematical expressions
according to the rules of PEMDAS, and using the operators:
- ! : Integer Factorials
- ^ : Exponents
- * : Multiplication
- / : Division
- + : Addition
- - : Subtraction


## Recursive

This folder contains the code that recursively solves an equation by looking at expressions
found in pairs/sets of parentheses. It's two primary methods, `solve()` and `simpleSolve()`, achieve this.

- Equation().simpleSolve()
    - This method takes an expression with no order (no parentheses) and can solve it folling PEMDAS and the
    accepted operators
- Equation().solve()
    - This method drives the recursion of the solving. It instantiates an `Equation()` object on every "outer layer"
    of parentheses. This created object then calls the `solve()` method and the process repeats while there are parentheses.
    If `solve()` is called and there are no parentheses, `simpleSolve()` is used. All of the outputs from the instantiated
    `Equation()` objects created from the original `Equation()` object are then combined for the final equation.

## Dijkstra (Shunting Yard Algorithm)