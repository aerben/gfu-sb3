package io.erben.greeter.autoconfigure;

import io.erben.MyModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MyModel.class)
public class MyModelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MyModel greeterConfig() {
        return new MyModel();
    }

}
