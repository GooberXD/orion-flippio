public class QuestionFactory {
    public Question createQuestion(String type, String dataLine) {
        if (type.equalsIgnoreCase("MC")) {
            String[] parts = dataLine.split("\\|");
            String text = parts[1];
            String[] choices = {parts[2], parts[3], parts[4], parts[5]};
            String answer = parts[6];
            return new MultipleChoiceQuestion(text, answer, choices);
        }
        return null;
    }
}