package com.bh.timetracker.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.fasterxml.jackson.annotation.JsonInclude;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
public class AppConfiguration {

	@Value("${app.cors.host}")
	private String corsHost;
    @Bean
    public MappingJackson2HttpMessageConverter customConverters() {
    
    	
    	  MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

          ObjectMapper mapper = new ObjectMapper();
          //Registering Hibernate5Module to support lazy objects
          mapper.registerModule(new Hibernate5Module());
          mapper.setSerializationInclusion(Include.NON_NULL);
          messageConverter.setObjectMapper(mapper);
          return messageConverter;

    }
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }
   
    @Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
    	
    	System.out.println("AppConfiguration.corsFilter()"+corsHost+"#");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(corsHost);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		
		bean.setOrder(0);
		return bean;
	}

}