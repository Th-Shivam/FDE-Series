package me.thakurshivamsingh.ai_summarizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SummarizeController {
    @Autowired
    private SummarizeService summarizeService;

    @PostMapping("/summarize")
    public String summarize(@RequestBody String ticket) {
        return summarizeService.summarize(ticket);
    }
}
