package me.thakurshivamsingh.agentic_ai_chatbot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {

        return chatService.chat(
                request.message(),
                request.conversationId()
        );
    }

    public record ChatRequest(
            String message,
            String conversationId
    ) {}
}