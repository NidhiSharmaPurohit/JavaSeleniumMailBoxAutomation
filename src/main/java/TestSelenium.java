import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.jws.soap.SOAPBinding;
import java.security.PublicKey;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestSelenium {

    public UserDetail getUserDetailFromConfig()
    {
        UserDetail userDetail = new UserDetail();

        InputStream inputStream =null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }


            // get the property value and print it out
            String user = prop.getProperty("userName");
            String password = prop.getProperty("Password");
            String from = prop.getProperty("From");
            String to = prop.getProperty("To");
            String subject = prop.getProperty("Subject");
            String message = prop.getProperty("MessageBody");

            //System.out.println("User " + user);
            //System.out.println("Password " + password);
            //System.out.println("From " + from);
            //System.out.println("To " + to);
            //System.out.println("Subject " + subject);
            //System.out.println("MessageBody " + message);

            userDetail.userName = user;
            userDetail.password = password;
            userDetail.From = from;
            userDetail.To = to;
            userDetail.Subject = subject;
            userDetail.MessageBody = message;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
           if(inputStream != null) {
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }

        return userDetail;
    }

    public static void main(String[] args) {

        System.out.println("Started selenium MailBox Test Suite");

        UserDetail userDetail =  new TestSelenium().getUserDetailFromConfig();

        IMailBoxFunctionality mailBoxFunctionality = new YahooMailBoxFunctionality();
        WebDriver driver =  mailBoxFunctionality.InitDriver();
        if(driver != null)
            System.out.println("Selenium WebDriver Initialized Successfully");
        else
            System.out.println("Selenium WebDriver Not Initialized ");

        boolean isLogin = mailBoxFunctionality.Login(userDetail.userName, userDetail.password);

        if(isLogin)
            System.out.println("Login is successfull");
        else
            System.out.println("Login  Failed");

        boolean isSendMailSuccess = mailBoxFunctionality.SendMail(userDetail.From,
                                                                   userDetail.To,
                                                                   userDetail.Subject,
                                                                   userDetail.MessageBody);
        if(isSendMailSuccess)
            System.out.println("Mail Send SuccessFully");
        else
            System.out.println("Mail Not Send. Mail Failed");

        boolean isDeleteSpamMailSuccess = mailBoxFunctionality.DeleteSpamMails(true);

        if(isDeleteSpamMailSuccess)
            System.out.println("Spam Mail Deleted SuccessFully");
        else
            System.out.println("Spam Mail Deletion Failed");

        int filteredMailBoxCount = mailBoxFunctionality.FilterCountOfMails(userDetail.From,
                                                                           userDetail.To,
                                                                           userDetail.Subject);
        if(filteredMailBoxCount >= 1)
            System.out.println("Mail Found with From and Subject");
        else
            System.out.println("Mail Not Found with From and Subject. Failed");

        boolean isMailDelivered = mailBoxFunctionality.VerifyMailDeliveredByMailCount();

        if(isMailDelivered)
            System.out.println("Mail Delivered Verified SuccessFully");
        else
            System.out.println("Mail Delivered Verification Failed");

        System.out.println("Finished Testing MailBox Selenium Test Suite");

    }

    private class UserDetail
    {
        public String userName;
        public String password;
        public String From;
        public String To;
        public String Subject;
        public String MessageBody;
    }
}
