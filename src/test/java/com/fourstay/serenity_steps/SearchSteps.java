package com.fourstay.serenity_steps;

import com.fourstay.pages.HomePage;
import com.fourstay.pages.ListingPage;
import com.fourstay.pages.ResultsPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.apache.log4j.Logger;


import java.util.Iterator;
import java.util.Set;



import static junit.framework.TestCase.assertTrue;

public class SearchSteps extends ScenarioSteps {

    public HomePage homePage;
    public ResultsPage resultsPage;
    public ListingPage listingPage;
    final static Logger logger = Logger.getLogger(SearchSteps.class);


    @Step
    public void searchAnAreaInListings(String area){
        getDriver().manage().window().maximize();
        homePage.switchFrame();
        homePage.mainSearchBox.sendKeys(area);
        homePage.getDropDownSearchAreas(1).click();
        homePage.mainSearchButton.click();
    }
    @Step
    public void checkListingsAgainstInformation(){
        resultsPage.switchFrame();
        logger.info("getText from getListingTitle index 1: "+resultsPage.getGetListingTitle(1).getText());
        resultsPage.getGetListingTitle(1).click();
        Set set = getDriver().getWindowHandles();
        String handle = getDriver().getWindowHandle();
        Iterator it = set.iterator();
        while (it.hasNext()){
            String s = it.next().toString();
            if(!s.equals(handle)){
                getDriver().switchTo().window(s);
                break;
            }
        }
    }
    @Step
    public void verifyListing(){
        listingPage.listingTitle.shouldContainText("Staging listing for test");//verify title
        listingPage.hostName.shouldContainText("Hostlo Dev");//verify host

        Assert.assertEquals(Integer.parseInt(listingPage.listingPrice.getText().trim()),2000);//verify price
        //verify amenities
        Assert.assertEquals("Gym", listingPage.propertyAmenities.get(0).getText().trim());
        Assert.assertEquals("Swimming pool", listingPage.propertyAmenities.get(1).getText().trim());
        Assert.assertEquals("Balcony", listingPage.propertyAmenities.get(2).getText().trim());
        Assert.assertEquals("Wifi", listingPage.propertyAmenities.get(3).getText().trim());
        //verify property rules
        Assert.assertEquals("Dogs okay",listingPage.propertyRules.get(0).getText().trim());
        Assert.assertEquals("No parties events",listingPage.propertyRules.get(1).getText().trim());
    }

    @Step
    public void changePrice() throws InterruptedException {
        resultsPage.switchFrame();
        Assert.assertTrue(resultsPage.getGetListingTitle(1).isDisplayed());
        resultsPage.priceRangeDropDown.click();
        Actions actions = new Actions(getDriver());
        actions.clickAndHold(resultsPage.priceRangeRightButton);
        actions.moveByOffset(-70,0);
        actions.release();
        actions.perform();
        resultsPage.applyButton.click();
        Thread.sleep(2000);
        boolean listing = false;
        try {
            logger.info("check to see getListTitle index 1 is displayed");
            listing = (!resultsPage.getGetListingTitle(1).isDisplayed());
        }catch (NoSuchElementException e){
            listing = true;
        }
        Assert.assertTrue(listing);

    }
}
