Feature: Save new company

  Scenario: As a user when i provide a new company to be saved, the company will be added successfully
    Given company to be added
    When click on the button save
    Then company will be added successfully

  Scenario: As a user when i provide an invalid company, an invalid param exception must be thrown
    Given invalid company to be added
    When click on the button save
    Then invalid param exception must be thrown

