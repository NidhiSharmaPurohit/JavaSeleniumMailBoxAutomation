import org.openqa.selenium.WebDriver;

public class GmailMailBoxFunctionality implements IMailBoxFunctionality{

    private String UserName;
    private String Password;

    private WebDriver _driver;

    private static final String  MailLoginUri = "https://www.gmail.com/";

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
        return null;
    }

    @Override
    public boolean Login(String userName, String EncodedPassword) {
        return false;
    }

    @Override
    public boolean SendMail(String from, String to, String subject, String messageBody) {
        return false;
    }

    @Override
    public boolean DeleteSpamMails(boolean deleteAll) {
        return false;
    }

    @Override
    public int FilterCountOfMails(String from, String to, String subject) {
        return 0;
    }

    @Override
    public boolean VerifyMailDeliveredByMailCount() {
        return false;
    }
}
