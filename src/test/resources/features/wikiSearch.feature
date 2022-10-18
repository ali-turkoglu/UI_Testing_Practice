Feature: Wikipedia search functionality
  As a user, when I am on the Wiki search page
  I should be able to search whatever I want and see relevant information

  Background:
    Given user is on Wiki search page


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


  Scenario Outline: Language dropdown menu verification on the right side of the search bar
    When user click the language dropdown menu and select "<Language>" as a search language
    Then user should see "<Shorten>" of language on the right side

    Examples: Language values
      | Language | Shorten |
      | English  | EN      |
      | Deutsch  | DE      |
      | Français | FR      |
      | Türkçe   | TR      |

@wip_ali
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