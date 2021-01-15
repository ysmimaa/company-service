Feature: Display company service
  Scenario: As a user when calling the company service
            then a list of companies is returned
    Given service company
    When user does a call to the list of companies
    Then list with all companies is returned

  Scenario: As a user when calling the company service
            and the result is empty
            then no content exception is thrown
    Given service company
    When user does a call to the list of companies
    And the returned list is empty
    Then an no content exception is thrown