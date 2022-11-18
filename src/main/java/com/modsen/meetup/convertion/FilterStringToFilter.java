package com.modsen.meetup.convertion;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.dto.filter.FilterString;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FilterStringToFilter implements Converter <FilterString, Filter> {

    @Override
    public Filter convert(FilterString source) {

        Filter filter = new Filter();

        filter.setTopic(source.getTopic());
        filter.setOrganizer(source.getOrganizer());
        filter.setSortingField(source.getSortingField());
        filter.setSortingType(source.getSortingType());

        if (source.getDateTime() != null)
        {
            filter.setDateTime(LocalDateTime.parse(source.getDateTime()));
        }

        return filter;
    }
}
