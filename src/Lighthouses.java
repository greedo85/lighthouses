

import java.util.Arrays;
import java.util.Scanner;

/* og�lnie je�li user wybierze pole z latarni� to nie mo�na mu na to pozwoli�, czyli je�li wstawi jedynk�
 * w pole z latarni� musi wyst�pi� wyj�tek (je�li pole do wstawienia statku b�dzie r�ne od null)
 * trzeba b�dzie parstowa� zapisane pola na int �eby sprawdzi� ile statk�w o�wietl�
 * trzeba zabroni� userowi wpisania innego chara ni� '1'*/
public class Lighthouses {

    char board[][] = new char[12][12];
    Scanner key = new Scanner(System.in);

    public static void main( String[] args ) {
        Lighthouses lighthouses = new Lighthouses();
        lighthouses.setBoard();
        System.out.println("Tak wygl�da tablica do kt�rej trzeba wstawi� statki:");
        System.out.println("'_' - to puste pole na kt�rym mo�na ustawi� statek.");
        System.out.println("Cyfra w polu oznacza ile s�siednich p�l w pionie i poziomie o�wietla latarnia");
        lighthouses.printBoard(lighthouses.board);
        lighthouses.putShips(lighthouses.board);
    }
    public void printBoard(char board[][])
    {
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
        int indexNumberRow;
        int indexNumberColumn;
        char quit='q';
        while ((quit=key.next().charAt(0))!='q') {
            System.out.println("Wpisz 'q' aby zako�czy� gr�, lub 'z' aby zdj�� statek");
            if(quit=='z')
            {
                removeShip(board);
            }
            try {
                System.out.print("Rz�d:");
                indexNumberRow = key.nextInt();
                System.out.print("Kolumna:");
                indexNumberColumn = key.nextInt();
                int j;
                for (int i = indexNumberRow - 1; i <= indexNumberRow + 1; i++) {
                    for ( j = indexNumberColumn - 1; j <= indexNumberColumn + 1; j++) {
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
                if (check==true) {
                    tab[indexNumberRow][indexNumberColumn] = 'x';
                }
                printBoard(board);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Poza zakresem tablicy, spr�buj jeszcze raz");
                putShips(board);
            }
        }
    }
    public void removeShip(char[][]tab)
    {
        System.out.println("Zdejmij statek, podaj rz�d i kolumn�:");
        int indexNumberRow;
        int indexNumberColumn;
        try
        {
            System.out.println("Rz�d:");
            indexNumberRow=key.nextInt();
            System.out.println("Kolumna:");
            indexNumberColumn=key.nextInt();
            if(tab[indexNumberRow][indexNumberColumn]=='x')
            {
                board[indexNumberRow][indexNumberColumn]='_';
                System.out.println("Zdj��em statek z:["+indexNumberRow+"] ["+indexNumberColumn);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Poza zakresem tablicy, spr�buj jeszcze raz");
            removeShip(board);
        }
    }
}
