package com.kerry.senior.validator;

import com.kerry.senior.util.PatternUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Kerry Dong
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;

	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return PatternUtil.isMobile(value);
		}else {
            return StringUtils.isEmpty(value) || PatternUtil.isMobile(value);
        }
	}

}
