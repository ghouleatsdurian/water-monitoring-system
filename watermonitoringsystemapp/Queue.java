public class Queue extends LinkedList {
    public Queue() {
        super();
    }

    // i. add data at the end of the list (enqueue).
    public void enqueue(WaterSample data) {
        insertAtBack(data);
    }

    // ii. Removes data at the beginning of a list (dequeue).
    public WaterSample dequeue() {
        return removeFromFront();
    }

    // iii. Determine size of the list.
    public int size() {
        return sizeList();
    }

    // iv. Determine whether the list is empty.
    public boolean isEmpty() {
        return super.isEmpty();
    }
}