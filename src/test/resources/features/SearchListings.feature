@demo
Feature:
  Background:
    Given the user is on the 4Stay home page

   Scenario Outline:
     When the user searches an  "<area>"
     And the user changes the price range to $50 - $1900
     Then the user should not see any results over that price range

     Examples:
     |area|
     |Arlington, VA, USA|


  Scenario Outline: Search a listing
    When the user searches an  "<area>"
    Then the guest should be able to search for listings

    Examples:
    |area|
    |Arlington, VA, USA|


