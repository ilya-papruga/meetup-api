package com.modsen.meetup.validation;

import com.modsen.meetup.validation.api.PathVariableValidator;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class PathVariableValidatorImpl implements PathVariableValidator {

    public UUID validUUID(String uuid) {
        Pattern regex = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
        if (!regex.matcher(uuid).matches()){
            throw new IllegalArgumentException ("invalid uuid");
        }
        return UUID.fromString(uuid);
    }

    public void validUnixTime(Long time){

        if (time.toString().length() != 13) {
            throw new IllegalArgumentException("invalid date update");
        }

    }
}
