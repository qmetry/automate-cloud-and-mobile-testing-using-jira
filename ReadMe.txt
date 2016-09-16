Steps to verify before running "mvn test":
- Depending upon your OS (Windows/Mac), please verify chromeDriverPath in your step definitions.
- Verify the apiKey, testRunName, platform, server in systemPropertyVariables in pom.xml.

Then you can run the project with "mvn test" and verify the issues created in Jira. Good luck. :)