Feature: Buscar en Google

  Scenario: Buscar información usando DuckDuckGo
    Given que estoy en la página de DuckDuckGo
    When busco "Playwright Java"
    Then los resultados deben contener "Playwright"
