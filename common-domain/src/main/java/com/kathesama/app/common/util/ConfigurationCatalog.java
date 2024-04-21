package com.kathesama.app.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigurationCatalog {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$";
}

