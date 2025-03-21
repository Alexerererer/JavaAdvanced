package com.JavaLab.AdvJava.service;

import com.JavaLab.AdvJava.models.RztkGood;
import com.JavaLab.AdvJava.models.RztkGoodsRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//A parser service using Selenium and WebDrivers
@Service
public class ParserService {

    private static final Logger log = LogManager.getLogger(ParserService.class);
    @Autowired
    RztkGoodsRepository rztkGoodsRepository;

    public void parseAndSave(String url,List<Float> exchangeRates)
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // use headless mode
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);

            //Selection of needed elements via hardcoded cssSelectors; TODO: move them co config
            List<WebElement> titleElements = driver.findElements(By.cssSelector("span.goods-tile__title"));
            List<WebElement> priceElements = driver.findElements(By.cssSelector("span.goods-tile__price-value"));

            List<RztkGood> rztkGoods = new ArrayList<>();
            //Get the lowest number of elements between title and price(in case they mismatch)
            int count = Math.min(titleElements.size(), priceElements.size());

            //Read acquired elements texts and create a good object with their values and add them to DB
            for (int i = 0; i < count; i++) {
                String title = titleElements.get(i).getText();
                String priceText = priceElements.get(i).getText().replaceAll("[^\\d]", "");
                int priceUah = priceText.isEmpty() ? 0 : Integer.parseInt(priceText);

                RztkGood good = new RztkGood();
                good.setTitle(title);
                good.setPrice_uah(priceUah);
                good.setPrice_usd((int) Math.floor(priceUah / exchangeRates.get(1))); //Magick number is USD index

                rztkGoods.add(good);
            }

            rztkGoodsRepository.deleteAll();

            rztkGoodsRepository.saveAll(rztkGoods);

        } catch (Exception e) {
            log.error("Parsing not successful");
        } finally {
            driver.quit();
        }
    }
}
