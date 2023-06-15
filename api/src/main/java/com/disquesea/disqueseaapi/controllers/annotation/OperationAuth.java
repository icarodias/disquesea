package com.disquesea.disqueseaapi.controllers.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Target({ METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Operation(security = @SecurityRequirement(name = "bearerAuth"))
public @interface OperationAuth {

    @AliasFor(annotation = Operation.class)
    String summary() default "";

}