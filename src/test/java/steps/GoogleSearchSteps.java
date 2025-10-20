package steps;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import io.cucumber.java.After;
import org.junit.Assert;
import pages.DuckDuckGoPage;

public class GoogleSearchSteps {

    private static Playwright playwright;
    private static Browser browser;
    private static Page page;
    private DuckDuckGoPage duckPage;

    @Given("que estoy en la página de DuckDuckGo")
    public void abrirDuckDuckGo() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
        page = browser.newPage();
        duckPage = new DuckDuckGoPage(page);
        duckPage.abrir();

        Assert.assertTrue("No se cargó DuckDuckGo", page.title().contains("DuckDuckGo"));
    }

    @When("busco {string}")
    public void buscar(String termino) {
        duckPage.buscar(termino); // Llama al método del POM
    }

    @Then("los resultados deben contener {string}")
    public void verificarResultados(String textoEsperado) {
        Assert.assertTrue("No se encontró el texto esperado: " + textoEsperado,
                duckPage.resultadosContienen(textoEsperado)); // Llama al método del POM
    }

    @After
    public void cerrar() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
