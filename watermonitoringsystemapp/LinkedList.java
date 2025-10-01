public class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    public Node getHead() {
        return head;
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // i. insert node at front and at the back of the list.
    public void insertAtFront(WaterSample data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void insertAtBack(WaterSample data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // ii. remove node anywhere in the list.
    public boolean removeNode(int attributeNum, String value) {
        if (head == null) return false;

        if (matchAttribute(head.data, attributeNum, value)) {
            head = head.next;
            if (head == null) {
                 tail= null;
            }
            size--;
            return true;
        }

        Node current = head.next;
        Node prev = head;

        while (current != null) {
            if (prev != null && matchAttribute(current.data, attributeNum, value)) {
                prev.next = current.next;
                if (current == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }


    private boolean matchAttribute(WaterSample sample, int attributeNum, String value) {
        value = value.toLowerCase();
        if (attributeNum == 1) { // Date
            return sample.getDate().toLowerCase().equals(value);
            } else if (attributeNum == 2) { // Location
                return sample.getLocation().toLowerCase().equals(value);
            } else if (attributeNum == 3) { // Temperature
                return String.valueOf(sample.getTemperature()).toLowerCase().equals(value);
            } else if (attributeNum == 4) { // pH
                return String.valueOf(sample.getPH()).toLowerCase().equals(value);
            } else if (attributeNum == 5) { // Hardness Class
                return sample.getHardnessClass().toLowerCase().equals(value);
            } else {
                return false;
            }
    }

    //iii. provide traversal from head until the last node in the list.
    public void traverseList(){
        Node current = getHead();
        while (current != null) {
            System.out.println(current.data);
            current = current.getNext();
        }
    }

    //iv. determine the size of the list.
    public int sizeList(){
        return size;
    }

    //v. status of whether the list is empty or has element(s).
    public boolean isEmpty(){
        return size == 0;
    }

    //vi. a method to display details of all elements in the list.
    public void displayList(){
        traverseList();
    }

    //additional method: to remove node from front of list
    public WaterSample removeFromFront() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return null;
        }

        WaterSample removeItem = head.data;
        head = head.next;
        size--;
        return removeItem;
    }
}