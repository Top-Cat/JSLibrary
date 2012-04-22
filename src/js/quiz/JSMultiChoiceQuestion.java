package js.quiz;

import java.util.Random;

public class JSMultiChoiceQuestion extends JSQuestion {

	String[] choices;
	int correctAnswer;
	
	public JSMultiChoiceQuestion(String text, String[] choices, int correctAnswerIndex) {
		super(text, choices[correctAnswerIndex]);
		this.choices = choices;
		this.correctAnswer = correctAnswerIndex;
	}

	public String[] getChoices() {
		return choices;
	}
	
	public String getChoice(int index) {
		return choices[index];
	}
	
	public int getCorrectAnswerIndex() {
		return correctAnswer;
	}
	
	public String getCorrectAnswer() {
		return choices[correctAnswer];
	}
	
	public int getNumberOfChoices() {
		return choices.length;
	}
	
	public String[] getHintByHiding(int numberToLeave) {
		String[] remaining = new String[choices.length];
		remaining[correctAnswer] = getCorrectAnswer();
		Random r = new Random();
		int added = 1;
		while (added < numberToLeave) {
			int next = r.nextInt(choices.length);
			if (remaining[next] == null) {
				remaining[next] = choices[next];
				added ++;
			}
		}
		return remaining;
	}
	
	public String[] getHintByRemoving(int numberToLeave) {
		String[] hidden = getHintByHiding(numberToLeave);
		String[] hint = new String[numberToLeave];
		int nextEmpty = 0;
		for (int i = 0; i < hidden.length; i ++) {
			if (hidden[i] != null) {
				hint[nextEmpty] = hidden[i];
				nextEmpty ++;
			}
		}
		return hint;
	}
 
	public void setChoices(String[] answers) {
		this.choices = answers;
	}
	
	public void setChoice(String answer, int index) {
		choices[index] = answer;
	}
	
	public void setCorrectAnswerIndex(int index) {
		correctAnswer = index;
	}
	
}
