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

  // Insere depois da linha especificada
  void InsertAfter(Node node, String newData) {
    if (node == null) {
      System.out.println("Node de referência inválido!");
      return;
    }

    Node newNode = new Node(newData);

    Node nextNode = node.getNext();

    newNode.setNext(nextNode);
    newNode.setPrevious(node);

    node.setNext(newNode);

    if (nextNode != null) {
      nextNode.setPrevious(newNode);
    } else {
      tail = newNode;
    }

    count++;
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

  // Remove um node especifico
  void Remove(Node node) {
    if (node == null) {
      System.out.println("Node inválido!");
      return;
    }

    Node prev = node.getPrevious();
    Node next = node.getNext();

    if (prev != null) {
      prev.setNext(next);
    } else {
      head = next;
    }

    if (next != null) {
      next.setPrevious(prev);
    } else {
      tail = prev;
    }

    count--;
  }

  // Remover todos os nodes entre start e end
  void RemoveFrom(int start, int end) {
    if (start <= 0 || end <= 0 || start > count || end > count || start > end) {
      System.out.println("Linhas inválidas!");
      return;
    }

    Node curr = head;
    int lineNumber = 1;

    while (curr != null) {
      if (lineNumber >= start && lineNumber <= end) {
        Node prev = curr.getPrevious();
        Node next = curr.getNext();

        if (prev != null) {
          prev.setNext(next);
        } else {
          head = next;
        }

        if (next != null) {
          next.setPrevious(prev);
        } else {
          tail = prev;
        }

        count--;
      }

      curr = curr.getNext();
      lineNumber++;
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

    if (head == null || tail == null) {
      System.out.println("Empty list.");
      return;
    }

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
