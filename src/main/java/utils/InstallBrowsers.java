package utils;

import com.microsoft.playwright.*;

public class InstallBrowsers {
    public static void main(String[] args) {
        System.out.println(" Instalando navegadores de Playwright...");
        Playwright.create();  // Esto descarga navegadores si no están instalados
        System.out.println("✅ Navegadores instalados correctamente.");
    }
}

