package com.jsun;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class TestDeepseek {

    @Value("${spring.ai.deepseek.api-key}")
    private String apiKey;

    @Test
    public void testDeepseek(@Autowired DeepSeekChatModel deepSeekChatModel) {
        System.out.println("API Key=" + apiKey);
        // String response = deepSeekChatModel.call("Hi, how are you?");
        Flux<String> stream = deepSeekChatModel.stream("写一首描绘秋天的诗，要求要有意境和文采。");
        stream.toIterable()
                .forEach(System.out::println);
    }

    @Test
    public void testDeekseekOptions(@Autowired DeepSeekChatModel chatModel) {
        DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                // 0-2 double value, used to optimize model output.
                .temperature(1.9d)
                .model("deepseek-chat")
                .build();
        ChatResponse chatResponse = chatModel.call(new Prompt("写一句描绘秋天的诗，要求要有意境和文采。", options));
        System.out.println(chatResponse.getResult()
                .getOutput()
                .getText());
    }

    @Test
    public void testDeepseekReasoningContent(@Autowired DeepSeekChatModel chatModel) {
        Flux<ChatResponse> chatResponse = chatModel.stream(new Prompt("香蕉为什么是弯的"));
        chatResponse.toIterable().forEach(r -> {
            DeepSeekAssistantMessage assistantMessage = (DeepSeekAssistantMessage) r.getResult().getOutput();
            String reasoningContent = assistantMessage.getReasoningContent();
            if (reasoningContent != null) {
                System.out.print(reasoningContent);
            }
        });
        chatResponse.toIterable().forEach(r -> {
            DeepSeekAssistantMessage assistantMessage = (DeepSeekAssistantMessage) r.getResult().getOutput();
            String text = assistantMessage.getText();
            if (text != null)
                System.out.print(text);
        });
    }
}
