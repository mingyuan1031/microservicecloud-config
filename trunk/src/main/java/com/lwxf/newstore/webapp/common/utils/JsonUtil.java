package com.lwxf.newstore.webapp.common.utils;

import java.util.List;
import java.util.Map;

import com.lwxf.newstore.webapp.common.json.JacksonObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Hu Changwei
 * @date 2013-12-25
 * 
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper defaultJsonMapper = new JacksonObjectMapper();

	private static ObjectMapper noNullJsonMapper = new JacksonObjectMapper();

	private static ObjectMapper formatJsonMapper = new JacksonObjectMapper();

	static {
		noNullJsonMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		//
		formatJsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	public static <T> T fromJson(String json, Class<T> dstType) {
		try {
			return defaultJsonMapper.readValue(json, dstType);
		} catch (Exception e) {
			logger.error("异常", e);
			logger.error(json);
			return null;
		}
	}

	public static <T> T from(Object src, Class<T> dstType) {
		if (src == null) {
			return null;
		}
		//
		String json = JsonUtil.toJson(src, true);
		//
		return fromJson(json, dstType);
	}

	public static <T> T fromJson(String json, TypeReference<T> dstTypeRef) {
		try {
			return defaultJsonMapper.readValue(json, dstTypeRef);
		} catch (Exception e) {
			logger.error("异常", e);
			logger.error(json);
			return null;
		}
	}

	public static <T> T from(Object src, TypeReference<T> dstTypeRef) {
		if (src == null) {
			return null;
		}
		//
		String json = JsonUtil.toJson(src, true);
		//
		return fromJson(json, dstTypeRef);
	}

	public static String toJson(Object src) {
		if (src == null) {
			return null;
		}
		try {
			return defaultJsonMapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			logger.error("异常", e);
			return null;
		}
	}

	public static String toFormattedJson(Object src) {
		if (src == null) {
			return null;
		}
		try {
			return formatJsonMapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			logger.error("异常", e);
			return null;
		}
	}

	public static String toJson(Object src, boolean noNullValues) {
		if (src == null) {
			return null;
		}
		ObjectMapper jsonMapper = noNullValues ? noNullJsonMapper : defaultJsonMapper;
		try {
			return jsonMapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			logger.error("异常", e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static String formatAsMap(String jsonStr) {
		Map data = fromJson(jsonStr, Map.class);
		return toFormattedJson(data);
	}

	@SuppressWarnings("rawtypes")
	public static String formatAsList(String jsonStr) {
		List data = fromJson(jsonStr, List.class);
		return toFormattedJson(data);
	}
}
