### NOT FINISHED YET
---

# Testing Wikipedia Pages
---


### In this project, I test the search functionality and verify the languages, title, and header features of the page in the "https://www.wikipedia.org" site


### Using Tools and Libraries
- IntellJ as a IDE,
- Java as a programming language,
- Maven as an automation tool,
- Cucumber BDD framework for the smart logic and non-technical team members,
- JUnit as a testing tool,
- POM design pattern,
- Singleton desing pattern, 


### Installation

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
- SLF4J Failed
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
	- creating **resources** folder under test folder
	- creating **features** folder under resources folder for features files for the project
	- creating **com.practice** folder under the java folder
	- creating **pages, **runners, step_definitions,** and **utilities** folders under the practice folder

5. Creating ***CukesRunner.java*** file under the runner folder and adding runner features
```
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "html:target/cucumber-report.html",
        features = "src/test/resources/features",
        glue = "com/practise/step_definitions",
        dryRun = false,
        tags = ""
)
public class CukesRunner {
}
```

6. Creating ***ConfigurationReader.java*** file under utilities to read *configuration.properties* files
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

7. Creating ***driver.java*** file to return same driver instance when we call it to apply Singleton design pattern
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

8. Creating ***Hooks.java*** to add pre&post conditions to each scenario and each step, and adding some steps in it to take screenshots if a scenario will be fail
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

9. Creating ***wikiSearch.feature*** file under the feature folder and writing scenarios with **Gherkin** language
```
Feature: Wikipedia search functionality
  As a user, when I am on the Wiki search page
  I should be able to search whatever I want and see relevant information

  Background:
    Given user is on Wiki search page

  Scenario Outline: Language dropdown menu verification on the right side of the search bar
    When user click the language dropdown menu
    And user select "<Language>" as a search language
    Then user should see "<Shorten>" of language on the right side

    Examples:
      | Language | Shorten |
      | English  | EN      |
      | Deutsch  | DE      |
      | Français | FR      |
      | Türkçe   | TR      |


 Scenario Outline: Search fuctionality verification
    When user types "<searchValue>" a name of the list to search
    And user click search icon
    Then User sees "<expectedTitle>" is in the wiki title
    And User sees "<expectedMainHeader>" is in the main header
    
    Examples: search values 
      | searchValue       | expectedTitle     | expectedMainHeader |
      | Steve Jobs        | Steve Jobs        | Steve Jobs         |
      | Cristiano Ronaldo | Cristiano Ronaldo | Cristiano Ronaldo  |
      | Bob Marley        | Bob Marley        | Bob Marley         |
      | Chuck Norris      | Chuck Norris      | Chuck Norris       |
      | Antony Hopkins    | Antony Hopkins    | Antony Hopkins     |

  Scenario Outline: Searching with different language verification
    When user click the language dropdown menu
    And user select "<Search Language>" as a search language
    And user write "Steve Jobs" in search bar
    And user click search icon
    Then result page should be seen selected "<Result Language>"

  Examples:
    | Search Language | Result Language |
    | English         | EN              |
    | Deutsch         | DE              |
    | Français        | FR              |
    | Türkçe          | TR              |

```

10. Creating ***wikiSearch_StepDefinitions.java*** under the *step_definitions* folder and snippet steps from *wikiSearch.feature* file  
```

```
