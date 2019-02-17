# e2e-persinio-tests
This is a test repository which tests the payroll section of Personio by implementing few test cases. I have selcted the payroll section 
as in my opinion it is one of the most vital business function areas inside the company.

# Prerequisites

Docker needs to be installed.
Maven needs to be installed
JDK needs to be installed

# Execution Steps

mvn clean verify

# Maven sure file execution Reporting
Maven Sure fire reports are generated inside target/surefire-reports folder

# Allure Reporting
Allure installtion instructions are present here https://docs.qameta.io/allure/#_installing_a_commandline
allure serve target/allure-results