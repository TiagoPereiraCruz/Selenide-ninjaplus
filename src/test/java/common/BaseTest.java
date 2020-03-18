package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.SideBar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.screenshot;
import static io.qameta.allure.Allure.addAttachment;

public class BaseTest {
    protected static MoviePage movie;
    protected static LoginPage Login;
    protected static SideBar Side;

    @BeforeMethod
    public void BaseSetUp() {

        Properties prop = new Properties();
        InputStream inputFile = getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            prop.load(inputFile);
        } catch (Exception ex) {
            System.out.println("Deu ruim ao carregar o config.properties! Trace => " + ex.getMessage());
        }

        Configuration.browser = prop.getProperty("browser");
        Configuration.baseUrl = prop.getProperty("url");
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout"));

        Login = new LoginPage();
        Side = new SideBar();
        movie = new MoviePage();
    }

    @AfterMethod
    public void BaseTearDown() {
        // Tiramos um screenshot pelo Selenide
        String tempShot = screenshot("temp_shot");

        // Queremos transformar em binário para anexar no report do Allure
        try {
            BufferedImage bimage = ImageIO.read(new File(tempShot));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(bimage, "png", baos);
            byte[] finalShot = baos.toByteArray();

            addAttachment("Evidência", new ByteArrayInputStream(finalShot));
        } catch (Exception ex) {
            System.out.println("Deu erro ao anexar o Screenshot :( Trace => " + ex.getMessage());
        }
    }
}
