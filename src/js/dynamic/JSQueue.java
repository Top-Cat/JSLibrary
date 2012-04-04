package js.dynamic;



/**
 * A basic implementation of the 'Queue' dynamic structure, which adheres to the First In, First Out
 * principle. Data can only be added to the end of the queue, and can only be removed from the start
 * of the queue, in the order it was added.
 * 
 * @author Josh
 * @version 1.0
 *
 */
public class JSQueue<E> {

	private JSQueueNode start;
	private JSQueueNode end;
	private int nodeCount;
	
	/**
	 * Initialises a new queue containing no data items.
	 */
	public JSQueue() {
		start = null;
		end = null;
		nodeCount = 0;
	}
	
	/**
	 * Determines whether the queue is currently empty.
	 * 
	 * @return <code>true</code> if the queue is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return (size() == 0);
	}
	
	/**
	 * Determines how many data items are currently in the queue.
	 * 
	 * @return The number of data items in the queue.
	 */
	public int size() {
		return nodeCount;
	}
	
	/**
	 * Pulls the next data item from the front of the queue.
	 * 
	 * @return The data item from the front of the queue as an {@link Object}.
	 */
	@SuppressWarnings("unchecked")
	public E pull() throws EmptyQueueException {
		if (size() > 0) {
			Object object = start.getData();
			E data = (E) object;
			start = start.getNext();
			if (start == null)
				end = null;
			nodeCount --;
			return data;
		} else {
			throw new EmptyQueueException("Tried to pull from an empty queue.");
		}
	}
	
	/**
	 * Adds a new data item to the end of the queue.
	 * 
	 * @param data - The data to add to the queue.
	 */
	public void push(E data) {
		JSQueueNode pointer = new JSQueueNode();
		pointer.setObject(data);
		pointer.setNext(null);
		if (size() == 0) {
			start = pointer;
			end = pointer;
		} else {
			end.setNext(pointer);
			end = pointer;
		}
		nodeCount ++;
		
		
	}
	

	
	/**
	 * Empties the queue of all data items.
	 */
	public void clear() {
		start = null;
		end = null;
		nodeCount = 0;
		System.gc();
	}
	
}
