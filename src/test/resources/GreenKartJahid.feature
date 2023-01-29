@smoketest @KT001
Feature: Verify check out function in Green kart website
Scenario: Successfully add max and min price Item to cart and check out
Given user go to Green Kart home page
And user verify page title as "GreenKart - veg and fruits kart"
When user capture all price from the page in descending order
Then user add to cart max and min price item and capture total price
And user click on cart button then proceed to check out
Then user verify total price from previous page
And user click on place order
Then user choose country as "United States"
And user click on check box for agree to terms and conditions
And user click on the proceed button
Then user verify "Thank you, your order has been placed successfully" message