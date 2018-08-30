package com.lwxf.newstore.webapp.common.json;

import java.util.Date;

import com.lwxf.mybatis.json.JsonDateDeserializer;
import com.lwxf.mybatis.json.JsonDateSerializer;
import com.lwxf.mybatis.json.JsonDateTimeDeserializer;
import com.lwxf.mybatis.json.JsonDateTimeSerializer;
import com.lwxf.mybatis.json.JsonShortDateTimeDeserializer;
import com.lwxf.mybatis.json.JsonShortDateTimeSerializer;
import com.lwxf.mybatis.json.JsonShortTimeDeserializer;
import com.lwxf.mybatis.json.JsonShortTimeSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:32:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class JacksonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public JacksonObjectMapper() {

        SimpleModule module = new SimpleModule("miscDateModule");
        //
        // HH:mm
        module.addSerializer(Date.class, new JsonShortTimeSerializer());
        // yyyy-MM-dd
        module.addSerializer(Date.class, new JsonDateSerializer());
        // yyyy-MM-dd HH:mm
        module.addSerializer(Date.class, new JsonShortDateTimeSerializer());
        // yyyy-MM-dd HH:mm:ss
        module.addSerializer(Date.class, new JsonDateTimeSerializer());
        //----------------------------------------------------------------
        // yyyy-MM-dd HH:mm:ss
        module.addDeserializer(Date.class, new JsonDateTimeDeserializer());
        // yyyy-MM-dd HH:mm
        module.addDeserializer(Date.class, new JsonShortDateTimeDeserializer());
        // yyyy-MM-dd
        module.addDeserializer(Date.class, new JsonDateDeserializer());
        // HH:mm
        module.addDeserializer(Date.class, new JsonShortTimeDeserializer());
        //
        this.registerModule(module);
        //
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        this.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        //
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //
        this.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}