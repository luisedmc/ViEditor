// Luis Eduardo Meduna C. - 42203341 / 10408871

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
  private String filePath;
  private DoublyLinkedList dll;

  FileHandler(String path) {
    filePath = path;
    dll = new DoublyLinkedList();
  }

  void ReadFile() {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        // System.out.println(line);
        dll.Push(line);
      }
      System.out.println("File read successfully!");
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }

  int GetFileSize() {
    return dll.Count();
  }

  DoublyLinkedList GetDLL() {
    return dll;
  }

  String GetFilePath() {
    return filePath;
  }

}
