package chrome.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.google.gson.Gson;
import api.requests.ApiRequests;

import java.util.LinkedList;
import java.util.List;

public class WebScraper {
    WebDriver driver;
    final int alertType = 6;
    final String postedBy = "afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4";
    String searchItem = "phones";

    //region Scraper

    public String Scraper(int productAmount){
        //Setting up Chrome Driver
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
        driver = new ChromeDriver();
        ApiRequests api = new ApiRequests();
        String isPosted = "All alerts have been posted";

        openKlikks(driver);

        search(driver, searchItem);

        List<String> productList = getProductLinks(driver, productAmount);
        List<Alert> alertsToPost = getAlertList(productList, productAmount);

        // Converting Alert objects to json and posting them
        for(int i = 0 ; i < productAmount ; i++){
            String jsonAlert = alertToJson(alertsToPost.get(i));
            int responseCode = api.postAlert(jsonAlert);
            // If any of the alerts fail to be posted, the appropriate string is returned
            if(responseCode != 201) isPosted = "Not all alerts have been successfully posted";
        }

        return isPosted;
    }

    //endregion

    //region Scraper Functions

    public List<Alert> getAlertList(List<String> productList, int productAmount){

        List<Alert> alertsToPost = new LinkedList<Alert>();

        // populating object and adding it to alert list
        for(int i = 0 ; i < productAmount ; i++){
            selectProduct(driver, productList.get(i));

            String heading = getProductHeading(driver);
            String description = getProductDescription(driver);
            String url = productList.get(i);
            String imageUrl = getProductImageUrl(driver);
            int priceInCents = getProductPriceInCents(driver);

            alertsToPost.add(new Alert(alertType, heading, description, url, imageUrl,postedBy, priceInCents));
        }

        return alertsToPost;
    }

    public String alertToJson(Alert alert){
        String jsonAlert = new Gson().toJson(alert);

        return jsonAlert;
    }

    public void search(WebDriver driver, String searchItem){
        WebElement searchField = driver.findElement(By.className("header-search-button"));
        searchField.sendKeys(searchItem);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    public List<String> getProductLinks(WebDriver driver, int linkAmount){
        List<WebElement> productLinkElements = driver.findElements(By.className("product_card_grid_body_mobile"));
        List<String> productLinkList = new LinkedList<String>();
        for (int i = 0 ; i < linkAmount ; i++){
            productLinkList.add(i, productLinkElements.get(i).findElement(By.tagName("a")).getAttribute("href"));
        }

        return productLinkList;
    }

    public void selectProduct(WebDriver driver, String productLink) {
        driver.get(productLink);
    }

    public String getProductHeading(WebDriver driver){
        String heading = null;
        try{ heading = driver.findElement(By.className("product_detail_mobile_title")).findElement(By.tagName("b")).getAttribute("innerHTML"); }
        catch (Exception e) { heading = "Header could not be found" ; }

        return heading;
    }

    public String getProductDescription(WebDriver driver){
        String description = null;
        // Inconsistent use of naming in website required a try catch to be utilised
        try {
            description = driver.findElement(By.className("product_detail_summary_summary")).findElement(By.tagName("div")).findElement(By.tagName("b")).getAttribute("innerHTML");
        }
        catch(Exception e){
            try { description = driver.findElement(By.className("product_detail_summary_summary")).findElement(By.tagName("b")).getAttribute("innerHTML");}
            catch (Exception a) { description = "Description could not be found" ; }
        }
        return description;
    }

    public String getProductImageUrl(WebDriver driver){
        String imageUrl;
        try { imageUrl = driver.findElement(By.className("carousel-inner")).findElement(By.tagName("img")).getAttribute("src"); }
        catch (Exception e) { imageUrl = "Image Url could not be found" ; }

        return imageUrl;
    }

    public int getProductPriceInCents(WebDriver driver){
        int finalPrice = 0;
        try {
            String price = driver.findElement(By.className("product_detail_mobile_price")).findElement(By.tagName("b")).getAttribute("innerHTML");
            // processing the returned value in order to obtain the price of type integer
            String[] priceSplit = price.split("€");
            String[] priceSplit2 = priceSplit[1].split("&");
            String[] priceInCents = priceSplit2[0].split("\\.");
            finalPrice = Integer.parseInt(priceInCents[0] + priceInCents[1]);
        }
        catch (Exception e) { finalPrice = 0; }

        return finalPrice;
    }

    public void openKlikks(WebDriver driver) {
        driver.get("https://www.klikk.com.mt/");
    }

    public void teardown() {
        driver.quit();
    }

    //endregion

}
