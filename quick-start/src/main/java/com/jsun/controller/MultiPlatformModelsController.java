package com.jsun.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.jsun.pojos.MultiPlatformModelOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MultiPlatformModelsController {

    Map<String, ChatModel> platforms = new HashMap<>();

    public MultiPlatformModelsController(DeepSeekChatModel deepSeekChatModel, DashScopeChatModel dashScopeChatModel) {
        platforms.put("deepseek", deepSeekChatModel);
        platforms.put("dashscope", dashScopeChatModel);
    }

    @GetMapping(value = "/chat", produces = "text/stream;charset=UTF-8")
    public Flux<String> chat(
            String message,
            MultiPlatformModelOptions options
    ) {
        String platform = options.getPlatform();
        ChatModel chatModel = platforms.get(platform);
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        ChatClient chatClient = builder.defaultOptions(ChatOptions.builder()
                        .temperature(options.getTemperature())
                        .model(options.getModel())
                        .build())
                .build();
        Flux<String> content = chatClient.prompt()
                .user(message)
                .stream().content();
        return content;
    }
}
