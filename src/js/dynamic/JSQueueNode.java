package js.dynamic;

public class JSQueueNode {

	Object data;
	JSQueueNode next;
	
	public Object getData() {
		return data;
	}
	
	public JSQueueNode getNext() {
		return next;
	}
	
	public void setObject(Object data) {
		this.data = data;
	}
	
	public void setNext(JSQueueNode next) {
		this.next = next;
	}
	
}
