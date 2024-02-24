package az.azericard.servicecard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class EncryptionConfiguration {

    @Value("${app.security.encryptionPassword}")
    private String encryptionPassword;

    @Value("${app.security.encryptionSalt}")
    private String encryptionSalt;

    @Bean
    public TextEncryptor encryptor() {
        return Encryptors.text(encryptionPassword, encryptionSalt);
    }

}
