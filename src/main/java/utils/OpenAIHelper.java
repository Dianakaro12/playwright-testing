package utils;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.*;
import java.util.List;

public class OpenAIHelper {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final OpenAiService service = new OpenAiService(API_KEY);

    public static String explicarError(String errorMensaje) {
        ChatMessage userMessage = new ChatMessage(
                "user",
                "Eres un experto en Playwright, Cucumber y automatización. Explica brevemente en español este error:\n\n"
                        + errorMensaje
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(userMessage))
                .temperature(0.4)
                .build();

        ChatCompletionResult result = service.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
