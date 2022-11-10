package com.modsen.meetup.validation.api;

import java.util.UUID;

public interface PathVariableValidator {

    UUID validUUID(String uuid);

    void validUnixTime(Long time);

}
