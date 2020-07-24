

import java.util.Arrays;
import java.util.Scanner;

/* ogólnie jeœli user wybierze pole z latarni¹ to nie mo¿na mu na to pozwoliæ, czyli jeœli wstawi jedynkê
 * w pole z latarni¹ musi wyst¹piæ wyj¹tek (jeœli pole do wstawienia statku bêdzie ró¿ne od null)
 * trzeba bêdzie parstowaæ zapisane pola na int ¿eby sprawdziæ ile statków oœwietl¹
 * trzeba zabroniæ userowi wpisania innego chara ni¿ '1'*/
public class Lighthouses {

    char board[][] = new char[12][12];
    Scanner key = new Scanner(System.in);

    public static void main( String[] args ) {
        Scanner key = new Scanner(System.in);
        Lighthouses lighthouses = new Lighthouses();
        lighthouses.setBoard();
        System.out.println("Tak wygl¹da tablica do której trzeba wstawiæ statki:");
        System.out.println("'_' - to puste pole na którym mo¿na ustawiæ statek.");
        System.out.println("Cyfra w polu oznacza ile s¹siednich pól w pionie i poziomie oœwietla latarnia");
        lighthouses.printBoard(lighthouses.board);
        lighthouses.putShips(lighthouses.board);

    }
    public void printBoard( char board[][] ) {
        System.out.println("    1  2  3  4  5  6  7  8  9  10 ");
        for (int i = 1; i < board.length - 1; i++) {
            if (i < 10) {
                System.out.print(i + " :");
            } else {
                System.out.print(i + ":");
            }
            for (int j = 1; j < board[i].length - 1; j++) {
                System.out.print("[" + board[i][j] + "]");
            }
            System.out.println(" ");
        }
    }
    public void setBoard() {
        board[1][1] = '1';
        board[1][10] = '2';
        board[3][8] = '4';
        board[4][6] = '2';
        board[5][4] = '0';
        board[6][7] = '2';
        board[7][5] = '0';
        board[8][3] = '0';
        board[10][1] = '2';
        board[10][10] = '3';
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = '_';
                }
            }
        }
    }

    public void putShips( char[][] tab ) {
        boolean check = false;
        System.out.println("Podaj koordynaty komórki w któr¹ chcesz wstawiæ statek:");
        System.out.println("Wpisz 'q' aby zakoñczyæ grê, 'z' aby zdj¹æ statek, 's' aby sprawdziæ planszê");
        char quit = key.next().charAt(0);
        if (quit == 'z') {
            removeShip(board);
        }
        else if (quit == 's') {
            checkLighthouses(board);
            putShips(board);
        }
        else if(quit=='q')
        {
            System.exit(0);
        }

        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.print("Rz¹d:");
            indexNumberRow = key.nextInt();
            System.out.print("Kolumna:");
            indexNumberColumn = key.nextInt();
            int j;
            for (int i = indexNumberRow - 1; i <= indexNumberRow + 1; i++) {
                for (j = indexNumberColumn - 1; j <= indexNumberColumn + 1; j++) {
                    /*System.out.print(tab[i][j] + " ");*/
                    if (tab[i][j] == '_') {
                        check = true;
                    } else {
                        System.out.println("Spróbuj jeszcze raz:");
                        printBoard(board);
                        check = false;
                        putShips(board);
                    }
                }
            }
            if (check == true) {
                tab[indexNumberRow][indexNumberColumn] = 'x';
                printBoard(board);
                putShips(board);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza zakresem tablicy, spróbuj jeszcze raz");
            putShips(board);
        }
    }

    public void removeShip( char[][] tab ) {
        System.out.println("Zdejmij statek, podaj rz¹d i kolumnê:");
        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.println("Rz¹d:");
            indexNumberRow = key.nextInt();
            System.out.println("Kolumna:");
            indexNumberColumn = key.nextInt();
            if (tab[indexNumberRow][indexNumberColumn] == 'x') {
                board[indexNumberRow][indexNumberColumn] = '_';
                System.out.println("Zdj¹³em statek z:[" + indexNumberRow + "] [" + indexNumberColumn);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza zakresem tablicy, spróbuj jeszcze raz");
            removeShip(board);
        }
        putShips(board);
    }

    public void /*boolean*/ checkLighthouses( char[][] tab ) {
        int counter = 0;
        int lighthouseCounter;
        for (int i = 1; i < tab.length - 1; i++) {
            for (int j = 1; j < tab[i].length - 1; j++) {
                if (tab[i][j] != '_' && tab[i][j] != 'x') {
                    int currentIndex = j;
                    System.out.println("Latarnia:" + tab[i][j]);
                    String indexValue = String.valueOf(tab[i][j]);
                    lighthouseCounter = Integer.parseInt(indexValue);
                    /* Tu mamy ju¿ zapisan¹ wartoœæ latarni, teraz trzeba sprawdziæ w przód i ty³ czy dla tej wartoœci nie ma wiêcej
                     * ni¿ mo¿na postawiæ w rzêdzie przód-ty³ i góra-dó³*/
                    for (int k = 0; k < tab[j].length - 1; k++) {
                        if (tab[j][k] == 'x') {
                            counter += 1;
                        }
                        System.out.println("podtablica: " + tab[j][k]);
                    }
                }
            }
        }
        System.out.println("Liczba xów dla latarni" + counter);
        putShips(board);
    }
}
