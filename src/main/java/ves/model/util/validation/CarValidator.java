package ves.model.util.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ves.model.Car;

public class CarValidator {

    public void validate(Car car, Errors errors) {
        if (StringUtils.isEmpty(car.getMake())){
            errors.rejectValue("make", "required");
        }
        if (StringUtils.isEmpty(car.getModel())){
            errors.rejectValue("model", "required");
        }
        if (StringUtils.isEmpty(car.getRegistrationNumber())){
            errors.rejectValue("registrationNumber", "required");
        }
    }
}
