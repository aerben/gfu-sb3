package digital.erben.mymodel.autoconfigure;

import digital.erben.MyModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MyModel.class)
@EnableConfigurationProperties(MyModelProperties.class)
public class MyModelAutoConfiguration {

    private final MyModelProperties properties;

    public MyModelAutoConfiguration(MyModelProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MyModel greeterConfig() {
        return new MyModel(
            properties.getFirstName(),
            properties.getLastName(),
            properties.getAge()
        );
    }
}
