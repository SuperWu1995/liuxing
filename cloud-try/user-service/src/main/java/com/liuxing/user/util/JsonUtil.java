package com.liuxing.user.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonUtil {
	private static JsonUtil ju;
	private static JsonFactory jf;
	private static ObjectMapper mapper;
	private JsonUtil(){}
	
	public static JsonUtil getInstance() {
		if(ju==null) {
			ju = new JsonUtil();
		}
		return ju;
	}
	
	public static ObjectMapper getMapper() {
		if(mapper==null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public static JsonFactory getFactory() {
		if(jf==null) {
			jf = new JsonFactory();
		}
		return jf;
	}
	
	public String obj2json(Object obj) {
		JsonGenerator jg = null;
		try {
			jf = getFactory();
			mapper = getMapper();
			StringWriter out = new StringWriter();
			jg = jf.createGenerator(out);
			mapper.writeValue(jg, obj);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(jg!=null) {
					jg.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object json2obj(String json, Class<?> clz) {
		try {
			mapper = getMapper();
			return mapper.readValue(json,clz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * List集合转换为Json
     * @param list
     * @return
     */
    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 对象数组转换为Json
     * @param array
     * @return
     */
    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * Map集合转换为Json
     * @param map
     * @return
     */
    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * Set集合转为Json
     * @param set
     * @return
     */
    public static String set2json(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 字符串转换为Json
     * @param s
     * @return
     */
    public static String string2json(String s) {
        if (s == null) {
			return "";
		}
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '/':
                sb.append("\\/");
                break;
            default:
                if (ch >= '\u0000' && ch <= '\u001F') {
                    String ss = Integer.toHexString(ch);
                    sb.append("\\u");
                    for (int k = 0; k < 4 - ss.length(); k++) {
                        sb.append('0');
                    }
                    sb.append(ss.toUpperCase());
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
    public static String object2json(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer
                || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double
                || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2json(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2json((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2json((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2json((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2json((Set<?>) obj));
        } else {
            json.append(bean2json(obj));
        }
        return json.toString();
    }
    /**
     * 对象bean转换为Json
     * @param bean
     * @return
     */
    public static String bean2json(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = object2json(props[i].getName());
                    String value = object2json(props[i].getReadMethod().invoke(
                            bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }


	@SuppressWarnings("unchecked")
	public static String toJson(final Object o)
	{
		if (o == null)
		{
			return "null";
		}
		if (o instanceof String) //String
		{
			return string2Json((String) o);
		}
		if (o instanceof Date) //Boolean
		{
			return date2Json((Date) o);
		}
		if (o instanceof Boolean) //Boolean
		{
			return boolean2Json((Boolean) o);
		}
		if (o instanceof Number) //Number
		{
			return number2Json((Number) o);
		}
		if (o instanceof Map) //Map
		{
			return map2Json((Map<String, Object>) o);
		}
		if (o instanceof Collection) //List  Set
		{
			return collection2Json((Collection) o);
		}
		if (o instanceof Object[]) //对象数组
		{
			return array2Json((Object[]) o);
		}

		if (o instanceof int[])//基本类型数组
		{
			return intArray2Json((int[]) o);
		}
		if (o instanceof boolean[])//基本类型数组
		{
			return booleanArray2Json((boolean[]) o);
		}
		if (o instanceof long[])//基本类型数组
		{
			return longArray2Json((long[]) o);
		}
		if (o instanceof float[])//基本类型数组
		{
			return floatArray2Json((float[]) o);
		}
		if (o instanceof double[])//基本类型数组
		{
			return doubleArray2Json((double[]) o);
		}
		if (o instanceof short[])//基本类型数组
		{
			return shortArray2Json((short[]) o);
		}
		if (o instanceof byte[])//基本类型数组
		{
			return byteArray2Json((byte[]) o);
		}
		if (o instanceof Object) //保底收尾对象
		{
			return object2Json(o);
		}

		throw new RuntimeException("不支持的类型: " + o.getClass().getName());
	}

	/**
	 * 将 String 对象编码为 JSON格式，只需处理好特殊字符
	 * 
	 * @param s
	 *            String 对象
	 * @return JSON格式
	 */
public 	static String string2Json(final String s)
	{
		final StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('\"');
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);
			switch (c)
			{
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	/**
	 * 将 Number 表示为 JSON格式
	 * 
	 * @param number
	 *            Number
	 * @return JSON格式
	 */
public	static String number2Json(final Number number)
	{
		return number.toString();
	}
	
public 	static String date2Json(final Date date)
	{
		return string2Json(date2String(date, "yyyy-MM-dd"));
	}

	/**
	 * 将 Boolean 表示为 JSON格式
	 * 
	 * @param bool
	 *            Boolean
	 * @return JSON格式
	 */
public 	static String boolean2Json(final Boolean bool)
	{
		return bool.toString();
	}

	/**
	 * 将 Collection 编码为 JSON 格式 (List,Set)
	 * 
	 * @param c
	 * @return
	 */
	public static String collection2Json(final Collection<Object> c)
	{
		final Object[] arrObj = c.toArray();
		return toJson(arrObj);
	}

	/**
	 * 将 Map<String, Object> 编码为 JSON 格式
	 * 
	 * @param map
	 * @return
	 */
	public static String map2Json(final Map<String, Object> map)
	{
		if (map.isEmpty())
		{
			return "{}";
		}
		final StringBuilder sb = new StringBuilder(map.size() << 4); //4次方
		sb.append('{');
		final Set<String> keys = map.keySet();
		for (final String key : keys)
		{
			final Object value = map.get(key);
			sb.append('\"');
			sb.append(key); //不能包含特殊字符
			sb.append('\"');
			sb.append(':');
			sb.append(toJson(value)); //循环引用的对象会引发无限递归
			sb.append(',');
		}
		// 将最后的 ',' 变为 '}': 
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	/**
	 * 将数组编码为 JSON 格式
	 * 
	 * @param array
	 *            数组
	 * @return JSON 格式
	 */
	public static String array2Json(final Object[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4); //4次方
		sb.append('[');
		for (final Object o : array)
		{
			sb.append(toJson(o));
			sb.append(',');
		}
		// 将最后添加的 ',' 变为 ']': 
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String intArray2Json(final int[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final int o : array)
		{
			sb.append(Integer.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String longArray2Json(final long[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final long o : array)
		{
			sb.append(Long.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String booleanArray2Json(final boolean[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final boolean o : array)
		{
			sb.append(Boolean.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String floatArray2Json(final float[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final float o : array)
		{
			sb.append(Float.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String doubleArray2Json(final double[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final double o : array)
		{
			sb.append(Double.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String shortArray2Json(final short[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final short o : array)
		{
			sb.append(Short.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String byteArray2Json(final byte[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final byte o : array)
		{
			sb.append(Byte.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String object2Json(final Object bean)
	{
		//数据检查
		if (bean == null)
		{
			return "{}";
		}
		final Method[] methods = bean.getClass().getMethods(); //方法数组
		final StringBuilder sb = new StringBuilder(methods.length << 4); //4次方
		sb.append('{');

		for (final Method method : methods)
		{
			try
			{
				final String name = method.getName();
				String key = "";
				if (name.startsWith("get"))
				{
					key = name.substring(3);

					//防死循环
					final String[] arrs =
					{ "Class" };
					boolean bl = false;
					for (final String s : arrs)
					{
						if (s.equals(key))
						{
							bl = true;
							continue;
						}
					}
					if (bl)
					{
						continue; //防死循环
					}
				}
				else if (name.startsWith("is"))
				{
					key = name.substring(2);
				}
				if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0)
				{
					if (key.length() == 1)
					{
						key = key.toLowerCase();
					}
					else if (!Character.isUpperCase(key.charAt(1)))
					{
						key = key.substring(0, 1).toLowerCase() + key.substring(1);
					}
					final Object elementObj = method.invoke(bean);

					//System.out.println("###" + key + ":" + elementObj.toString());

					sb.append('\"');
					sb.append(key); //不能包含特殊字符
					sb.append('\"');
					sb.append(':');
					sb.append(toJson(elementObj)); //循环引用的对象会引发无限递归
					sb.append(',');
				}
			}
			catch (final Exception e)
			{
				//e.getMessage();
				throw new RuntimeException("在将bean封装成JSON格式时异常：" + e.getMessage(), e);
			}
		}
		if (sb.length() == 1)
		{
			return bean.toString();
		}
		else
		{
			sb.setCharAt(sb.length() - 1, '}');
			return sb.toString();
		}
	}
	
	public static String date2String(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		String sDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sDate = sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sDate;
	}
}
