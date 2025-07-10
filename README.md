# Quiz Management System

## Project Description
This is a simple role-based quiz system that allows:
- Admin users to add multiple-choice questions (MCQs) to a question bank.
- Student users to take a quiz based on the question bank, with 20 pre-loaded Software Quality Assurance (SQA)-related questions and the ability to add more.

## Tech Stack
- Language: Java
- Data Storage: JSON files (`users.json` for credentials, `quiz.json` for questions)
- Build Tool: Gradle
- Dependencies: Jackson (for JSON handling), JUnit (for testing)

## File Structure
- `src/main/java/org.example/Main.java`: Main application logic.
- `src/main/resources/users.json`: Stores user credentials and roles.
- `src/main/resources/quiz.json`: Stores the MCQ question bank with 21 SQA-related questions.
- `.gitignore`: Excludes `.idea/`, `.gradle/`, `gradle/`, and `build/` from version control.
- `README.md`: This file with project details.
- `build.gradle`: Configures project dependencies and build settings.
- `settings.gradle`: Sets the project name.

## Setup
1. Open the project in IntelliJ IDEA.
2. Sync the project with Gradle (`File > Sync Project with Gradle Files`) to download dependencies.
3. Build the project (`Build > Rebuild Project`).
4. Run the application by right-clicking `Main.java` and selecting `Run 'Main.main()'`.

## Usage
- **Admin Login**: Use username "admin" and password "1234" to add new questions to the question bank.
- **Student Login**: Use username "salman" and password "1234" to take a quiz with up to 10 random questions.

## Demo
- [Video Demo][https://drive.google.com/file/d/12bJzjxnryaH82pnS1U8BAn2yiZTL9zV6/view?usp=sharing]: Shows admin login, adding a sample question, student login, and completing a quiz.
