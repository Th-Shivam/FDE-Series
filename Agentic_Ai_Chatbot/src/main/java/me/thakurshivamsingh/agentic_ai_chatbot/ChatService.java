package me.thakurshivamsingh.agentic_ai_chatbot;

import me.thakurshivamsingh.agentic_ai_chatbot.aitools.CalculatorTool;
import me.thakurshivamsingh.agentic_ai_chatbot.aitools.CurrencyExchangeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final CalculatorTool calculatorTool;
//    private WeatherTool weatherTool;
    private final CurrencyExchangeTool currencyExchangeTool;

    public ChatService(
            ChatClient.Builder builder,
            CalculatorTool calculatorTool,
            ChatMemory chatMemory,
            CurrencyExchangeTool currencyExchangeTool
    ) {

        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory)
                        .build();

        this.chatClient = builder
                .defaultAdvisors(memoryAdvisor)
                .build();

        this.calculatorTool = calculatorTool;
        this.currencyExchangeTool = currencyExchangeTool;
    }

    private static final String SYSTEM_PROMPT = """
            You are a helpful AI assistant with access to external tools.

            Follow these rules:
            1. For arithmetic calculations, ALWAYS use the calculator tool.
            2. Always use calculator tool for even trivial calculation.
            3. For currency conversion or exchange rates, ALWAYS use the convertCurrency tool.
            4. You may call multiple tools when solving a multi-step request.
            5. After receiving tool results, explain the answer naturally.
            6. Never invent current weather or exchange-rate information.
            """;
    public String chat(String message, String conversationId) {

        return chatClient.prompt()
                .tools(calculatorTool, currencyExchangeTool)
                .system(SYSTEM_PROMPT)
                .user(message)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .call()
                .content();
    }
}