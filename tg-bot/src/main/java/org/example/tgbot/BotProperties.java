package org.example.tgbot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.xml.transform.sax.SAXResult;

@Data
@Component
@ConfigurationProperties(prefix = "telegram.bot")
public class BotProperties {
    private String token;
    private String username;
}
