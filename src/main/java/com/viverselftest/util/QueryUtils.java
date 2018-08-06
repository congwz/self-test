package com.viverselftest.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class QueryUtils {

	public static String getQueryCondition(Object obj) {

        if(obj == null) return null;
  
        StringBuffer retSb = new StringBuffer();

		retSb.append(" 1 = 1 ");

		try {
			
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {

				String key = property.getName();

				if (key.compareToIgnoreCase("class") == 0)  continue;

				Method getter = property.getReadMethod();

				Object value = getter != null ? getter.invoke(obj) : null;

				if (value == null) continue;

				String queryValue = String.valueOf(value).trim();

				String condition = " and " + key + " = '" + queryValue + "'";

				if (queryValue.startsWith(">"))
					condition = " and " + key + " > '"  + queryValue.substring(1, queryValue.length()) + "' ";

				if (queryValue.startsWith(">="))
					condition = " and " + key + " >= '" + queryValue.substring(2, queryValue.length()) + "' ";

				if (queryValue.startsWith("<"))
					condition = " and " + key + " < '"  + queryValue.substring(1, queryValue.length()) + "' ";

				if (queryValue.startsWith("<="))
					condition = " and " + key + " <= '" + queryValue.substring(2, queryValue.length()) + "' ";

				if (queryValue.startsWith("!="))
					condition = " and " + key + " != '" + queryValue.substring(2, queryValue.length()) + "' ";

				if (queryValue.contains("*"))
				    condition = " and " + key + " like '" + queryValue.replaceAll("\\*", "\\%") + "' ";

				if (queryValue.contains("@-@"))
					condition = " and " + key + " >= '" + queryValue.split("@-@")[0] + "' and " + key + " <= '" + queryValue.split("@-@")[1] + "' ";

				retSb.append(condition);
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return retSb.toString();
    }



}
