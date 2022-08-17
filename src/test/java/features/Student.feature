Feature: Student controller Sprint
  Background: a user


  Scenario: verify that student details created.
    Given create a student
    When updating the student
    Then the student is updated

  Scenario: verify that student data is updated
      When creating a student
      And Updating the student
      Then  student is updated

  Scenario:  verify that Student details getting.
       When creating a student
        Then Getting Student data

  Scenario: verify that student student name couldn't blank.
    When  create a student
   And student without name
    Then Error message Name cannot be blank

  Scenario: verify that student details without age
      Given create a student
      And student details without age
      Then Error message Age cannot be blank

  Scenario: verify that student details without email.
       Given create a student
       And student details without email
       Then Error message Email cannot be blank

  Scenario: verify that student details without id.
    Given create a student
    And student details without id
    Then Error message internal server error

Scenario: verify that path param is required
  Given create a student
  And enter student without path
  Then throw an method error

  Scenario: verify student details is deleted
     When creating a student
     Then Delete student details

    Scenario: Verify student deteails it requires path parem
      When create a student
      And delete student without parm
      Then throw an method error
