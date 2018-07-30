package com.izettle.wrench.service.storage;

import com.izettle.wrench.preferences.WrenchPreferences;
import com.izettle.wrench.service.Storage;

class WrenchPreferenceStorage implements Storage {

    private final WrenchPreferences mWrenchPreferences;

    public WrenchPreferenceStorage(final WrenchPreferences wrenchPreferences) {
        mWrenchPreferences = wrenchPreferences;
    }

    @Override
    public Object getValue(final Class<?> type, final String key, final Object defaultValue) {
        if (type == String.class) {
            return mWrenchPreferences.getString(key, (String) defaultValue);
        } else if (type == Integer.TYPE || type == Integer.class) {
            return mWrenchPreferences.getInt(key, (Integer) defaultValue);
        } else if (type == Boolean.TYPE || type == Boolean.class) {
            return mWrenchPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (type.isEnum()) {
            final Class<Enum> enumClass = castToEnum(type);
            return mWrenchPreferences.getEnum(key, enumClass, enumClass.cast(defaultValue));
        }

        throw new IllegalArgumentException("Not supported type");
    }

    private static <T extends Enum<T>> Class<T> castToEnum(Class<?> type) {
        return (Class<T>) type;
    }
}
