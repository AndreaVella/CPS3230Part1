package test.Part1;

import chrome.scraper.WebScraper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import test.setup.driverSetup;
import chrome.scraper.Alert;
import api.requests.ApiRequests;

import java.util.List;

public class WebScraperTests {
    WebDriver driver;
    WebScraper scraper;
    driverSetup before;
    ApiRequests apiRequest;

    @BeforeEach
    public void setup() {
        before = new driverSetup();
        scraper = new WebScraper();
        apiRequest = new ApiRequests();
        // Setting up the driver to be used for the tests
        driver = before.setup();
    }

    @Test
    public void testOpenKlikks(){
        //Exercise
        scraper.openKlikks(driver);

        //Verify
        Assertions.assertEquals("Klikk Computers Store Malta", driver.getTitle());
    }

    @Test
    public void testSearch(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/";
        before.getRequiredLink(driver, requiredUrl);
        String searchTerm = "phone";

        //Exercise
        scraper.search(driver, searchTerm);

        //Verify
        Assertions.assertEquals(("Klikk Computers Store Malta - Search results for " + searchTerm), driver.getTitle());
    }

    @Test
    public void testGetProductLinks(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/shop?q=phones";
        int productAmount = 5;
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        List<String> productLinkList = scraper.getProductLinks(driver, productAmount);
        int listSize = productLinkList.size();

        //Verify
        Assertions.assertEquals(productAmount, listSize);
    }

    @Test
    public void testSelectProduct(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-";

        //Exercise
        scraper.selectProduct(driver, requiredUrl);

        //Verify
        Assertions.assertEquals(requiredUrl, driver.getCurrentUrl());
    }

    @Test
    public void testGetProductHeading(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String heading = scraper.getProductHeading(driver);

        //Verify
        Assertions.assertEquals("Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)", heading);
    }

    @Test
    public void testGetProductDescription(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String description = scraper.getProductDescription(driver);

        //Verify
        Assertions.assertEquals("Easy to use GSM phone for the elderly with comfort features and SOS-function.", description);
    }

    @Test
    public void testGetProductImageUrl(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String image = scraper.getProductImageUrl(driver);

        //Verify
        Assertions.assertEquals("https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png", image);
    }

    @Test
    public void testGetProductPriceInCents(){
        //Setup
        String requiredUrl = "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-";
        int expectedPrice = 5595;
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        int priceInCents = scraper.getProductPriceInCents(driver);

        //Verify
        Assertions.assertEquals(expectedPrice, priceInCents);
    }

    @Test
    public void testScarper(){
        //Setup
        int productAmount = 5;

        //Exercise
        String response = scraper.Scraper(productAmount);

        //Verify
        Assertions.assertEquals("All alerts have been posted", response);
    }

    @Test
    public void testAlertToJson(){
        //Setup
        String expectedJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";
        Alert mockAlert = new Alert(6, "Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)", "Easy to use GSM phone for the elderly with comfort features and SOS-function.", "https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-", "https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png", "afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4", 5595);

        //Exercise
        String jsonAlert = scraper.alertToJson(mockAlert);

        //Verify
        Assertions.assertEquals(expectedJson, jsonAlert);
    }

    @Test
    public void testPostAlert(){
        //Setup
        String mockJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";

        //Exercise
        int responseCode = apiRequest.postAlert(mockJson);

        //Verify
        Assertions.assertEquals(201, responseCode);
    }

    //region WebElement not found tests
    @Test
    public void testGetProductHeadingNotFound(){
        //Setup
        String requiredUrl = "https://www.webpagetest.org/blank.html";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String heading = scraper.getProductHeading(driver);

        //Verify
        Assertions.assertEquals("Header could not be found", heading);
    }

    @Test
    public void testGetProductDescriptionNotFound(){
        //Setup
        String requiredUrl = "https://www.webpagetest.org/blank.html";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String description = scraper.getProductDescription(driver);

        //Verify
        Assertions.assertEquals("Description could not be found", description);
    }

    @Test
    public void testGetProductImageUrlNotFound(){
        //Setup
        String requiredUrl = "https://www.webpagetest.org/blank.html";
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        String image = scraper.getProductImageUrl(driver);

        //Verify
        Assertions.assertEquals("Image Url could not be found", image);
    }

    @Test
    public void testGetProductPriceInCentsNotFound(){
        //Setup
        String requiredUrl = "https://www.webpagetest.org/blank.html";
        int expectedPrice = 0;
        before.getRequiredLink(driver, requiredUrl);

        //Exercise
        int priceInCents = scraper.getProductPriceInCents(driver);

        //Verify
        Assertions.assertEquals(expectedPrice, priceInCents);
    }
    //endregion

    @AfterEach
    public void teardown(){
        driver.quit();
    }
}
