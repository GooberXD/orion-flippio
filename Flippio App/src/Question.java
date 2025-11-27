public abstract class Question {
    protected String questionText;
    protected String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract void display();

    public boolean checkAnswer(String answer) {
        return this.correctAnswer.equalsIgnoreCase(answer);
    }

    //test gitpush from intellij, fr fr
    //this messahge

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}