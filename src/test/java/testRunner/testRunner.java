package testRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		 features = "C:\\Users\\GitBoy\\eclipse-workspace\\nopCommerceBDD\\src\\test\\resources\\Features",
		 glue={"stepDefinitions"},
		 tags = "@DashboardPage")

public class testRunner {
}