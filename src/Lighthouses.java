
import java.util.Arrays;
import java.util.Scanner;


public class Lighthouses {

    char board[][] = new char[12][12];
    Scanner key = new Scanner(System.in);

    public static void main( String[] args ) {
        Scanner key = new Scanner(System.in);
        Lighthouses lighthouses = new Lighthouses();
        lighthouses.setBoard();
        System.out.println("Tak wygląda plansza do której trzeba wstawić statki:");
        System.out.println("'_' - to puste pole na którym można ustawić statek.");
        System.out.println("Cyfra w polu oznacza ile sąsiednich pól w pionie i poziomie oświetla latarnia");
        lighthouses.printBoard();
        lighthouses.menu();
       // lighthouses.putShips();
    }

    public void printBoard() {
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
    public void menu() {
        System.out.println("Podaj koordynaty komórki w którą chcesz wstawić statek:");
        System.out.println("Wpisz 'q' aby zakończyć grę, 'z' aby zdjąć statek, 's' aby sprawdzić planszę\n'd' aby iść dalej");
        char quit = key.next().charAt(0);
        switch (quit) {
            case 'z':
                removeShip();
                break;
            case 's':
                checkLighthouses();
                putShips();
                break;
            case 'q':
                System.exit(0);
            case 'd':
                putShips();
                break;
            default:
                putShips();
        }
    }
    public void putShips() {
        boolean checkIndexAvailability = false;

                int indexNumberRow;
                int indexNumberColumn;
                try {
                    System.out.print("Rząd:");
                    indexNumberRow = key.nextInt();
                    System.out.print("Kolumna:");
                    indexNumberColumn = key.nextInt();
                    int j;
                    for (int i = indexNumberRow - 1; i <= indexNumberRow + 1; i++) {
                        for (j = indexNumberColumn - 1; j <= indexNumberColumn + 1; j++) {
                            /*System.out.print(board[i][j] + " ");*/
                            if (board[i][j] == '_') {
                                checkIndexAvailability = true;

                            } else {
                                System.out.println("Spróbuj jeszcze raz:");
                                printBoard();
                                checkIndexAvailability = false;
                                putShips();
                            }
                        }
                    }
                    if (checkIndexAvailability == true) {
                        board[indexNumberRow][indexNumberColumn] = 'x';
                        printBoard();
                        menu();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Poza planszą, spróbuj jeszcze raz");
                    putShips();
                }


    }

    public void removeShip() {
        System.out.println("Zdejmij statek, podaj rząd i kolumnę:");
        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.println("Rząd:");
            indexNumberRow = key.nextInt();
            System.out.println("Kolumna:");
            indexNumberColumn = key.nextInt();
            if (board[indexNumberRow][indexNumberColumn] == 'x') {
                board[indexNumberRow][indexNumberColumn] = '_';
                System.out.println("Zdjąłem statek z:[" + indexNumberRow + "] [" + indexNumberColumn);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza planszą, spróbuj jeszcze raz");
            removeShip();
        }
        putShips();
    }

    /* ta metoda musi porównywać czy dla każdej latarni zgadza się liczba uzyskana z policzenia x-ów*/
    public void checkLighthouses() {
        boolean win = false;
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                if (board[i][j] != '_' && board[i][j] != 'x') {
                    System.out.println("Latarnia:" + board[i][j]);
                    String indexValue = String.valueOf(board[i][j]);
                    int lighthouseValue = Integer.parseInt(indexValue);
                    int counter = 0;
                    /* Tu mamy już zapisaną wartość latarni, teraz trzeba sprawdzić w przód i tył czy dla tej wartości nie ma więcej
                     * niż można postawić w rzędzie przód-tył i góra-dół*/
                    /*przechodzenie rzędu poziomo*/
                    for (int k = 0; k < board[i].length; k++) {
                        if (board[i][k] == 'x') {
                            counter += 1;
                        }
                    }
                    /*przechodzenie kolumny pionowo*/
                    for (int l = 0; l < board[i].length; l++) {
                        if (board[l][j] == 'x') {
                            counter += 1;
                        }
                    }
                    System.out.println("Ilość statków" + counter);
                    if (counter == lighthouseValue) {
                        win = true;

                    } else {
                        win = false;
                        System.out.println("Popraw ustawienie statków " + board[i][j] + " licznik: " + counter);
                        putShips();
                    }
                }
            }

        }
        if (win == true) {
            System.out.println("Wygrałeś");
            System.exit(0);
        }
        putShips();
    }
}
