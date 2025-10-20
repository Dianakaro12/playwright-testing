package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
        plugin = {
                "pretty",                        // Consola legible
                "html:target/cucumber-report.html", // Reporte visual
                "json:target/cucumber-report.json",  // Reporte para Jenkins/Xray
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  // genera los reportes Allure
        },
        monochrome = true
)
public class TestRunner {
}
