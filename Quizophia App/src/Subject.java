import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final String subjectName;
    private final List<Question> questions;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getSubjectName() {
        return subjectName;
    }
}