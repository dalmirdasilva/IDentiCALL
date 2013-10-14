package trabalhoprofmichele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dalmir
 */
public class TrabalhoProfMichele {

    private static List<Movie> initialList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        UI ui = new UI();
        initialList.add(new Movie("The Silence of the Lambs", "Thomas Harris", 22, "The Silence of the Lambs (O Silêncio dos InocentesPT/BR) é um filme americano lançado em 1991.[2] Foi dirigido por Jonathan Demme e estrela Jodie Foster, Anthony Hopkins, Ted Levine e Scott Glenn. \nÉ baseado no romance homônimo de \n1988 porThomas Harris, o segundo a \napresentar Hannibal Lecter, um psiquiatra \nassassino em série canibal.", true, 1));
        initialList.add(new Movie("Merican Beauty", "Alan Ball", 22, "American Beauty is a 1999 American drama film directed by Sam Mendes and written by Alan Ball. Kevin Spacey stars as office worker \nLester Burnham, who has \na midlife crisis when \nhe becomes infatuated with \nhis teenage daughter's best friend.", true, 1));
        ui.setMovies(initialList);
        ui.setVisible(true);
        Thread.sleep(500);
        ui.populateForm(initialList.get(0));
    }
}
