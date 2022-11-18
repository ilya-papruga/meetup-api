package com.modsen.meetup.validation;

import com.modsen.meetup.dto.filter.FilterString;
import com.modsen.meetup.validation.api.FilterValidation;
import org.springframework.stereotype.Component;

@Component
public class FilterValidationImpl implements FilterValidation {

    public boolean isEmpty (FilterString filter){

        if (filter.getTopic() == null && filter.getOrganizer() == null && filter.getDateTime() == null && filter.getSortingField() == null) {
            return true;
        }
        return false;
    }

}
