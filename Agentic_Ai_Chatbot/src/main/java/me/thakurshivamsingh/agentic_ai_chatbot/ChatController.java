package me.thakurshivamsingh.agentic_ai_chatbot;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ChatController {

    private ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String sendMessage(@RequestBody String message){
        return chatService.chat(message);
    }
}
