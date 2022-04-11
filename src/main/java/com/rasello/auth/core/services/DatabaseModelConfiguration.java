package com.rasello.auth.core.services;

import com.rasello.auth.core.services.dto.DbModule;
import com.rasello.auth.core.services.entity.Forms;
import com.rasello.auth.core.services.entity.Menus;
import com.rasello.auth.repository.FormRepository;
import org.modelmapper.ModelMapper;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;


//@Configuration
public class DatabaseModelConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Autowired
//    List<JpaRepository> formRepository;


    public static HashMap<String, DbModule> fetchEntityMappings(){
//        TODO: mapping registration should be dynamic through annotation.
        HashMap <String, DbModule>   configurations = new HashMap<String,DbModule>();
//        configurations.put("forms", DbModule.builder().entity(Forms.class).repository(formRepository).build());
        configurations.put("menus", DbModule.builder().entity(Menus.class).build());
        return configurations;
    }

    @Bean
    public OpenApiCustomiser schemaCustomizer(){
        return openApi -> {};
//        TODO: loop throughout schema definition may be even create a rest resource according to the schema.

//        ArrayList<Class> list = new ArrayList();
//        list.add(TestDto.class);
//        list.add(RandomDto.class);
//        return openApi -> list.stream().forEach(item -> {
//            ResolvedSchema resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(item));
//            openApi.schema(resolvedSchema.schema.getName(),resolvedSchema.schema);
//        });
    }
}
