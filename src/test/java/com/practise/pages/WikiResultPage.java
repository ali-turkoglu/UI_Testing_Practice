package com.practise.pages;

import com.practise.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WikiResultPage {

    public WikiResultPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(className = "mw-page-title-main")
    public WebElement mainHeader;

    @FindBy(className = "infobox-above")
    public WebElement pictureTitle;

}
