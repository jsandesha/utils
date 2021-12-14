package com.highpeaksw.utils.enums;

public enum DateFormatPatternEnum {

        DD_MMM_YYYY("dd-MMM-yyyy"), DD_MMM_YY("dd-MMM-yy");

    private final String pattern;

    DateFormatPatternEnum( String pattern )
    {
        this.pattern = pattern;
    }

    public String getPattern()
    {
        return this.pattern;
    }
}
