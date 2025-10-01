public class Node {
    WaterSample data;
    Node next;

    Node(WaterSample d){ this(d, null); }

    Node(WaterSample d, Node nextNode){
        data = d;
        next = nextNode;
    }

    public WaterSample getData(){return data;}
    public Node getNext(){return next;}
}