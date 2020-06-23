package com.smile.autofilldata;

import android.app.Activity;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

/**
 * @author lizhiqiang
 */
public class SmAutoFillData {
    private static SmAutoFillData instance;

    private SmAutoFillData() {
    }

    public static SmAutoFillData getInstance() {

        if (instance == null) {
            instance = new SmAutoFillData();
        }

        return instance;
    }

    public void inject(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(AutoFillData.class)) {
                autoFillData(obj, field);
            }
        }
    }

    private void autoFillData(Object obj, Field field) {

        field.setAccessible(true);

        AutoFillData fieldAnnotation = field.getAnnotation(AutoFillData.class);

        String key;

        if (TextUtils.isEmpty(fieldAnnotation.key())) {
            key = field.getName();
        } else {
            key = fieldAnnotation.key();
        }

        if (obj instanceof Activity) {
            // Activity
            if (((Activity) obj).getIntent().hasExtra(key)) {
                try {
                    field.set(obj, ((Activity) obj).getIntent().getSerializableExtra(key));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } else if (obj instanceof Fragment) {
            //Fragment
            if (((Fragment) obj).getArguments() != null &&
                    ((Fragment) obj).getArguments().containsKey(key)) {
                try {
                    field.set(obj, ((Fragment) obj).getArguments().getSerializable(key));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
