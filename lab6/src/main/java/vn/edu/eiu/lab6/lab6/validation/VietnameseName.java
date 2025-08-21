package vn.edu.eiu.lab6.lab6.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VietnameseNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VietnameseName {
    String message() default "Each word in the name must start with an uppercase letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}