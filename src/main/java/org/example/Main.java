package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class User {
    @JsonProperty("username")
    String username;
    @JsonProperty("password")
    String password;
    @JsonProperty("role")
    String role;

    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("role") String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class Question {
    @JsonProperty("question")
    String question;
    @JsonProperty("option1")
    String option1;
    @JsonProperty("option2")
    String option2;
    @JsonProperty("option3")
    String option3;
    @JsonProperty("option4")
    String option4;
    @JsonProperty("answerKey")
    int answerKey;

    public Question(@JsonProperty("question") String question, @JsonProperty("option1") String option1, @JsonProperty("option2") String option2, @JsonProperty("option3") String option3, @JsonProperty("option4") String option4, @JsonProperty("answerKey") int answerKey) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerKey = answerKey;
    }
}

public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Question> questions = new ArrayList<>();
    private static ObjectMapper mapper = new ObjectMapper();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        loadUsers();
        loadQuestions();

        while (true) {
            System.out.println("Enter your username");
            String username = scanner.nextLine();
            System.out.println("Enter password");
            String password = scanner.nextLine();

            User user = authenticate(username, password);
            if (user != null) {
                if (user.role.equals("admin")) {
                    adminMenu();
                } else {
                    studentQuiz();
                }
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }
    }

    private static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void loadUsers() {
        try {
            File file = new File("src/main/resources/users.json");
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, User.class);
            users = mapper.readValue(file, listType);
        } catch (IOException e) {
            users.add(new User("admin", "1234", "admin"));
            users.add(new User("salman", "1234", "student"));
            saveUsers();
        }
    }

    private static void loadQuestions() {
        try {
            File file = new File("src/main/resources/quiz.json");
            if (file.exists() && file.length() > 0) {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Question.class);
                questions = mapper.readValue(file, listType);
            } else {
                // Only initialize with empty list if file doesn't exist or is empty
                questions = new ArrayList<>();
                saveQuestions();
            }
        } catch (IOException e) {
            questions = new ArrayList<>();
            saveQuestions();
        }
    }

    private static void saveUsers() {
        try {
            mapper.writeValue(new File("src/main/resources/users.json"), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveQuestions() {
        try {
            mapper.writeValue(new File("src/main/resources/quiz.json"), questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void adminMenu() {
        System.out.println("Welcome admin! Please create new questions in the question bank.");
        while (true) {
            System.out.println("Input your question");
            String question = scanner.nextLine();
            System.out.println("Input option 1:");
            String option1 = scanner.nextLine();
            System.out.println("Input option 2:");
            String option2 = scanner.nextLine();
            System.out.println("Input option 3:");
            String option3 = scanner.nextLine();
            System.out.println("Input option 4:");
            String option4 = scanner.nextLine();
            System.out.println("What is the answer key? (1-4)");
            int answerKey = Integer.parseInt(scanner.nextLine().trim());

            questions.add(new Question(question, option1, option2, option3, option4, answerKey));
            saveQuestions();
            System.out.println("Saved successfully!");
            System.out.println("Do you want to add more questions? (press 's' to start, 'q' to quit)");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("q")) break;
        }
    }

    private static void studentQuiz() {
        System.out.println("Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("s")) {
            if (questions.isEmpty()) {
                System.out.println("No questions available. Please ask the admin to add questions.");
                return;
            }
            int score = 0;
            List<Question> quizQuestions = new ArrayList<>();
            int questionsToSelect = Math.min(10, questions.size());
            for (int i = 0; i < questionsToSelect; i++) {
                quizQuestions.add(questions.get(random.nextInt(questions.size())));
            }

            for (int i = 0; i < quizQuestions.size(); i++) {
                Question q = quizQuestions.get(i);
                System.out.println("[Question " + (i + 1) + "] " + q.question);
                System.out.println("1. " + q.option1);
                System.out.println("2. " + q.option2);
                System.out.println("3. " + q.option3);
                System.out.println("4. " + q.option4);
                System.out.println("Your answer (1-4):");
                String input = scanner.nextLine().trim();
                try {
                    int answer = Integer.parseInt(input);
                    if (answer >= 1 && answer <= 4 && answer == q.answerKey) score++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Skipping this question.");
                }
            }

            String message = "";
            if (score >= 8) message = "Excellent! You have got " + score + " out of " + questionsToSelect;
            else if (score >= 5) message = "Good. You have got " + score + " out of " + questionsToSelect;
            else if (score >= 3) message = "Very poor! You have got " + score + " out of " + questionsToSelect;
            else message = "Very sorry you are failed. You have got " + score + " out of " + questionsToSelect;
            System.out.println(message);
            System.out.println("Would you like to start again? Press 's' for start or 'q' for quit");
            choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("s")) studentQuiz();
        }
    }
}