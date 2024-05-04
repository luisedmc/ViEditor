// Luis Eduardo Meduna C. - 42203341 / 10408871

public class Node {
  private String data;
  private Node next, previous;

  Node(String value) {
    data = value;
    next = previous = null;
  }

  public String getData() {
    return data;
  }

  public void setData(String value) {
    data = value;
  }

  public Node getNext() {
    return next;
  }

  public void setNext(Node node) {
    next = node;
  }

  public Node getPrevious() {
    return previous;
  }

  public void setPrevious(Node node) {
    previous = node;
  }

}
