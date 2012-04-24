package js.quiz;

import java.util.Random;

import js.JSUtil;

public class JSMultiChoiceQuestion extends JSQuestion {

	String[] choices;
	int correctAnswer;
	
	public JSMultiChoiceQuestion(String text, String[] choices, int correctAnswerIndex) {
		super(text, choices[correctAnswerIndex]);
		this.choices = choices;
		this.correctAnswer = correctAnswerIndex;
	}
	
	public static Object[] parseChoices(String s) {
		int count = JSUtil.countInstancesOf(",", s) + 1;
		int i = 0, j = s.indexOf(",");
		String[] answers = new String[count];
		int correctIndex = -1;
		
		answers[0] = s.substring(i, j).trim();
		if (answers[0].contains("*"))
			correctIndex = 0;
		answers[0] = answers[0].replace("*", "");
		
		for (int x = 1; x < count - 1; x ++) {
			i = j + 1;
			j = s.indexOf(",", i);
			answers[x] = s.substring(i, j).trim();
			if (answers[x].contains("*"))
				correctIndex = x;
			answers[x] = answers[x].replace("*", "");
		}
		
		i = j + 1;
		j = s.length();
		answers[count - 1] = s.substring(i, j).trim();
		if (answers[count - 1].contains("*"))
			correctIndex = count - 1;
		answers[count - 1] = answers[count - 1].replace("*", "");
		
		if (correctIndex >= 0) {
			Object[] array = {answers, correctIndex};
			return array;
		} else {
			System.err.println("Error parsing choices from \"" + s + "\"; no correct answer marked.");
			return null;
		}
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
	
	public String getAnswer() {
		return choices[correctAnswer];
	}
	
	public int getNumberOfChoices() {
		return choices.length;
	}
	
	public String[] getHintByHiding(int numberToLeave) {
		String[] remaining = new String[choices.length];
		remaining[correctAnswer] = getAnswer();
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
