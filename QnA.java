public class QnA {
    public String question, answer;
    public int timeAsked = 0;
    public boolean correctFormat = true;
    public boolean asked = false;
    public boolean correct = false;

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
