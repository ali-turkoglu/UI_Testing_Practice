package com.practise.pages;

import com.practise.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class WikiSearchPage {

    //create constructor
    // initialize the driver instance and this class' instance using PageFactory.initElements
    public WikiSearchPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "searchInput")
    public WebElement searchBox;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchButton;

    @FindBy(id = "jsLangLabel")
    public WebElement languageLabel;

    public void selectFromDropdownOption(String str){
        Select select=new Select(Driver.getDriver().findElement(By.id("searchLanguage")));
        select.selectByVisibleText(str);
    }
}
