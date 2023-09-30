package com.cjw.smarthomesync.common.annotation;


import jakarta.validation.groups.Default;

public class ValidationGroups {
    private ValidationGroups() {
    }

    public interface signup extends Default {};

    public interface login extends Default {};
}
