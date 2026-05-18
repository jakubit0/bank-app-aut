Feature: Withdraw money from bank account

  Scenario: User can withdraw money
    Given user is logged in
    When user withdraws 30 PLN
    Then balance should be 70 PLN
    And transaction history should contain "Withdrawal: 30 PLN"

  Scenario: User cannot withdraw more money than balance
    Given user is logged in
    When user withdraws 150 PLN
    Then error message should be "Insufficient funds"

  Scenario: User can make multiple withdrawals
    Given user is logged in
    When user withdraws 10 PLN
    And user withdraws 20 PLN
    And user withdraws 30 PLN
    Then balance should be 40 PLN