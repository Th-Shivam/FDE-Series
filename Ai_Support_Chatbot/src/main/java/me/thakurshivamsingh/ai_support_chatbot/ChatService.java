package me.thakurshivamsingh.ai_support_chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

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
            You are a customer-support executive for our
            Food ordering app named Lomato.
            
            Your job is to identify the customer's main
            problem and urgency. Answer them related to there query.
            
            Use professional language. If user has an issue,
            use words like I understand your frustration,
            I am really sorry for your trouble etc.
            
            Do not answer any other question which is not
            related to Ordering Food query, refund query,
            order tracking status query or company policy query.
            
            Reply in only 1-2 line , not more than that.
            """;

    public String chat(String message) {

        // USER role
        history.add(new UserMessage(message));

        // SYSTEM + Conversation History
        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .call()
                .content();

        // ASSISTANT role
        history.add(new AssistantMessage(response));

        return response;
    }

    public void clearHistory() {
        history.clear();
    }
}
