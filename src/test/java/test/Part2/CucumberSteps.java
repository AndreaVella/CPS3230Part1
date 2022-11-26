package test.Part2;

import api.requests.ApiRequests;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CucumberSteps {
    WebDriver driver;
    ApiRequests apiRequest;

    //region Valid Login Test
    @Given("I am a user of marketalertum")
    public void iAmUserOfMarketAlertUm() {
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();

        driver.get("https://www.marketalertum.com/Alerts/Login");
    }

    @When("I login using valid credentials")
    public void iLoginValid() {
        WebElement searchField = driver.findElement(By.name("UserId"));
        searchField.sendKeys("afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4");
        WebElement searchButton = driver.findElement(By.tagName("input"));
        searchButton.submit();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        String currentPage = driver.getCurrentUrl();
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/List", currentPage);
    }
    //endregion

    //region InValid Login Test
    @When("I login using invalid credentials")
    public void iLoginInvalid() {
        WebElement searchField = driver.findElement(By.name("UserId"));
        searchField.sendKeys("bfdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4");
        WebElement searchButton = driver.findElement(By.tagName("input"));
        searchButton.submit();

    }

    @Then("I should see the login screen again")
    public void iShouldSeeLoginScreen() {
        String currentPage = driver.getCurrentUrl();
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/Login", currentPage);
    }
    //endregion

    //region Alert Layout
    @Given("I am an administrator of the website and I upload 3 alerts")
    public void iAmAdminAndUploadThreeAlerts() {
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();
        apiRequest = new ApiRequests();

        String mockJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";

        for(int i = 0 ; i < 3 ; i++) apiRequest.postAlert(mockJson);
    }

    @When("I view a list of alerts")
    public void iViewAlerts() {
        driver.get("https://www.marketalertum.com/Alerts/Login");
        WebElement searchField = driver.findElement(By.name("UserId"));
        searchField.sendKeys("afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4");
        WebElement searchButton = driver.findElement(By.tagName("input"));
        searchButton.submit();

        driver.get("https://www.marketalertum.com/Alerts/List");
    }

    @Then("each alert should contain an icon")
    public void alertContainsIcon() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String icon = webElement.findElement(By.tagName("h4")).findElement(By.tagName("img")).getAttribute("src");
            assert (icon != null);
        }
    }

    @And("each alert should contain a heading")
    public void alertContainsHeading() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String heading = webElement.findElement(By.tagName("h4")).getText();
            assert (heading != null);
        }
    }

    @And("each alert should contain a description")
    public void alertContainsDescription() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String description = webElement.findElements(By.tagName("td")).get(2).getText();
            assert (description != null);
        }
    }

    @And("each alert should contain a price")
    public void alertContainsPrice() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String price = webElement.findElements(By.tagName("td")).get(3).getText();
            assert (price != null);
        }
    }

    @And("each alert should contain an image")
    public void alertContainsImage() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String image = webElement.findElements(By.tagName("img")).get(1).getAttribute("src");
            assert (image != null);
        }
    }

    @And("each alert should contain a link to the original product website")
    public void alertContainsLink() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        for (WebElement webElement : alertsList) {
            String link = webElement.findElement(By.tagName("a")).getAttribute("href");
            assert (link != null);
        }
    }
    //endregion

    //region Alert Layout

    @Given("I am an administrator of the website and I upload more than 5 alerts")
    public void iAmAdminAndUploadOver5Alerts() {
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();
        apiRequest = new ApiRequests();

        String mockJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";

        for(int i = 0 ; i < 7 ; i++) apiRequest.postAlert(mockJson);
    }

    @Then("I should see 5 alerts")
    public void iSeeFiveAlerts() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        Assertions.assertEquals(5, alertsList.size());
    }

    //endregion

    //region Icon Check

    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAdminAndUploadAlertType(int alertType) {
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();
        apiRequest = new ApiRequests();
        String mockJson = "{\"alertType\":"+alertType+",\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";

        apiRequest.postAlert(mockJson);
    }

    @Then("I should see 1 alerts")
    public void iSeeOneAlerts() {
        List<WebElement> alertsList = driver.findElements(By.tagName("table"));

        Assertions.assertEquals(1, alertsList.size());
    }

    @And("the icon displayed should be {string}")
    public void iconIsAccurate(String iconFileName) {
        WebElement alert = driver.findElement(By.tagName("table"));
        String icon = alert.findElement(By.tagName("h4")).findElement(By.tagName("img")).getAttribute("src");

        apiRequest.deleteAlerts();

        Assertions.assertTrue(icon.contains(iconFileName));
    }

    //endregion

    @After
    public void closeDriver(){
        if(apiRequest != null) {
            apiRequest.deleteAlerts();
        }
        driver.close();
    }
}
