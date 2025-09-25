package com.jsun;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class TestChatClient {
    @Test
    public void testChatClient(@Autowired ChatClient.Builder checkClientBuilder) {
        ChatClient chatClient = checkClientBuilder.build();
        String content = chatClient.prompt()
                .user("Hello")
                .call()
                .content();
        System.out.println(content);
    }

    @Test
    public void testChatClientStream(@Autowired ChatClient.Builder checkClientBuilder) {
        ChatClient chatClient = checkClientBuilder.build();
        Flux<String> content = chatClient.prompt()
                .user("What's the weather today in Shanghai China.")
                .stream()
                .content();
        content.toIterable().forEach(System.out::print);
    }

    @Test
    public void testMultiChatModels(@Autowired DeepSeekChatModel deepSeekChatModel) {
        ChatClient chatClient = ChatClient.builder(deepSeekChatModel).build();
        String content = chatClient.prompt()
                .user("Good morning")
                .call()
                .content();
        System.out.println(content);
    }
}
