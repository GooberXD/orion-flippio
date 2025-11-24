import java.util.List;
import java.util.Scanner;

public class Quiz {
    private Subject subject;
    private List<Question> questions;

    public Quiz(Subject subject) {
        this.subject = subject;
        this.questions = subject.getQuestions();
    }

    public void startQuiz(Student student) throws EmptyAnswerException {
        Scanner sc = new Scanner(System.in);
        int score = 0;

        System.out.println("Starting Quiz for: " + subject.getSubjectName());

        for (Question q : questions) {
            q.display(); // Polymorphic call
            System.out.print("Your Answer: ");
            String ans = sc.nextLine();

            if (ans.trim().isEmpty()) {
                throw new EmptyAnswerException("You cannot submit an empty answer!");
            }

            if (q.checkAnswer(ans)) {
                score++;
            }
        }

        System.out.println("Quiz Finished! Score: " + score + "/" + questions.size());

        student.setSubjectScore(subject.getSubjectName(), score);
    }
}