# Wordle Game Demo - JavaFX Implementation

## Introduction

This project is a Wordle game demo developed using Java and JavaFX. The game allows users to guess a hidden word, providing visual feedback on correct letters and their positions.

## Getting Started

### Installation
1. Clone this repository or download the files.
2. Import the project into your preferred IDE.
3. Ensure that JavaFX is properly set up in your project build path or IDE configuration.

### Running the Game
1. Open `App.java` and run the main method.
2. The game window will launch, displaying the game board for Wordle.
3. Enter your guesses using the input field, and the game will provide feedback on correct letters and positions.
4. Keep guessing until you find the hidden word or run out of attempts! You have 2 hints available for each round

### Game Mechanics
- You have 6 attempts to guess the correct 5-letter word.
- Correct letters in the right position will turn green.
- Correct letters in the wrong position will turn yellow.
- Incorrect letters will remain grey.

### File Descriptions
- **App.java**: The main entry point for launching the game. It sets up the JavaFX application and starts the game interface.
- **Board.java**: Contains the logic for managing the game board, validating guesses, and providing feedback to the player.

### Customization
You can customize the game's word list, feedback system, and other game mechanics by modifying the appropriate sections in `Board.java`.
