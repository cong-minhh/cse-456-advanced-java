package vn.edu.eiu.lab6.lab6.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VietnameseNameValidator implements ConstraintValidator<VietnameseName, String> {
    
    private static final String VIETNAMESE_NAME_PATTERN = 
        "^[AÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴBCDĐEÈÉẸẺẼÊỀẾỆỂỄFGHIÌÍỊỈĨJKLMNOÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠPQRSTUÙÚỤỦŨƯỪỨỰỬỮVWXYỲÝỴỶỸZ]" +
        "[aàáạảãâầấậẩẫăằắặẳẵbcdđeèéẹẻẽêềếệểễfghiìíịỉĩjklmnoòóọỏõôồốộổỗơờớợởỡpqrstuùúụủũưừứựửữvwxyỳýỵỷỹz]*" +
        "(?:\\s[AÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴBCDĐEÈÉẸẺẼÊỀẾỆỂỄFGHIÌÍỊỈĨJKLMNOÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠPQRSTUÙÚỤỦŨƯỪỨỰỬỮVWXYỲÝỴỶỸZ]" +
        "[aàáạảãâầấậẩẫăằắặẳẵbcdđeèéẹẻẽêềếệểễfghiìíịỉĩjklmnoòóọỏõôồốộổỗơờớợởỡpqrstuùúụủũưừứựửữvwxyỳýỵỷỹz]*)*$";
    
    @Override
    public void initialize(VietnameseName constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches(VIETNAMESE_NAME_PATTERN);
    }
}