package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/",".jsp");
	
	}
	   
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/resouces/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/resouces/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/resouces/fonts/");
		registry.addResourceHandler("/img/**").addResourceLocations("/resouces/img/");
	}
	
	
}