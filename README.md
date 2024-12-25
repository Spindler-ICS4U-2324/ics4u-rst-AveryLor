# **ICS4U Final Software Project: Enhanced Wordle Game**

This project was developed as part of the final assessment for the **ICS4U** course. It is a modern rendition of the classic **Wordle** game, enhanced with additional features and built using **Java** and **object-oriented programming principles**.

## **Project Overview**

This implementation of Wordle extends the original concept by introducing:
- **Shop System**: Players can use in-game points to purchase items or power-ups, adding more depth and replay value to the game.
- **User Management**: A system to track user data, such as game statistics and scores, providing a personalized experience for each player.

## **Technical Highlights**

### **Programming Approach**
- The project was developed using **Java** and follows **object-oriented programming (OOP)** principles. Key classes include:
  - `WordleGame`: Manages the game logic, including word generation, validation, and scoring.
  - `Shop`: Handles the shop system, allowing users to purchase items using points earned from gameplay.
  - `User`: Tracks individual user data such as game history, progress, and in-game currency.

### **File Input/Output**
- **File Management**: The project makes use of **file I/O** to read and write user data, ensuring that progress and scores persist between sessions. This allows the game to remember user statistics and make interactions more meaningful.
  - **Reading from Files**: Reads user data, such as previous scores or purchases, to maintain continuity.
  - **Writing to Files**: Updates user data after each game, including scores, points, and purchases, by writing back to the file.

### **Algorithms**
- **Word Validation**: A custom algorithm checks user inputs against a predefined list of valid words, ensuring the game operates correctly.
- **Score Calculation**: Points are dynamically calculated based on game performance, rewarding players for correct guesses and faster solutions.
- **Shop Transactions**: Implements logic for processing in-game purchases and deducting points when items are bought.

### **Technologies Used**
- **Java**: The core programming language used to implement the game logic, object management, and file operations.
- **File I/O**: Utilizes Java's file handling capabilities to store and retrieve user data.
  
---

This project provides a combination of game development, user tracking, and file management, demonstrating my understanding of **Java**, **OOP principles**, and **file handling** in a real-world application.

