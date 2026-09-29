package me.thakurshivamsingh.agentic_ai_chatbot;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public String chat(String message){
        return "Message: " + message;
    }
}
