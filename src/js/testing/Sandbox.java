package js.testing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import js.JSFrame;
import js.JSLog;
import js.quiz.JSMultiChoiceQuestion;
import js.quiz.JSQuestion;

public class Sandbox extends JSFrame implements ActionListener, MouseListener,
		ItemListener, ComponentListener {

	private static final long serialVersionUID = 1275283783541551650L;
	JTextField xField, yField;
	
	JSLog log;
	
	boolean b = true;
	JPanel anotherPanel;
	
	Sandbox() {
		
		String s = "<What is the capital of France?> <Paris*, London, Berlin, Tokyo, New York>";
		
		JSMultiChoiceQuestion mq = (JSMultiChoiceQuestion) JSQuestion.createQuestion(s);
		
		System.out.println("Question: " + mq.getText());
		System.out.print("Choices: ");
		for (int i = 0; i < mq.getNumberOfChoices(); i ++) {
			System.out.print(mq.getChoice(i) + ", ");
		}
		System.out.println("\nCorrect answer: " + mq.getCorrectAnswerIndex() + " (" + mq.getAnswer() + ")");
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		log.append("You clicked button number " + source.getText());
	}

	public void itemStateChanged(ItemEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}

	public static void main(String[] args) {
		Sandbox sandbox = new Sandbox();
	}

}
