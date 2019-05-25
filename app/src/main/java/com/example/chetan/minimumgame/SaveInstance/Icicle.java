package com.example.chetan.minimumgame.SaveInstance;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Save and load fields to/from a {@link Bundle}. All fields should be annotated with {@link
 * SaveInstance}.</p>
 */
public class Icicle {

    private static final String TAG = "Icicle";

    /**
     * Find all fields with the {@link SaveInstance} annotation and add them to the {@link Bundle}.
     *
     * @param outState
     *         The bundle from {@link Activity#onSaveInstanceState(Bundle)} or {@link
     *         Fragment#onSaveInstanceState(Bundle)}
     * @param classInstance
     *         The object to access the fields which have the {@link SaveInstance} annotation.
     * @see #load(Bundle, Object)
     */
    public static void save(Bundle outState, Object classInstance) {
        save(outState, classInstance, classInstance.getClass());
    }

    /**
     * Find all fields with the {@link SaveInstance} annotation and add them to the {@link Bundle}.
     *
     * @param outState
     *         The bundle from {@link Activity#onSaveInstanceState(Bundle)} or {@link
     *         Fragment#onSaveInstanceState(Bundle)}
     * @param classInstance
     *         The object to access the fields which have the {@link SaveInstance} annotation.
     * @param baseClass
     *         Base class, used to get all superclasses of the instance.
     * @see #load(Bundle, Object, Class)
     */
    public static void save(Bundle outState, Object classInstance, Class<?> baseClass) {
        if (outState == null) {
            return;
        }
        Class<?> clazz = classInstance.getClass();
        while (baseClass.isAssignableFrom(clazz)) {
            String className = clazz.getName();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(SaveInstance.class)) {
                    field.setAccessible(true);
                    String key = className + "#" + field.getName();
                    try {
                        Object value = field.get(classInstance);
                        if (value instanceof Parcelable) {
                            outState.putParcelable(key, (Parcelable) value);
                        } else if (value instanceof Serializable) {
                            outState.putSerializable(key, (Serializable) value);
                        }
                    } catch (Throwable t) {
                        Log.d(TAG, "The field '" + key + "' was not added to the bundle");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Load all saved fields that have the {@link SaveInstance} annotation.
     *
     * @param savedInstanceState
     *         The saved-instance {@link Bundle} from an {@link Activity} or {@link Fragment}.
     * @param classInstance
     *         The object to access the fields which have the {@link SaveInstance} annotation.
     * @see #save(Bundle, Object)
     */
    public static void load(Bundle savedInstanceState, Object classInstance) {
        load(savedInstanceState, classInstance, classInstance.getClass());
    }

    /**
     * Load all saved fields that have the {@link SaveInstance} annotation.
     *
     * @param savedInstanceState
     *         The saved-instance {@link Bundle} from an {@link Activity} or {@link Fragment}.
     * @param classInstance
     *         The object to access the fields which have the {@link SaveInstance} annotation.
     * @param baseClass
     *         Base class, used to get all superclasses of the instance.
     * @see #save(Bundle, Object, Class)
     */
    public static void load(Bundle savedInstanceState, Object classInstance, Class<?> baseClass) {
        if (savedInstanceState == null) {
            return;
        }
        Class<?> clazz = classInstance.getClass();
        while (baseClass.isAssignableFrom(clazz)) {
            String className = clazz.getName();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(SaveInstance.class)) {
                    String key = className + "#" + field.getName();
                    field.setAccessible(true);
                    try {
                        Object fieldVal = savedInstanceState.get(key);
                        if (fieldVal != null) {
                            field.set(classInstance, fieldVal);
                        }
                    } catch (Throwable t) {
                        Log.d(TAG, "The field '" + key + "' was not retrieved from the bundle");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

}
