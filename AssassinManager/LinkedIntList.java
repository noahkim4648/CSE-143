// LinkedIntList class can be used to store a list of integers.
public class LinkedIntList {
    public static LinkedIntList problem9(ListNode list, ListNode temp) {
        
        // list -> [1] -> [2] /
        // temp -> [3] -> [4] / 
        
        list.next.next = temp.next;  // 2 -> 4
        temp.next = list.next;       // 3 -> 2
        list.next = temp; 

        return new LinkedIntList(list);
    }

    public static class ListNode {
        public int data;          // data stored in this node
        public ListNode next;     // link to next node in the list

        // post: constructs a node with data 0 and null link
        public ListNode() {
            this(0, null);
        }

        // post: constructs a node with given data and null link
        public ListNode(int data) {
            this(data, null);
        }

        // post: constructs a node with given data and given link
        public ListNode(int data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }

    // LinkedIntList internals that can be ignored

    private ListNode front;          // node holding first value in list (null if empty)

    // Constructs a list containing the given front node.
    private LinkedIntList(ListNode front) {
        this.front = front;
    }

    // Constructs a list containing the given elements.
    public LinkedIntList(int... elements) {
        if (elements.length > 0) {
            front = new ListNode(elements[0]);
            ListNode current = front;
            for (int i = 1; i < elements.length; i++) {
                current.next = new ListNode(elements[i]);
                current = current.next;
            }
        }
    }

    // post: Returns true if o is a LinkedIntList with the same values.
    public boolean equals(Object o) {
        if (o instanceof LinkedIntList) {
            LinkedIntList other = (LinkedIntList) o;
            return toString().equals(other.toString());
        } else {
            return false;
        }
    }

    // post: Returns a text representation of the list.
    public String toString() {
        String result = "front";
        ListNode current = front;
        while (current != null) {
            result += " -> [" + current.data + "]";
            current = current.next;
        }
        result += " /";
        return result;
    }
}