
# Testing Wikipedia

In this project, I create whole steps from scratch to test the search functionality and verify the languages, title, and header features of the page in the "https://www.wikipedia.org" site. 

We can see how to create simple test steps, and how to use some libraries here. We can also see the implementation of Singleton and POM(Page Object Model) design patterns.

## Using Tools and Libraries
- IntellJ as a IDE,
- Java as a programming language,
- Selenium WebDriver,
- Maven as an automation tool,
- Cucumber BDD framework for the smart logic and non-technical team members,
- JUnit as a testing tool,
- POM design pattern,
- Singleton desing pattern, 

## Installation and Implementation

1. Creating new **Maven Project** from **IntellJ**  

2. Adding **dependencies** into the *pom.xml* file from "https://mvnrepository.com/"
	
- Selenium Java
```
  <!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>3.141.59</version>
</dependency>
```

- WebDriverManager
```
<!-- https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager -->
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.3.0</version>
</dependency>
```

- Cucumber io
```
<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.8.1</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.8.1</version>
    <scope>test</scope>
</dependency>
```
- SLF4J Failed (This is not necessary)
```
<!--If you want to get rid of SLF4J Failed to load message from the console -->
<dependency>
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-simple</artifactId>
     <version>1.7.32</version>
</dependency>
```

3. Adding plugings from IntelliJ settings
	- Cucumber for java
	- Gherkin

4. Creating files and folders for project structure to apply POM design pattern
	- creating ***configuration.properties*** file under the root folder and adding `browser=chrome` to avoid hard coding
	- creating **resources** folder under **test** folder
	- creating **features** folder under **resources** folder for features files for the project
	- creating **com.practice** folder under the **java** folder
	- creating **pages, **runners, step_definitions,** and **utilities** folders under the **practice** folder

5. Creating ***CukesRunner.java*** file under the **runner** folder and adding runner features. When I was typing some steps, I used the @wip_ali tag above the scenarios to run only the scenario have this tag. 
```
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "html:target/cucumber-report.html",
        features = "src/test/resources/features",
        glue = "com/practise/step_definitions",
        dryRun = false,
        tags = "@wip_ali"
)
public class CukesRunner {
}
```

6. Creating ***ConfigurationReader.java*** file under **utilities** to read *configuration.properties* files
```
public class ConfigurationReader {

    private static Properties properties = new Properties();

    static {


        FileInputStream file = null;
        try {
            file = new FileInputStream("configuration.properties");
            properties.load(file);
            file.close();

        } catch (IOException e) {
            System.out.println("File not found in the ConfigurationReader class.");
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String keyword){
        return properties.getProperty(keyword);
    }
}
```

7. Creating ***driver.java*** file under the **utilities** folder to return same driver instance when we call it to apply Singleton design pattern
```
public class Driver {

    private Driver() {}

    private static WebDriver driver;
    // if we will use multiTread we use the below line
    // private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    public static WebDriver getDriver() {

        if (driver == null) {

            String browserType = ConfigurationReader.getProperty("browser");

            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
                    break;
            }
        }
        return driver;
    }
    
    public static void closeDriver(){        
        if (driver!=null) {
            driver.quit();
            driver=null;
        }
    }
}
```

8. Creating ***Hooks.java*** file under the **utilities** folder to add pre&post conditions to each scenario and each step, and adding some steps in it to take screenshots if a scenario will be fail
```
public class Hooks {
    
    @After // this annotation must be from cucumber not from JUnit
    public void teardownScenario(Scenario scenario){
        if (scenario.isFailed()){
            byte[] screenshot= ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());
        }
    }
}
```

9. Creating ***wikiSearch.feature*** file under the **features** folder and writing scenarios with **Gherkin** language
```
Feature: Wikipedia search functionality
  As a user, when I am on the Wiki search page
  I should be able to search whatever I want and see relevant information

  Background:
    Given user is on Wiki search page

  @wip_ali
  Scenario Outline: Search fuctionality verification
    When user types "<searchValue>" a name of the list to search
    And user click search icon
    Then User sees "<expectedHeader>" is in the main header
    And User sees "<pictureHeader>" is above of the picture

    Examples: search values
      | searchValue         | expectedHeader      | pictureHeader       |
      | Steve Jobs          | Steve Jobs          | Steve Jobs          |
      | Bill Gates          | Bill Gates          | Bill Gates          |
      | Friedrich Nietzsche | Friedrich Nietzsche | Friedrich Nietzsche |
      | Immanuel Kant       | Immanuel Kant       | Immanuel Kant       |
      | İsmet Özel          | İsmet Özel          | İsmet Özel          |

  @wip_ali
  Scenario Outline: Language dropdown menu verification on the right side of the search bar
    When user click the language dropdown menu and select "<Language>" as a search language
    Then user should see "<Shorten>" of language on the right side

    Examples: Language values
      | Language | Shorten |
      | English  | EN      |
      | Deutsch  | DE      |
      | Français | FR      |
      | Türkçe   | TR      |


  Scenario Outline: Searching with different language verification
    When user click the language dropdown menu and select "<Search Language>" as a search language
    And user write "Steve Jobs" in search bar
    And user click search icon
    Then result page should be seen selected "<Result Language>"

    Examples: Language values
      | Search Language | Result Language |
      | English         | EN              |
      | Deutsch         | DE              |
      | Français        | FR              |
      | Türkçe          | TR              |

```

10. Creating ***Wiki_StepDefinitions.java*** under the **step_definitions** folder and snippet steps from ***wikiSearch.feature*** file  
```
public class Wiki_StepDefinitions {

    @Given("user is on Wiki search page")
    public void user_is_on_wiki_search_page() {
        
    }

    @When("user click the language dropdown menu and select {string} as a search language")
    public void userClickTheLanguageDropdownMenuAndSelectAsASearchLanguage(String str) {
        
    }

    @Then("user should see {string} of language on the right side")
    public void user_should_see_of_language_on_the_right_side(String string) {
        
    }

    @When("user types {string} a name of the list to search")
    public void user_types_a_name_of_the_list_to_search(String string) {
        
    }

    @When("user click search icon")
    public void user_click_search_icon() {
        
    }

    @Then("User sees {string} is in the main header")
    public void user_sees_is_in_the_main_header(String string) {
        
    }
    @Then("User sees {string} is above of the picture")
    public void user_sees_is_above_of_the_picture(String string) {
        
    }

    @When("user write {string} in search bar")
    public void user_write_in_search_bar(String string) {

    }

    @Then("result page should be seen selected {string}")
    public void result_page_should_be_seen_selected(String string) {

    }
}
```

11. Creating ***WikiSearchPage.java*** under the **pages** folder and add related WebElements and methods in it.
```
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

    public static void selectFromDropdownOption(String str){

        Select select=new Select(Driver.getDriver().findElement(By.id("searchLanguage")));
        select.selectByVisibleText(str);
    }
}
```

12. Creating an object in **Wiki_StepDefinitions.java** to able to use WebElements and methods of WikiSearchPage

`WikiSearchPage wikiSearchPage=new WikiSearchPage();`

13. Creating ***WikiResultPage.java*** under the **pages** folder and add related WebElements and methods in it.
```
public class WikiResultPage {

    public WikiResultPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(className = "mw-page-title-main")
    public WebElement mainHeader;

    @FindBy(className = "infobox-above")
    public WebElement pictureTitle;
}
```

14. Creating an object in **Wiki_StepDefinitions.java** to able to use WebElements and methods of WikiResultPage

`WikiResultPage wikiResultPage=new WikiResultPage();`

15. After adding steps, latest version of ***Wiki_StepDefinitions.java*** is:
```
public class Wiki_StepDefinitions {

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
```
