import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    private static Process process;
    protected WebDriver chromeDriver;

    @BeforeAll
    static void setupStand() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "libs/qualit-sandbox.jar");
            process = processBuilder.start();

            // без этого у меня не успевает подняться приложение целиком :(
            Thread.sleep(10_000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    static void closeStand() {
        process.destroyForcibly();
    }

    @BeforeEach
    public void initChromeDriver() {
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        chromeDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
        chromeDriver.get("http://localhost:8080/");
    }

    @AfterEach
    public void tearDownChromeDriver() {
        chromeDriver.quit();
    }
}