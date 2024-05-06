// Luis Eduardo Meduna C. - 42203341 / 10408871

public class DoublyLinkedList {
  private Node head, tail;
  private int count;

  DoublyLinkedList() {
    head = tail = null;
    count = 0;
  }

  // Insere no inicio
  void Insert(String data) {
    Node newNode = new Node(data);

    if (isEmpty()) {
      head = tail = newNode;
      count++;
    } else {
      newNode.setNext(head);
      head.setPrevious(newNode);
      head = newNode;
      count++;
    }
  }

  // Insere no final
  void Push(String data) {
    Node newNode = new Node(data);

    if (tail == null) {
      head = newNode;
      tail = newNode;
      count++;
    } else {
      tail.setNext(newNode);
      newNode.setPrevious(tail);
      tail = newNode;
      count++;
    }
  }

  Node GetHead() {
    return head;
  }

  private boolean isEmpty() {
    return head == null && tail == null;
  }

  int Count() {
    return count;
  }

  // Print simples
  void Print() {
    Node curr = head;

    while (curr != null) {
      System.out.println(curr.getData());
      curr = curr.getNext();
    }
  }

  // Print detalhado
  void PrintDLL() {
    System.out.println("Count: " + Count());
    System.out.println("Head: " + head.getData());
    System.out.println("Tail: " + tail.getData());

    Node curr = head;
    while (curr != null) {
      System.out.print("{" + (curr.getPrevious() != null ? curr.getPrevious().getData() : "null") + " <- ");
      System.out.print(curr.getData());
      System.out.println(" -> " + (curr.getNext() != null ? curr.getNext().getData() : "null") + "}");
      curr = curr.getNext();
    }
  }

}
