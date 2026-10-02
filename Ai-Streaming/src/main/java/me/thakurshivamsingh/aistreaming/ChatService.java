package me.thakurshivamsingh.aistreaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final List<Message> history = new ArrayList<>();

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    private static final String SYSTEM_PROMPT = """
            You are a helpful AI assistant.
            Answer clearly and accurately.
            """;

    public Flux<String> chat(String message) {

        // Save user message
        history.add(new UserMessage(message));

        // Store complete response while streaming
        StringBuilder fullResponse = new StringBuilder();

        return chatClient
                .prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .stream()
                .content()

                // Collect streamed chunks
                .doOnNext(fullResponse::append)

                // Save complete AI response in history
                .doOnComplete(() ->
                        history.add(
                                new AssistantMessage(
                                        fullResponse.toString()
                                )
                        )
                );
    }
}