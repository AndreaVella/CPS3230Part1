package test.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class driverSetup {
    WebDriver driver;

    public WebDriver setup(){
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();

        return driver;
    }

    public WebDriver getRequiredLink(WebDriver driver, String url){
        driver.get(url);

        return driver;
    }

}
