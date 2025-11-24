public class MultipleChoiceQuestion extends Question {
    private final String[] choices;

    public MultipleChoiceQuestion(String text, String answer, String[] choices) {
        super(text, answer);
        this.choices = choices;
    }

    @Override
    public void display() {
        System.out.println("Q: " + questionText);
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }
    }

    public String[] getChoices() {
        return choices;
    }
}