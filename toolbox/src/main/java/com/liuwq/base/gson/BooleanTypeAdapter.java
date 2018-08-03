package com.liuwq.base.gson;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 其他类型转换为 {@link Boolean}、{@code boolean} 类型适配器
 */
public class BooleanTypeAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value);
        }
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case BOOLEAN:
                return in.nextBoolean();
            case NULL:
                in.nextNull();
                return null;
            case NUMBER:
                return in.nextInt() != 0;
            case STRING:
                String booleanStr = in.nextString();
                if (TextUtils.isEmpty(booleanStr)) {
                    return null;
                } else {
                    return !booleanStr.equalsIgnoreCase("0");
                }
            default:
                throw new IllegalStateException("Expected BOOLEAN or NUMBER but was "
                        + peek);
        }
    }
}
