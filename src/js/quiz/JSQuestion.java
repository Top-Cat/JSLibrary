package js.quiz;

public class JSQuestion {

	private String text;
	private String hint;
	private String answer;
	
	public JSQuestion(String text, String answer) {
		this.text = text;
		this.answer = answer;
	}
	
	public JSQuestion(String text, String answer, String hint) {
		this(text, answer);
		this.hint = hint;
	}
	
	public String getText() {
		return text;
	}
	
	public String getHint() {
		return hint;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
