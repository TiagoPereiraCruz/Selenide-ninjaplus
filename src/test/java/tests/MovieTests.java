package tests;

import common.BaseTest;
import libs.Database;
import models.MovieModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MovieTests extends BaseTest {

    private Database db;

    @BeforeMethod
    public void setUp() {
        Login
                .open()
                .with("tiago@ninjaplus.com", "pwd123");
    }

    @BeforeSuite
    public void delorean() {
        db = new Database();
        db.resetMovies();
    }

    @Test
    public void shouldRegisterANewMovie() {

        MovieModel movieData = new MovieModel(
                "Jumanji - Próxima fase",
                "Pré-venda",
                2020,
                "16/01/2020",
                Arrays.asList("The Rock", "Jack Black", "Kevin Hart", "Karen Gillan", "Danny DeVito"),
                "Tentado a revisitar o mundo de jumanji, Spencer decide "
                        + "consertar o bug no jogo do game que permite que sejam transportados ao local",
                "jumanji2.jpg"
        );

//        db.deleteMovie(movieData.title);

        movie
                .add()
                .create(movieData)
                .items().findBy(text(movieData.title)).shouldBe(visible);
    }

    @Test
    public void shouldSearchtwoMovies() {

        movie.search("Batman").items().shouldHaveSize(2);
    }

    @AfterMethod
    public void tearDown() {
        Login.clearSession();
    }
}
