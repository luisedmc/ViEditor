// Luis Eduardo Meduna C. - 42203341 / 10408871

import java.util.Scanner;

public class Editor {
  private FileHandler file;
  private Scanner scanner;
  private String cmd;
  private String[] selectedLines;
  private boolean isModified;
  private boolean isOpened;
  private boolean isCopied;
  private DoublyLinkedList clipboard;

  Editor(String filePath) {
    file = new FileHandler(filePath);
    scanner = new Scanner(System.in);
    cmd = "";
    isModified = false;
    isOpened = false;
    isCopied = false;
  }

  private boolean IsOpened() {
    return isOpened;
  }

  // :e nomeArq.ext
  void OpenFile(String filePath) {
    file = new FileHandler(filePath);
    file.ReadFile();
    isOpened = true;
    // file.GetDLL().Print();
  }

  // :w
  void SaveW() {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    Save();
  }

  // :wq
  void SaveAndExit() {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    Save();
    System.exit(0);
  }

  // :w nomeArq.ext
  void SaveAs(String filePath) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    file.ChangeFileName(filePath);
    Save();
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
        selectedLines[index++] = line; // Linhas selecionadas salvas no array
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    System.out.println("Linhas selecionadas: " + start + " a " + end);
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

    clipboard = new DoublyLinkedList();
    for (String line : selectedLines) {
      clipboard.Insert(line);
    }

    isCopied = true;
    System.out.println("Linhas copiadas.");
  }

  // :p LinIniPaste
  void Paste(int line) {
    if (!isCopied) {
      System.out.println("Nenhuma linha copiada!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;
    while (currentNode != null) {
      if (lineNumber == line) {
        Node clipboardNode = clipboard.GetHead();
        while (clipboardNode != null) {
          file.GetDLL().InsertAfter(currentNode, clipboardNode.getData());
          clipboardNode = clipboardNode.getNext();
        }
        break;
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    isModified = true;
    System.out.println("Linhas coladas.");
  }

  // :c
  void Cut() {
    if (selectedLines == null || selectedLines.length == 0) {
      System.out.println("Nenhuma linha selecionada!");
      return;
    }

    int start = Integer.MAX_VALUE;
    int end = Integer.MIN_VALUE;

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;
    while (currentNode != null) {
      String line = currentNode.getData();
      for (int i = 0; i < selectedLines.length; i++) {
        if (line.equals(selectedLines[i])) {
          start = Math.min(start, lineNumber); // Update start index
          end = Math.max(end, lineNumber); // Update end index
          break;
        }
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    file.GetDLL().RemoveFrom(start, end);
    selectedLines = null;
    isModified = true;
    System.out.println("Linhas cortadas.");

    // file.GetDLL().Print();
  }

  // :s
  void Display() {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (file.GetFileSize() == 0) {
      System.out.println("Arquivo vazio!");
      return;
    } else if (file.GetFileSize() <= 10) {
      file.GetDLL().Print();
      return;
    }

    System.out.println("Conteúdo do arquivo:");
    System.out.println("Pressione Enter para printar as próximas 10 linhas...\n");
    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;
    while (currentNode != null) {
      String line = currentNode.getData();
      System.out.println(line);
      if (lineNumber % 10 == 0) {
        scanner.nextLine();
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }
  }

  // :s Lin
  void DisplayLine(int line) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (line <= 0 || line > file.GetFileSize()) {
      System.out.println("Linha inválida!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;

    System.out.println("Conteúdo da linha " + line + ":");
    while (currentNode != null) {
      if (lineNumber == line) {
        System.out.println(currentNode.getData());
        break;
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }
  }

  // :s LinIni LinFim
  void DisplayLines(int start, int end) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (start <= 0 || end <= 0 || start > file.GetFileSize() || end > file.GetFileSize() || start > end) {
      System.out.println("Linhas inválidas!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;

    System.out.println("Conteúdo das linhas " + start + " a " + end + ":");
    while (currentNode != null) {
      if (lineNumber >= start && lineNumber <= end) {
        System.out.println(currentNode.getData());
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }
  }

  // :x Lin
  void DeleteLine(int line) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (line <= 0 || line > file.GetFileSize()) {
      System.out.println("Linha inválida!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;

    while (currentNode != null) {
      if (lineNumber == line) {
        file.GetDLL().Remove(currentNode);
        break;
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    isModified = true;
    System.out.println("Linha " + line + " deletada.");
  }

  // :xG Lin
  void DeleteFromLine(int line) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (line <= 0 || line > file.GetFileSize()) {
      System.out.println("Linha inválida!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;

    while (currentNode != null) {
      if (lineNumber >= line) {
        file.GetDLL().Remove(currentNode);
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    isModified = true;
    System.out.println("Linhas a partir da linha " + line + " deletadas.");
  }

  // :XG Lin
  void DeleteUntilLine(int line) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (line <= 0 || line > file.GetFileSize()) {
      System.out.println("Linha inválida!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;

    while (currentNode != null) {
      if (lineNumber <= line) {
        file.GetDLL().Remove(currentNode);
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    isModified = true;
    System.out.println("Linhas até a linha " + line + " deletadas.");
  }

  // :/ elem
  void Search(String element) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;
    boolean found = false;

    System.out.println("Linhas contendo '" + element + "':");
    while (currentNode != null) {
      String line = currentNode.getData();
      if (line.contains(element)) {
        System.out.println("Linha " + lineNumber + ": " + line);
        found = true;
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    if (!found) {
      System.out.println("Nenhuma linha contendo '" + element + "' encontrada.");
    }
  }

  // :/ elem elemChange
  void Replace(String element, String elementChange) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    // int lineNumber = 1;
    boolean found = false;

    while (currentNode != null) {
      String line = currentNode.getData();
      if (line.contains(element)) {
        line = line.replace(element, elementChange);
        currentNode.setData(line);
        found = true;
      }
      currentNode = currentNode.getNext();
      // lineNumber++;
    }

    if (!found) {
      System.out.println("Nenhuma linha contendo '" + element + "' encontrada.");
    } else {
      isModified = true;
      System.out.println("Elemento substituído.");
    }
  }

  // :/ elem elemChange Line
  void ReplaceLine(String element, String elementChange, int line) {
    if (!IsOpened()) {
      System.out.println("Nenhum arquivo aberto!");
      return;
    }

    if (line <= 0 || line > file.GetFileSize()) {
      System.out.println("Linha inválida!");
      return;
    }

    Node currentNode = file.GetDLL().GetHead();
    int lineNumber = 1;
    boolean found = false;

    while (currentNode != null) {
      if (lineNumber == line) {
        String lineData = currentNode.getData();
        if (lineData.contains(element)) {
          lineData = lineData.replace(element, elementChange);
          currentNode.setData(lineData);
          found = true;
        }
        break;
      }
      currentNode = currentNode.getNext();
      lineNumber++;
    }

    if (!found) {
      System.out.println("Nenhuma linha contendo '" + element + "' encontrada.");
    } else {
      isModified = true;
      System.out.println("Elemento substituído.");
    }
  }

  // :ZZ
  void Save() {
    if (!isModified) {
      System.out.println("Nenhuma modificacao realizada.");
      return;
    }

    file.SaveFile();
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
    System.out.println(":w - Salvar o arquivo atual.");
    System.out.println(":w nomeArq.ext - Salvar o arquivo atual com o nome 'nomeArq.ext'.");
    System.out.println(":wq - Salvar o arquivo atual e sair do editor.");
    System.out.println(":q! - Sair do editor sem salvar as modificações realizadas.");
    System.out.println(":ZZ - Gravar conteúdo em arquivo, se alterado.");
    System.out.println(
        ":v LinIni LinFim - Selecionar as linhas de 'LinIni' a 'LinFim' para realizar operações de copiar ou cortar.");
        System.out.println(":y - Copiar as linhas selecionadas.");
    System.out.println(":c - Cortar as linhas selecionadas.");
    System.out.println(":p LinIniPaste - Colar as linhas copiadas após a linha 'LinIniPaste'.");
    System.out.println(":s - Exibir o conteúdo do arquivo de 10 em 10 linhas.");
    System.out.println(":s Lin - Exibir a linha 'Lin' do arquivo.");
    System.out.println(":s LinIni LinFim - Exibir as linhas de 'LinIni' a 'LinFim' do arquivo de 10 em 10 linhas.");
    System.out.println(":x Lin - Deletar a linha 'Lin' do arquivo.");
    System.out.println(":xG Lin - Deletar as linhas a partir da linha 'Lin' do arquivo.");
    System.out.println(":XG Lin - Deletar as linhas até a linha 'Lin' do arquivo.");
    System.out.println(":/ elem - Buscar todas as linhas que contém 'elem'.");
    System.out.println(":/ elem elemChange - Substituir 'elem' por 'elemChange' em todas as linhas.");
    System.out.println(":/ elem elemChange Lin - Substituir 'elem' por 'elemChange' na linha 'Lin'.");
    System.out.println(":help - Exibir os comandos disponíveis.");

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
          if (parts.length >= 2) { // Segundo argumento para nome do arquivo
            OpenFile(parts[1]);
          } else {
            System.out.println("Usage: :e fileName.txt");
          }
          break;

        case ":w":
          if (parts.length >= 2) { // Segundo argumento para nome do arquivo
            SaveAs(parts[1]);
            break;
          } else {
            SaveW();
            break;
          }

        case ":wq":
          SaveAndExit();
          break;

        case ":v":
          if (parts.length >= 3) { // Check second and third arguments for line numbers
            SelectLines(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
          } else {
            System.out.println("Usage: :v LinIni LinFim");
          }
          break;

        case ":c":
          Cut();
          break;

        case ":p":
          if (parts.length >= 2) { // Segundo argumento para linha de colagem
            Paste(Integer.parseInt(parts[1]));
          } else {
            System.out.println("Usage: :p LinIniPaste");
          }
          break;

        case ":s":
          if (parts.length >= 3) {
            DisplayLines(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
          } else if (parts.length >= 2) {
            DisplayLine(Integer.parseInt(parts[1]));
          } else {
            Display();
          }
          break;

        case ":x":
          if (parts.length >= 2) {
            DeleteLine(Integer.parseInt(parts[1]));
          } else {
            System.out.println("Usage: :x Lin");
          }
          break;

        case ":xG":
          if (parts.length >= 2) {
            DeleteFromLine(Integer.parseInt(parts[1]));
          } else {
            System.out.println("Usage: :xG Lin");
          }
          break;

        case ":XG":
          if (parts.length >= 2) {
            DeleteUntilLine(Integer.parseInt(parts[1]));
          } else {
            System.out.println("Usage: :XG Lin");
          }
          break;

        case ":/":
          if (parts.length == 2) {
            Search(parts[1]);
          } else if (parts.length == 3) {
            Replace(parts[1], parts[2]);
          } else if (parts.length == 4) {
            ReplaceLine(parts[1], parts[2], Integer.parseInt(parts[3]));
          } else {
            System.out.println("Usage: :/ element");
          }
          break;

        case ":y":
          Copy();
          break;

        case ":ZZ":
          Save();
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
