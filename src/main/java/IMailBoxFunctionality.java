import org.openqa.selenium.WebDriver;

public interface IMailBoxFunctionality {

   public WebDriver InitDriver();

   public boolean Login(String userName, String EncodedPassword);

   public boolean SendMail(String from, String to, String subject, String messageBody);

   public boolean DeleteSpamMails(boolean deleteAll);

   public int FilterCountOfMails(String from, String to, String subject);

   public boolean VerifyMailDeliveredByMailCount();

}
