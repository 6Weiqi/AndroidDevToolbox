package com.liaobd.common.base.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;


/**
 * @param <T> 需要转化的列表元素类型
 *            <P> Created by liuwq on 2018/4/16.
 */
public class ListTypeAdapter<T> extends TypeAdapter<List<T>> {

    private Gson mGson;

    public ListTypeAdapter(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        mGson = gson;
    }

    @Override
    public void write(JsonWriter out, List<T> value) throws IOException {

        if (value == null) {
            out.nullValue();
        } else {
            out.jsonValue(toJson(value));
        }
    }

    @Override
    public List<T> read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NULL:
                in.nextNull();
                return null;
            case BEGIN_ARRAY:
                return mGson.fromJson(in.nextString(), new TypeToken<List<T>>() {
                }.getType());
//                return in.nextString().split(",");
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
