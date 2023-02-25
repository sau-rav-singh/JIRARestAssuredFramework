package TestRunner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Test
@CucumberOptions(features = "src/test/java/features/readWriteToExcel.feature", plugin = { "pretty",
		"json:target/jsonReports/cucumber-report.json" }, glue = { "stepDefinitions" })

public class ReadWriteToExcelTest extends AbstractTestNGCucumberTests {

}
