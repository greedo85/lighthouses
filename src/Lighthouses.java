import java.util.Scanner;

/* og�lnie je�li user wybierze pole z latarni� to nie mo�na mu na to pozwoli�, czyli je�li wstawi statek
 * w pole z latarni� musi wyst�pi� wyj�tek (je�li pole do wstawienia statku b�dzie r�ne od EMPTY')
 * trzeba b�dzie parstowa� zapisane pola na int �eby sprawdzi� ile statk�w o�wietl�
 * trzeba zabroni� userowi wpisania innego znaku ni� int*/
public class Lighthouses {
    private final char EMPTY = '_';
    private final char SHIP = 'x';

    private char board[][];
    private Scanner key = new Scanner(System.in);

    public Lighthouses(int size) {
        board = new char[size][size];
        setBoard();
    }

    public static void main(String[] args) {
        Lighthouses lighthouses = new Lighthouses(12);
        System.out.println("Tak wygl�da plansza do kt�rej trzeba wstawi� statki:");
        System.out.println("'_' - to puste pole na kt�rym mo�na ustawi� statek.");
        System.out.println("Cyfra w polu oznacza ile s�siednich p�l w pionie i poziomie o�wietla latarnia");
        lighthouses.menu();
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
                    board[i][j] = EMPTY;
                }
            }
        }
    }

    public void menu() {
        boolean win = false;
        do {
            printBoard();
            System.out.println("Wybierz jedn� z opcji: ");
            System.out.println("'w' - wstaw statek");
            System.out.println("'z' - zdejmij statek");
            System.out.println("'s' - sprawd� czy wygra�e�");
            System.out.println("'q' - zako�cz gr�");
            char quit = key.next().charAt(0);

            switch (quit) {
                case 'w':
                    putShips();
                    break;
                case 'z':
                    removeShip();
                    break;
                case 's':
                    win = checkLighthouses();
                    break;
                case 'q':
                    System.exit(0);
            }
        } while (!win);
    }

    public void putShips() {
        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.print("Rz�d:");
            indexNumberRow = key.nextInt();
            System.out.print("Kolumna:");
            indexNumberColumn = key.nextInt();

            if (isIndexAvailabilty(indexNumberRow, indexNumberColumn)) {
                board[indexNumberRow][indexNumberColumn] = SHIP;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza plansz�, spr�buj jeszcze raz");
        }


    }

    private boolean isIndexAvailabilty(int indexNumberRow, int indexNumberColumn) {
        for (int i = indexNumberRow - 1; i <= indexNumberRow + 1; i++) {
            for (int j = indexNumberColumn - 1; j <= indexNumberColumn + 1; j++) {
                if (board[i][j] != EMPTY) {
                    System.out.println("Spr�buj jeszcze raz.");
                    return false;
                }
            }
        }
        return true;
    }

    public void removeShip() {
        System.out.println("Zdejmij statek, podaj rz�d i kolumn�:");
        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.println("Rz�d:");
            indexNumberRow = key.nextInt();
            System.out.println("Kolumna:");
            indexNumberColumn = key.nextInt();
            if (board[indexNumberRow][indexNumberColumn] == SHIP) {
                board[indexNumberRow][indexNumberColumn] = EMPTY;
                System.out.println("Zdj��em statek z:[" + indexNumberRow + "] [" + indexNumberColumn);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza plansz�, spr�buj jeszcze raz.");
        }
    }

    /* ta metoda musi por�wnywa� czy dla ka�dej latarni zgadza si� liczba uzyskana z policzenia x-�w*/
    public boolean checkLighthouses() {
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                if (board[i][j] != EMPTY && board[i][j] != SHIP) {
                    System.out.println("Latarnia:" + board[i][j]);
                    String indexValue = String.valueOf(board[i][j]);
                    int lighthouseValue = Integer.parseInt(indexValue);
                    int counter = 0;
                    /* Tu mamy ju� zapisan� warto�� latarni, teraz trzeba sprawdzi� w prz�d i ty� czy dla tej warto�ci nie ma wi�cej
                     * ni� mo�na postawi� w rz�dzie prz�d-ty� i g�ra-d�*/
                    /*przechodzenie rz�du poziomo*/
                    for (int k = 0; k < board[i].length; k++) {
                        if (board[i][k] == SHIP) {
                            counter++;
                        }
                    }
                    /*przechodzenie kolumny pionowo*/
                    for (int l = 0; l < board[i].length; l++) {
                        if (board[l][j] == SHIP) {
                            counter++;
                        }
                    }
                    System.out.println("Ilo�� statk�w" + counter);
                    if (counter != lighthouseValue) {
                        System.out.println("Popraw ustawienie statk�w " + board[i][j] + " licznik: " + counter);
                        return false;
                    }
                }
            }
        }

        System.out.println("Wygra�e�");
        return true;
    }
}
