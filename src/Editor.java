// Luis Eduardo Meduna C. - 42203341 / 10408871

import java.util.Scanner;

public class Editor {
  private FileHandler file;
  private Scanner scanner;
  private String cmd;

  Editor(String filePath) {
    file = new FileHandler(filePath);
    scanner = new Scanner(System.in);
    cmd = "";
  }

  // :e nomeArq.ext
  void OpenFile(String filePath) {
    file = new FileHandler(filePath);
    file.ReadFile();
  }

  // :help
  void Help() {
    System.out.println("Comandos disponíveis:");

    System.out.println(
        ":e nomeArq.ext - Abrir o arquivo de nome 'nomeArq.ext', armazenar cada linha em um Node da lista encadeada circular.");
  }

  void InitMenu() {
    PrintMenu();

    while (true) {
      System.out.print("-> ");
      cmd = scanner.nextLine();
      switch (cmd) {
        case ":help":
          Help();
          break;

        default:
          break;
      }
    }
  }

  void PrintMenu() {
    System.out.println("Editor aberto!");
    System.out.println(":help para listar comandos disponíveis");
  }

}
