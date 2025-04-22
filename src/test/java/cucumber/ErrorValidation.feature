
@tag
Feature: Error validation
  I want to use this template for my feature file


  @ErrorValidation
  Scenario Outline: Error validation for user login
    Given I landed on Ecommerece Page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
      | name                 | password 	| 
      | razin.qa@example.com | Justdoit@1 |
