import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException, ParseException {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
       options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);

        String URL = "https://www.youtube.com/channel/UC55rfMA3tJbxgkvj_dDYyTA";
        driver.get(URL);

        WebElement subscriberCount = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse/div[3]/ytd-c4-tabbed-header-renderer/tp-yt-app-header-layout/div/tp-yt-app-header/div[2]/div[2]/div/div[1]/div/div[1]/yt-formatted-string[2]"));

        int initialCount = NumberFormat.getNumberInstance(java.util.Locale.US).parse(subscriberCount.getText()).intValue();

        driver.quit();
        System.out.println(getCurrentTimeStamp() + " Initial Subscriber count: " + initialCount);

        while (true) {

            Thread.sleep(90000);

            driver = new ChromeDriver(options);

            driver.get(URL);
            Thread.sleep(2000);

            subscriberCount = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse/div[3]/ytd-c4-tabbed-header-renderer/tp-yt-app-header-layout/div/tp-yt-app-header/div[2]/div[2]/div/div[1]/div/div[1]/yt-formatted-string[2]"));

            int currentCount = NumberFormat.getNumberInstance(java.util.Locale.US).parse(subscriberCount.getText()).intValue();

            if (currentCount > initialCount) {
                playSound("/Users/waougri/Desktop/Mario-coin-sound.wav");
                System.out.println(getCurrentTimeStamp() + " Subscriber count increased to: " + currentCount);



            } else if (currentCount < initialCount) {
                System.out.println(getCurrentTimeStamp() + " Subscriber count decreased to: " + currentCount);
                playSound("/Users/waougri/Desktop/Quack Sound Effect.wav");

            } else {
                System.out.println(getCurrentTimeStamp() + " Subscriber count unchanged: " + currentCount);
            }

            initialCount = currentCount;

            driver.quit();
        }
    }

    public static void playSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            System.out.println(getCurrentTimeStamp() + " Playing sound: " + filePath);
        } catch (Exception ex) {
            System.out.println(getCurrentTimeStamp() + " Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        return "["+sdf.format(now)+"]";
    }


}