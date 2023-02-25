package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features/readWriteToExcel.feature", plugin = { "pretty",
		"json:target/jsonReports/cucumber-report.json" }, glue = { "stepDefinitions" })

public class ReadWriteToExcelTest extends AbstractTestNGCucumberTests {

}
