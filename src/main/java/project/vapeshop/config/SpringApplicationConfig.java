package project.vapeshop.config;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.MessageInterpolator;

@Configuration
@ComponentScan("project.vapeshop")
@EnableTransactionManagement
@EnableWebMvc
@EnableAspectJAutoProxy
@PropertySource("classpath:aplication.properties")
public class SpringApplicationConfig implements WebMvcConfigurer {

    public SpringApplicationConfig() {
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public MessageInterpolator messageInterpolator() {
        return new ParameterMessageInterpolator();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }


}
