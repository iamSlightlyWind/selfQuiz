public class QnA {
    public String question, answer;
    public boolean correctFormat = true;

    public QnA(String qna) {
        String[] parts = qna.split("\\|");
        if (parts.length == 2) {
            this.question = parts[0];
            this.answer = parts[1];
        } else {
            correctFormat = false;
        }
    }
}
