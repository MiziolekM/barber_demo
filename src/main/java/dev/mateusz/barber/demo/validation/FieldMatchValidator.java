package dev.mateusz.barber.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

// wykorzystuje ConstraintValidator do porównywania czy oba e-maile są takie same
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstFieldName;
    private String secondFieldName;
    private String message;

    // initializacja co z czym porównuje i jaka wiadomość
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
	    	firstFieldName = constraintAnnotation.first();
	    	secondFieldName = constraintAnnotation.second();
	    	message = constraintAnnotation.message();
    }

    // sprawdzenie 
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        
    	boolean valid = true;
        
    	try
        {
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignoruje
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}