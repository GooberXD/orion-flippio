public class StudentScore {
    private final String subjectName;
    private final int score;

    public StudentScore(String subjectName, int score) {
        this.subjectName = subjectName;
        this.score = score;
    }

    public int getScore() { return score; }
    public String getSubjectName() { return subjectName; }
}