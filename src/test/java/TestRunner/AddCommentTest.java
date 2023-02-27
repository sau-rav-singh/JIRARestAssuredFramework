package TestRunner;

import org.testng.annotations.Listeners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Listeners({listeners.ExtentReportConfigListener.class,listeners.MySuiteListener.class})

@CucumberOptions(features = "src/test/java/features/addComment.feature", plugin = { "pretty",
		"json:target/jsonReports/cucumber-report.json" }, glue = { "stepDefinitions" })

public class AddCommentTest extends AbstractTestNGCucumberTests {
}