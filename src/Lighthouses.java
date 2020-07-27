
import java.util.Arrays;
import java.util.Scanner;

/* og�lnie je�li user wybierze pole z latarni� to nie mo�na mu na to pozwoli�, czyli je�li wstawi statek
 * w pole z latarni� musi wyst�pi� wyj�tek (je�li pole do wstawienia statku b�dzie r�ne od '_'')
 * trzeba b�dzie parstowa� zapisane pola na int �eby sprawdzi� ile statk�w o�wietl�
 * trzeba zabroni� userowi wpisania innego znaku ni� int*/
public class Lighthouses {

    char board[][] = new char[12][12];
    Scanner key = new Scanner(System.in);

    public static void main( String[] args ) {
        Scanner key = new Scanner(System.in);
        Lighthouses lighthouses = new Lighthouses();
        lighthouses.setBoard();
        System.out.println("Tak wygl�da tablica do kt�rej trzeba wstawi� statki:");
        System.out.println("'_' - to puste pole na kt�rym mo�na ustawi� statek.");
        System.out.println("Cyfra w polu oznacza ile s�siednich p�l w pionie i poziomie o�wietla latarnia");
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
        System.out.println("Podaj koordynaty kom�rki w kt�r� chcesz wstawi� statek:");
        System.out.println("Wpisz 'q' aby zako�czy� gr�, 'z' aby zdj�� statek, 's' aby sprawdzi� plansz�\n'd'aby i�� dalej");
        char quit = key.next().charAt(0);
        if (quit == 'z') {
            removeShip(board);
        } else if (quit == 's') {
            checkLighthouses(board);
            putShips(board);
        } else if (quit == 'q') {
            System.exit(0);
        }
        else if(quit=='d') {
            int indexNumberRow;
            int indexNumberColumn;
            try {
                System.out.print("Rz�d:");
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
                            System.out.println("Spr�buj jeszcze raz:");
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
                System.out.println("Poza zakresem tablicy, spr�buj jeszcze raz");
                putShips(board);
            }
        }
    }

    public void removeShip( char[][] tab ) {
        System.out.println("Zdejmij statek, podaj rz�d i kolumn�:");
        int indexNumberRow;
        int indexNumberColumn;
        try {
            System.out.println("Rz�d:");
            indexNumberRow = key.nextInt();
            System.out.println("Kolumna:");
            indexNumberColumn = key.nextInt();
            if (tab[indexNumberRow][indexNumberColumn] == 'x') {
                board[indexNumberRow][indexNumberColumn] = '_';
                System.out.println("Zdj��em statek z:[" + indexNumberRow + "] [" + indexNumberColumn);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza zakresem tablicy, spr�buj jeszcze raz");
            removeShip(board);
        }
        putShips(board);
    }

    /* ta metoda musi por�wnywa� czy dla ka�dej latarni zgadza si� liczba uzyskana z policzenia x-�w*/
    public void  checkLighthouses( char[][] tab ) {
        boolean winOrNot = false;
        for (int i = 1; i < tab.length - 1; i++) {
            for (int j = 1; j < tab[i].length - 1; j++) {
                if (tab[i][j] != '_' && tab[i][j] != 'x') {
                    System.out.println("Latarnia:" + tab[i][j]);
                    String indexValue = String.valueOf(tab[i][j]);
                    int lighthouseValue = Integer.parseInt(indexValue);
                    int counter = 0;
                    /* Tu mamy ju� zapisan� warto�� latarni, teraz trzeba sprawdzi� w prz�d i ty� czy dla tej warto�ci nie ma wi�cej
                     * ni� mo�na postawi� w rz�dzie prz�d-ty� i g�ra-d�*/
                    /*przechodzenie rz�du poziomo*/
                    for (int k = 0; k < tab[i].length ; k++) {
                        if (tab[i][k] == 'x') {
                            counter += 1;
                        }
                    }
                    /*przechodzenie rz�du pionowo*/
                    for (int l = 0; l < tab[i].length ; l++) {
                        if (tab[l][j] == 'x') {
                            counter += 1;
                        }
                    }
                    System.out.println("Ilo�� statk�w"+counter);
                    if (counter == lighthouseValue) {
                        winOrNot = true;

                    } else {
                        winOrNot = false;
                        System.out.println("Popraw ustawienie statk�w "+tab[i][j]+ " licznik: "+counter);
                        putShips(board);
                    }
                }
            }

        }
        if (winOrNot == true) {
            System.out.println("Wygra�e�");
            System.exit(0);
        }
        putShips(board);
    }
}
