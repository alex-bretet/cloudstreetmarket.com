package edu.zipcloud.cloudstreetmarket.api.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@ComponentScan(value={"edu.zipcloud.cloudstreetmarket.api","edu.zipcloud.cloudstreetmarket.core","edu.zipcloud.cloudstreetmarket.shared"})
@EnableSwagger //Loads the spring beans required by the framework
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@EnableEntityLinks //Loads Hateoas EntityLinks bea
@PropertySource(value={"file:${user.home}/app/cloudstreetmarket.properties"})
@EnableRabbit
@EnableAsync
public class AnnotationConfig {

   private SpringSwaggerConfig springSwaggerConfig;

   /**
    * Required to autowire SpringSwaggerConfig
    */
   @Autowired
   public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
      this.springSwaggerConfig = springSwaggerConfig;
   }

   /**
    * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework - allowing for multiple
    * swagger groups i.e. same code base multiple swagger resource listings.
    */
   @Bean
   public SwaggerSpringMvcPlugin customImplementation(){
      return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
              .includePatterns(".*").apiInfo(new ApiInfo(
            		  "Cloudstreet Market / Swagger UI",
            		  "The Rest API developed with Spring MVC Cookbook [PACKT]",
            		  "",
            		  "alex.bretet@gmail.com",
            		  "GNU GENERAL PUBLIC LICENSE v2",
            		  "http://www.gnu.org/licenses/gpl-2.0.html"));
   }
}