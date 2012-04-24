package js.quiz;

import js.JSUtil;

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
	
	public static JSQuestion createQuestion(String s) {
		String text = "", answer = "", hint = "";
		
		if (JSUtil.countInstancesOf("<", s) == JSUtil.countInstancesOf(">", s)) {
			int i = s.indexOf("<") + 1;
			int j = s.indexOf(">", i);
			text = s.substring(i, j);
			
			i = s.indexOf("<", i) + 1;
			if (i > 0) {
				j = s.indexOf(">", i);
				answer = s.substring(i, j);
			}
			
			i = s.indexOf("<", i) + 1;
			if (i > 0) {
				j = s.indexOf(">", i);
				hint = s.substring(i, j);
			}
			
			if (answer.length() == 0)
				System.err.println("Error creating question from \"" + s + "\"; no answer provided. \n");
			else {
				if (! answer.contains(",")) {
					JSQuestion q = new JSQuestion(text, answer, hint);
					return q;
				} else {
					Object[] array = JSMultiChoiceQuestion.parseChoices(answer);
					JSMultiChoiceQuestion q = new JSMultiChoiceQuestion(text, (String[]) array[0], (Integer) array[1]);
					return q;
				}
			}
		} else {
			System.err.println("Error creating question from \"" + s + "\"; malformed tags. \n");
		}
		
		return null;
	}
	
}
