package trabalhoprofmichele;

public class Movie {
    
    private static final String[] genres = {
        "Ação",
        "Romance",
        "Comedia",
        "Terror"
    }; 
    
    private String name;
    private String author;
    private int howMany;
    private String synopsis;
    private boolean type;
    private int genre;

    public Movie() {
    }
    
    public Movie(String name, String author, int howMany, String synopsis, boolean type, int genre) {
        this.name = name;
        this.author = author;
        this.howMany = howMany;
        this.synopsis = synopsis;
        this.type = type;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }
    
    public static String[] getGenres() {
        return genres;
    }
}
