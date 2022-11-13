package com.modsen.meetup.validation;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.validation.api.FilterValidation;
import org.springframework.stereotype.Component;

@Component
public class FilterValidationImpl implements FilterValidation {

    public boolean isEmpty (Filter filter){

        if (filter.getTopic() == null && filter.getOrganizer() == null && filter.getDateTime() == null && filter.getSortingField() == null) {
            return true;
        }
        return false;
    }

}
