// Luis Eduardo Meduna C. - 42203341 / 10408871

import java.util.Scanner;

public class Editor {
  private FileHandler file;
  private Scanner scanner;
  private String cmd;
  private String[] selectedLines;

  Editor(String filePath) {
    file = new FileHandler(filePath);
    scanner = new Scanner(System.in);
    cmd = "";
  }

  // :e nomeArq.ext
  void OpenFile(String filePath) {
    file = new FileHandler(filePath);
    file.ReadFile();
    // file.GetDLL().Print();
  }

  // :v LinIni LinFim
  void SelectLines(int start, int end) {
    if (start <= 0 || end <= 0 || start > file.GetFileSize() || end > file.GetFileSize() || start > end) {
      System.out.println("Linhas inválidas!");
      return;
    }

    int numSelectedLines = end - start + 1; // n° de linhas selecionadas
    selectedLines = new String[numSelectedLines]; // Array de linhas selecionadas
    int lineNumber = 1;
    int index = 0;

    Node currentNode = file.GetDLL().GetHead();
    while (currentNode != null) {
      if (lineNumber >= start && lineNumber <= end) {
        String line = currentNode.getData();
        selectedLines[index++] = line; // Salva a linha no array
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    // System.out.println("Linhas selecionadas: " + start + " a " + end);
    // for (String line : selectedLines) {
    // System.out.println(line);
    // }
  }

  // :y
  void Copy() {
    if (selectedLines == null || selectedLines.length == 0) {
      System.out.println("Nenhuma linha selecionada!");
      return;
    }

    // TODO: Copiar as linhas selecionadas
  }

  // :q!
  void Exit() {
    System.out.println("Fechar editor sem salvar? (y/n)");
    String ans = scanner.nextLine();
    if (ans.equals("y"))
      System.exit(0);
  }

  // :help
  void Help() {
    System.out.println("Comandos disponíveis:\n");

    System.out.println(
        ":e nomeArq.ext - Abrir o arquivo de nome 'nomeArq.ext', armazenar cada linha em um Node da lista encadeada circular.");
    System.out.println(":q! - Sair do editor sem salvar as modificações realizadas.");
    System.out.println(
        ":v LinIni LinFim - Selecionar as linhas de 'LinIni' a 'LinFim' para realizar operações de copiar ou cortar.");
    System.out.println();
  }

  void InitMenu() {
    PrintMenu();

    while (true) {
      System.out.print("-> ");
      cmd = scanner.nextLine();
      String[] parts = cmd.split("\\s+");

      switch (parts[0]) {

        case ":e":
          if (parts.length >= 2) { // Check second argument for file name
            OpenFile(parts[1]);
          } else {
            System.out.println("Usage: :e fileName.txt");
          }
          break;

        case ":v":
          if (parts.length >= 3) { // Check second and third arguments for line numbers
            SelectLines(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
          } else {
            System.out.println("Usage: :v LinIni LinFim");
          }
          break;

        case ":help":
          Help();
          break;

        case ":q!":
          Exit();

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
