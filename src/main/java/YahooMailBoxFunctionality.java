import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Base64;
import java.util.List;

public class YahooMailBoxFunctionality implements IMailBoxFunctionality{

    private String UserName;
    private String Password;

    private WebDriver _driver;

    private static final String  MailLoginUri = "https://mail.yahoo.com/";

    public String getPassword() {
        return Password;
    }

    public String getUserName(){
        return UserName;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public WebDriver InitDriver() {
        _driver = SeleniumBrowserWebDriverFactory.
                  getInstance(SeleniumBrowserWebDriverFactory.BrowserType.FireFox);

        if(_driver != null)
        {
            _driver.get(MailLoginUri);
        }

        return _driver;
    }

    @Override
    public boolean Login(String userName, String EncodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(EncodedPassword);
        String decodedPassword = new String(decodedBytes);

        WebElement signInElement = _driver.findElement(By.xpath("/html/body/div[1]/a"));
        signInElement.click();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement userNameElement = _driver.findElement(By.xpath("//input[@id='login-username']"));
        userNameElement.sendKeys(userName);


        WebElement nextbutton = _driver.findElement(By.xpath("//*[@id='login-signin']"));
        nextbutton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Now Enter Password");
        WebElement passwordElement = _driver.findElement(By.xpath("//*[@id='login-passwd']"));
        passwordElement.sendKeys(decodedPassword);
        System.out.println("Entered Password");

        WebElement loginButton = _driver.findElement(By.xpath("//*[@id='login-signin']"));
        loginButton.click();

        return true;
    }

    @Override
    public boolean SendMail(String from, String to, String subject, String messageBody) {

        WebElement linkcompose = _driver.findElement(By.linkText("Compose"));
        linkcompose.click();


        WebElement send_to = _driver.findElement(By.id("message-to-field"));
        send_to.sendKeys(to);

        WebElement subject_to = _driver.findElement(By.xpath("//div[@class='p_R D_F W_6D6F ']/input"));
        subject_to.sendKeys(subject);




        WebElement mail_content = _driver.findElement(By.xpath("//div[@id='editor-container']/div[1]"));
        mail_content.sendKeys(messageBody);

        WebElement send_mail = _driver.findElement(By.xpath("//span[contains(text(),'Send')]"));
        send_mail.click();


        _driver.navigate().refresh();
        return true;
    }

    @Override
    public boolean DeleteSpamMails(boolean deleteAll) {
        WebElement span_link = _driver.findElement(By.xpath("//span[contains(text(),'Spam')]"));
        span_link.click();



        WebElement span_select = _driver.findElement(By.xpath("//span[@class='D_F']/button"));
        span_select.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("after selection");

        List<WebElement> span_list = _driver.findElements(By.xpath("//span[@class='D_X em_N o_h X_eo6 G_e t_l i_N']"));
        //"//span[contains(text(),'Delete')]"));
        span_list.get(2).click();

        //System.out.println("after delete click");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement span_ok = _driver.findElement(By.xpath("//div[@class='P_ZG0ms9']/button[contains(text(),'OK')]"));
        span_ok.click();
//div[@class='typeahead-inputs-container p_R']/input[1]
        return true;
    }

    @Override
    public int FilterCountOfMails(String from, String to, String subject) {
        int filteredMailCount = 0;
        String filter_condition = "from:"+ from + " subject:" + subject;
        WebElement search_content = _driver.findElement(By.xpath("//div[@class='typeahead-inputs-container p_R']/input[1]"));

        search_content.sendKeys(filter_condition);

        search_content.sendKeys(Keys.ENTER);

        List<WebElement> elementList = _driver.findElements(By.xpath("//span[@class='D_F ab_C gl_C W_6D6F']"));
        if(elementList.size() > 0)
        {
            filteredMailCount = elementList.size();
            //System.out.println("greather than 0");
        }
        else
        {
            //System.out.println("No Element Found");
        }
        return filteredMailCount;
    }

    @Override
    public boolean VerifyMailDeliveredByMailCount() {
        List<WebElement> countlist = _driver.findElements(By.xpath("//span[@data-test-id='displayed-count']"));
        System.out.println(countlist.get(0).getText());
        return true;

    }
}
