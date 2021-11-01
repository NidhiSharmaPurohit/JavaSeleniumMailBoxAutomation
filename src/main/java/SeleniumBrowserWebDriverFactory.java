import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumBrowserWebDriverFactory {

    public static WebDriver getInstance(BrowserType browserType){
        WebDriver driver = null;
        switch (browserType)
        {
            case FireFox:
                System.setProperty("webdriver.gecko.driver", "geckodriver-v0.30.0-win64/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case Chrome:
                driver = new ChromeDriver();
                break;
            case Edge:
                driver = new EdgeDriver();
                break;
            case Opera:
                driver = new OperaDriver();
                break;
            case Safari:
                driver = new SafariDriver();
                break;
            case IE:
                driver = new InternetExplorerDriver();
                break;
            default:
                driver = null;

        }

        return driver;
    }

    public static enum BrowserType
    {
        FireFox,
        Chrome,
        IE,
        Opera,
        Safari,
        Edge
    }


}
