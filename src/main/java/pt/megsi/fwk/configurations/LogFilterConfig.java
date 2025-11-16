package pt.megsi.fwk.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import pt.megsi.fwk.filters.LogFilter;

@Configuration
public class LogFilterConfig {
    @Bean
    public FilterRegistrationBean<LogFilter> responseCaptureFilter() {
        FilterRegistrationBean<LogFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registration.setEnabled(true);

        return registration;
    }
}
