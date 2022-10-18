package com.practise.step_definitions;

import com.practise.pages.WikiResultPage;
import com.practise.pages.WikiSearchPage;
import com.practise.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class wiki_StepDefinitions {

    WikiSearchPage wikiSearchPage=new WikiSearchPage();
    WikiResultPage wikiResultPage=new WikiResultPage();

    @Given("user is on Wiki search page")
    public void user_is_on_wiki_search_page() {
        Driver.getDriver().get("https://www.wikipedia.org");
    }

    @When("user click the language dropdown menu and select {string} as a search language")
    public void userClickTheLanguageDropdownMenuAndSelectAsASearchLanguage(String str) {
        wikiSearchPage.selectFromDropdownOption(str);
    }

    @Then("user should see {string} of language on the right side")
    public void user_should_see_of_language_on_the_right_side(String string) {
        Assert.assertEquals(string,wikiSearchPage.languageLabel.getText().toUpperCase());
    }

    @When("user types {string} a name of the list to search")
    public void user_types_a_name_of_the_list_to_search(String string) {
        wikiSearchPage.searchBox.sendKeys(string);
    }

    @When("user click search icon")
    public void user_click_search_icon() {
        wikiSearchPage.searchButton.click();
    }

    @Then("User sees {string} is in the main header")
    public void user_sees_is_in_the_main_header(String string) {
        Assert.assertEquals(string,wikiResultPage.mainHeader.getText());
    }
    @Then("User sees {string} is above of the picture")
    public void user_sees_is_above_of_the_picture(String string) {
        Assert.assertEquals(string,wikiResultPage.pictureTitle.getText());
    }

    @When("user write {string} in search bar")
    public void user_write_in_search_bar(String string) {
        wikiSearchPage.searchBox.sendKeys(string);
    }

    @Then("result page should be seen selected {string}")
    public void result_page_should_be_seen_selected(String string) {
        String xpathOfElement="//html[@lang='"+string.toLowerCase()+"']";
        String actualResult= Driver.getDriver().findElement(By.xpath(xpathOfElement)).getAttribute("lang").toUpperCase();
        Assert.assertEquals(string,actualResult);
    }



}
