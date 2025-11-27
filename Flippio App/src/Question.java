public abstract class Question {
    protected String questionText;
//    protected String correctAnswer;
    protected int correctAnswer; // edited, Ligaray, 11282025

    public Question(String questionText, int correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract void display();

    public boolean checkAnswer(int answerIndex) {
//        return this.correctAnswer.equalsIgnoreCase(answer);
        return correctAnswer == answerIndex; // edited, Ligaray, 11282025
    }

    public String getQuestionText() {
        return questionText;
    }

//    public String getCorrectAnswer() {
//        return correctAnswer;
//    }
    public int getCorrectAnswer() {
        return correctAnswer; // edited, Ligaray, 11282025
    }

}