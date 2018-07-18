package com.liuwq.common.base.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


/**
 * @param <T> 需要转化的数组元素类型
 *            <P> Created by liuwq on 2018/4/16.
 */
public class ArrayTypeAdapter<T> extends TypeAdapter<T[]> {

    private Gson mGson;

    public ArrayTypeAdapter(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        mGson = gson;
    }

    @Override
    public void write(JsonWriter out, T[] value) throws IOException {

        if (value == null) {
            out.nullValue();
        } else {
            out.jsonValue(toJson(value));
        }
    }

    @Override
    public T[] read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NULL:
                in.nextNull();
                return null;
            case BEGIN_ARRAY:
                return mGson.fromJson(in.nextString(), new TypeToken<T[]>() {
                }.getType());
//            in.beginArray();
//            fromJson()
//            while (in.hasNext()) {
//                in.beginObject();
//                in.nextString();
//                in.endObject();
//            }
//            in.endArray();
//            return fromJson(in.nextString());
            default:
                return fromJson(in.nextString());
        }
//        return fromJson(in.nextName());
    }
}
