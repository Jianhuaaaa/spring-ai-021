# Spring AI practice with DeepSeek API

### DeepSeek Models

- deepseek-chat - 模型指向DeepSeek-V3 - 不带思考
- deepseek-reasoner - 模型指向DeepSeek-R1 - 带思考

### DeepSeek开启深度思考

- 将模型指向DeepSeek-R1
- 将Output强制转换成DeepSeekAssistantMessage
- 通过getReasoningContent()方法拿到思考内容

```java
DeepSeekAssistantMessage assistantMessage = (DeepSeekAssistantMessage) r.getResult().getOutput();
String reasoningContent = assistantMessage.getReasoningContent();
```