package com.practise.step_definitions;

import com.practise.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class wikiSearch_StepDefinitions {

    @Given("user is on Wiki search page")
    public void user_is_on_wiki_search_page() {
        Driver.getDriver().get("https://www.wikipedia.org");
    }

    @When("user click the language dropdown menu")
    public void user_click_the_language_dropdown_menu() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("user select {string} as a search language")
    public void user_select_as_a_search_language(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("user should see {string} of language on the right side")
    public void user_should_see_of_language_on_the_right_side(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("user types {string} a name of the list to search")
    public void user_types_a_name_of_the_list_to_search(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("user click search icon")
    public void user_click_search_icon() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("User sees {string} is in the wiki title")
    public void user_sees_is_in_the_wiki_title(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("User sees {string} is in the main header")
    public void user_sees_is_in_the_main_header(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("user write {string} in search bar")
    public void user_write_in_search_bar(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("result page should be seen selected {string}")
    public void result_page_should_be_seen_selected(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}
