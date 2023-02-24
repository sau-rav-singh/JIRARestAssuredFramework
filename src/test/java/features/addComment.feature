Feature: Adding Comment to Jira Issues

  Scenario Outline: Add Comment to Jira Bug with Valid Data
    Given A Workbook named "addCommentToBug" and sheetname as "Sheet1" and Row number as <RowNumber> is read
    Then Create the addCommentToBug payload
    When "AddComment" request is sent with the "Post" HTTP method on IssueID as "<ISSUEID>"
    Then Validate that the response status code is "201"
    And Validate the following fields from the response:
      | Field                       | Value                         |
      | Display Name                | <DISPLAY_NAME>                |
      | Comment                     | <COMMENT>                     |
      | Update Author Email Address | <UPDATE_AUTHOR_EMAIL_ADDRESS> |

    Examples: 
      | RowNumber | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
      |         1 | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
      |         2 | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
      |         3 | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
#
  #Scenario Outline: Add Comment to Jira Bug with Invalid Data
    #Given Row number is <RowNumber>
    #Then Create the addCommentToBug payload
    #When the "AddComment" request is sent with the "Post" HTTP method on IssueID "<ISSUEID>"
    #Then the response status code should not be "201"
#
    #Examples: 
      #| RowNumber | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
      #|         4 | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
      #|         5 | ISSUEID | DISPLAY_NAME | UPDATE_AUTHOR_EMAIL_ADDRESS | COMMENT |
#
  #Scenario: Add Comment to Non-Existing Jira Bug
    #Given an Excel sheet with name "addCommentToBug" and sub-sheet name "Sheet1" is read
    #And Row number is 6
    #Then Create the addCommentToBug payload
    #When the "AddComment" request is sent with the "Post" HTTP method on a non-existing IssueID
    #Then the response status code should be "404"
