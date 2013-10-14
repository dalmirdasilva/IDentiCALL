package trabalhoprofmichele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author dalmir
 */
public class UI extends JFrame {

    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JTextField movieName;
    private JTextField movieAuthor;
    private JTextField howMany;
    private ButtonGroup buttonGroup;
    private JRadioButton dvdRadio;
    private JRadioButton brRadio;
    private JComboBox genreComboBox;
    private JTextArea synopsisText;
    private JButton addMovie;
    private JButton removeMovie;
    private JButton cancelMovie;
    private List<Movie> movies;
    private JButton searchMovie;
    private JButton clearFormButtom;

    UI() {
        setSize(700, 300);
        setResizable(false);
        setTitle("Cadastro filmes");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        LayoutManager layout = new GridBagLayout();
        movies = new ArrayList<>();
        northPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();
        movieName = new JTextField(15);
        movieAuthor = new JTextField(15);
        howMany = new JTextField(10);
        howMany = new JTextField(10);
        buttonGroup = new ButtonGroup();
        dvdRadio = new JRadioButton();
        brRadio = new JRadioButton();
        buttonGroup.add(dvdRadio);
        buttonGroup.add(brRadio);
        genreComboBox = new JComboBox();
        synopsisText = new JTextArea();
        addMovie = new JButton("Adiciona");
        removeMovie = new JButton("Remove");
        cancelMovie = new JButton("Cancela");
        searchMovie = new JButton("Buscar filme");
        clearFormButtom = new JButton("Limpa");

        northPanel.setBackground(new Color(0xcc, 0xcc, 0xff));
        northPanel.setLayout(layout);
        add(northPanel, BorderLayout.NORTH);

        centerPanel.setBackground(new Color(0xcc, 0xcc, 0xff));
        centerPanel.setLayout(layout);
        add(centerPanel, BorderLayout.CENTER);

        southPanel.setBackground(new Color(0x33, 0xff, 0xff));
        southPanel.setLayout(layout);
        add(southPanel, BorderLayout.SOUTH);

        initTop();
        initCenter();
        initButtom();
        initActions();
    }

    private void initTop() {
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets.top = 10;
        constraint.insets.bottom = 10;
        northPanel.add(new JLabel("Cadastro de Filmes - Locadora Filme Certo", JLabel.CENTER), constraint);
    }

    private void initCenter() {
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets.top = 4;
        constraint.insets.bottom = 4;
        constraint.insets.left = 10;
        constraint.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(new JLabel("Nome do filme:"), constraint);
        constraint.gridx = 1;
        constraint.gridwidth = 6;
        constraint.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(movieName, constraint);
        constraint.gridx = 7;
        constraint.gridwidth = 1;
        constraint.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(searchMovie, constraint);

        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(new JLabel("Nome do autor:"), constraint);

        constraint.gridx = 1;
        constraint.gridwidth = 7;
        constraint.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(movieAuthor, constraint);

        constraint.gridy = 2;
        constraint.gridx = 0;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(new JLabel("Quantidade de examplos:"), constraint);

        constraint.gridx = 2;
        constraint.gridwidth = 1;
        constraint.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(howMany, constraint);

        constraint.gridx = 4;
        constraint.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(new JLabel("Tipo:"), constraint);

        constraint.gridx = 6;
        constraint.anchor = GridBagConstraints.LINE_START;
        dvdRadio.setText("DVD");
        centerPanel.add(dvdRadio, constraint);
        constraint.gridx = 7;
        brRadio.setText("Blu-Ray DVD");
        centerPanel.add(brRadio, constraint);
    }

    private void initButtom() {
        JScrollPane scroll = new JScrollPane();
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets.top = 4;
        constraint.insets.bottom = 4;
        constraint.insets.left = 60;
        constraint.gridy = 0;
        constraint.gridx = 0;
        constraint.anchor = GridBagConstraints.LINE_END;
        southPanel.add(new JLabel("Genero:"), constraint);
        constraint.gridx = 1;
        constraint.insets.left = 10;
        constraint.gridwidth = 6;
        constraint.anchor = GridBagConstraints.LINE_START;
        southPanel.add(genreComboBox, constraint);
        for (String s : Movie.getGenres()) {
            genreComboBox.addItem(s);
        }
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.gridwidth = 1;
        constraint.anchor = GridBagConstraints.LINE_END;
        southPanel.add(new JLabel("Sinopse:"), constraint);
        constraint.gridx = 1;
        constraint.gridwidth = 2;
        constraint.anchor = GridBagConstraints.LINE_START;
        synopsisText.setColumns(15);
        synopsisText.setRows(5);
        constraint.gridheight = 5;
        scroll.setViewportView(synopsisText);
        southPanel.add(scroll, constraint);
        constraint.gridx = 3;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        southPanel.add(addMovie, constraint);
        constraint.gridx = 4;
        southPanel.add(removeMovie, constraint);
        constraint.gridy = 2;
        constraint.gridx = 3;
        southPanel.add(cancelMovie, constraint);
        constraint.gridx = 4;
        southPanel.add(clearFormButtom, constraint);
    }

    private void initActions() {

        addMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                insertMovie();
            }
        });

        removeMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeMovie();
            }
        });

        searchMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(evt.paramString());
                searchMovie();
            }
        });

        synopsisText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getClass());
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        searchMovie.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println(e.paramString());
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(e.paramString());
            }
        });

        clearFormButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    private void insertMovie() {
        Movie m = createFromForm();
        if (m != null) {
            movies.add(m);
        }
    }

    private void removeMovie() {
        String name = movieName.getText();
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                JOptionPane.showMessageDialog(null, "Filme removido.");
                movies.remove(movie);
                clearForm();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Filme não encontrado.");
    }

    private void searchMovie() {
        String name = movieName.getText();
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                populateForm(movie);
                JOptionPane.showMessageDialog(null, "Filme encontrado!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Filme não encontrado!");
    }

    private Movie createFromForm() {
        Movie movie = new Movie();
        movie.setName(movieName.getText());
        movie.setAuthor(movieAuthor.getText());
        int hm = 0;
        try {
            hm = Integer.parseInt(howMany.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Use numeros somente no campo 'Quantidade'.");
            return null;
        }
        movie.setHowMany(hm);
        movie.setSynopsis(synopsisText.getText());
        boolean isDvd = false;
        if (dvdRadio.isSelected()) {
            isDvd = true;
        }
        movie.setType(isDvd);
        return movie;
    }

    public void clearForm() {
        movieName.setText("");
        movieAuthor.setText("");
        howMany.setText("");
        synopsisText.setText("");
        genreComboBox.setSelectedIndex(0);
    }

    public void populateForm(Movie movie) {
        movieName.setText(movie.getName());
        movieAuthor.setText(movie.getAuthor());
        howMany.setText(String.valueOf(movie.getHowMany()));
        synopsisText.setText(movie.getSynopsis());
        if (movie.getType()) {
            dvdRadio.setSelected(true);
        } else {
            brRadio.setSelected(true);
        }
        genreComboBox.setSelectedIndex(movie.getGenre());
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
