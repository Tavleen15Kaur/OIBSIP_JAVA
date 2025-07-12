import java.util.*;

class Onlineexaminationsystem {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static List<Question> questions = new ArrayList<>();
    private static Timer timer = new Timer();
    private static int score = 0; // Moved score to an instance variable

    static {
        // Adding some default users
        users.put("person1", new User("person1", "password1"));
        users.put("person2", new User("person2", "password2"));

        // Adding some default questions
        questions.add(new Question("Who won Kargil War?", "India", "Pakistan", "China", "Bangladesh", "A"));
        questions.add(new Question("What is the national game of India?", "Cricket", "Hockey", "football", "kabaddi", "B"));
        questions.add(new Question("What is the name of the planet with rings?", "Earth", "Mars", "Jupiter", "Saturn", "D"));
        questions.add(new Question("What is the group of stars arranged in a definite pattern called?", "Solar System", "Constellation", "Milky Way", "Galaxy", "B"));
        questions.add(new Question("How many ICC tropies were won by India?", "8", "6", "7", "5", "C"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Examination System");

        while (true) {
            System.out.print("\nChoose from the following: ");
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("open option:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("\nExited successfully.");
                
                   
                    return;
                default:
                    System.out.println("\nInvalid choice. \nPlease try again.\n");
            }
        }
    }

    @Override
    public String toString() {
        return "OnlineExaminationSystem []";
    }

    private static void login(Scanner scanner) {
        System.out.print("\nEnter login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(loginId, password)) {
            System.out.println("Login successful!");
            currentUser = users.get(loginId);

            while (true) {
                System.out.print("\nChoose from the following: ");
                System.out.println("\n1. Update Profile and Password");
                System.out.println("2. Start Exam");
                System.out.println("3. Logout");
                System.out.print("open option ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        updateProfile(scanner);
                        break;
                    case 2:
                        startExam(scanner);
                        break;
                    case 3:
                        System.out.println("Logging out.....\n logged out.");
                        currentUser = null;
                        return;
                    default:
                        System.out.println("Invalid choice.\n Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login ID or password.\n");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("\nEnter new login ID: ");
        String newLoginId = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        if (users.containsKey(newLoginId)) {
            System.out.println("Login ID already exists. Please choose a different login ID.");
        } else {
            users.put(newLoginId, new User(newLoginId, newPassword));
            System.out.println("Registration successful! You can now log in with your new credentials.\n");
        }
    }

    private static boolean authenticate(String loginId, String password) {
        return users.containsKey(loginId) && users.get(loginId).getPassword().equals(password);
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("\nEnter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Profile updated successfully!\n");
    }

    private static void startExam(Scanner scanner) {
        System.out.println("\nStarting exam...");
        score = 0; // Reset score before starting the exam
        int questionIndex = 0;

        // Shuffle the questions list to ensure different order each time
        Collections.shuffle(questions);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Auto-submitting your answers...");
                System.out.println("Your score: " + score + "/" + questions.size());
                System.exit(0);
            }
        }, 60000); // 1 minute timer

        for (Question question : questions) {
            System.out.println("Q" + (++questionIndex) + ": " + question.getQuestion());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());
            System.out.print("Your answer:");
            String answer = scanner.nextLine().toUpperCase();

            if (answer.equals(question.getCorrectAnswer())) {
                score++;
            }
        }

        timer.cancel();
        System.out.println("Exam finished! \nYour score: " + score + "/" + questions.size());
    }
}

class User {
    private String password;

    public User(String loginId, String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Question {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
