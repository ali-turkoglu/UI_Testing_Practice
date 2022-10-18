Feature: Wikipedia search functionality
  As a user, when I am on the Wiki search page
  I should be able to search whatever I want and see relevant information

  Background:
    Given user is on Wiki search page


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


  Scenario Outline: Language dropdown menu verification on the right side of the search bar
    When user click the language dropdown menu
    And user select "<Language>" as a search language
    Then user should see "<Shorten>" of language on the right side

    Examples: Language values
      | Language | Shorten |
      | English  | EN      |
      | Deutsch  | DE      |
      | Français | FR      |
      | Türkçe   | TR      |


  Scenario Outline: Searching with different language verification
    When user click the language dropdown menu
    And user select "<Search Language>" as a search language
    And user write "Steve Jobs" in search bar
    And user click search icon
    Then result page should be seen selected "<Result Language>"

    Examples: Language values
    | Search Language | Result Language |
    | English         | EN              |
    | Deutsch         | DE              |
    | Français        | FR              |
    | Türkçe          | TR              |