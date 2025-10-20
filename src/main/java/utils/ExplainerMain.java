package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ExplainerMain {
    public static void main(String[] args) throws Exception {
        // Leer el archivo de logs de errores o capturas de test fallidos
        String errores = new String(Files.readAllBytes(Paths.get("target/errors.txt")));

        if (!errores.isEmpty()) {
            String explicacion = OpenAIHelper.explicarError(errores);
            System.out.println(" Explicación IA de los errores:\n" + explicacion);

            // Opcional: guardar explicación en un archivo para Xray
            Files.write(Paths.get("target/errors_explanation.txt"), explicacion.getBytes());
        }
    }
}

