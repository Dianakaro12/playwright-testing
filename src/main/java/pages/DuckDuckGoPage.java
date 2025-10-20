package pages;

import com.microsoft.playwright.*;

import java.util.List;

public class DuckDuckGoPage {

    private Page page;

    public DuckDuckGoPage(Page page) {
        this.page = page;
    }

    // Método para abrir la página
    public void abrir() {
        page.navigate("https://duckduckgo.com/");
    }

    // Método para buscar un término
    public void buscar(String termino) {
        page.locator("input[name='q']").fill(termino);
        page.locator("input[name='q']").press("Enter");
        page.waitForSelector("li[data-layout='organic']");
    }

    // Método para verificar que los resultados contienen el texto esperado
    public boolean resultadosContienen(String textoEsperado) {
        List<String> resultados = page.locator("li[data-layout='organic']").allInnerTexts();
        return resultados.stream()
                .anyMatch(text -> text.toLowerCase().contains(textoEsperado.toLowerCase()));
    }
}
