package springboot06mybatis.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * ClassName:    MathUtils
 * Package:    springboot06mybatis.utils
 * Description:object类型转BigDecimal
 * Datetime:    2020/4/11   23:11
 * Author:   hewson.chen@foxmail.com
 */
public class MathUtils {
    public static BigDecimal objectConvertBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
                        + " into a BigDecimal.");
            }
        }
        return ret;
    }
}
