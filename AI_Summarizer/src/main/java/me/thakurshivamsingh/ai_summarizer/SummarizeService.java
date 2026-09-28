package me.thakurshivamsingh.ai_summarizer;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SummarizeService {
    private ChatClient chatClient;

    public SummarizeService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String summarize(String ticket) {
        String output = chatClient.prompt()
                .user("Summarize this support ticket in 2 lines : \n\n" + ticket)
                .call()
                .content();

        return output;
    }
}
