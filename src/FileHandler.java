// Luis Eduardo Meduna C. - 42203341 / 10408871

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

  void SaveFile() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
      Node currentNode = dll.GetHead();
      while (currentNode != null) {
        bw.write(currentNode.getData());
        bw.newLine();
        currentNode = currentNode.getNext();
      }
      System.out.println("File saved successfully!");
    } catch (IOException e) {
      System.err.println("Error saving the file: " + e.getMessage());
    }
  }

  void ChangeFileName(String newFileName) {
    File file = new File(filePath);
    File newFile = new File(newFileName);
    if (file.renameTo(newFile)) {
      System.out.println("File name changed from " + filePath + " to " + newFileName + " successfully!");
    } else {
      System.err.println("Error changing the file name!");
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
