package ming.store.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ming on 2017/8/24.
 */
public class CommonUtils {

    //32位的 UUID
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    // 64位的 UUID
    public static String getUUID64(){
        return  getUUID()+ getUUID();
    }

    //带注册Date的 BeanUtil
    public static <T>  T copyToBean(HttpServletRequest request, Class<T> tClass) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String,String> map = new HashMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        //重写封装 map
        for (Map.Entry<String, String[]> entry : entries) {
            map.put(entry.getKey(),entry.getValue()[0]);
        }
        // 注册 date 类型
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object o) {
                Date  date = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        },Date.class);
        T t = tClass.newInstance();
        BeanUtils.copyProperties(t,map);
        return t;
    }

}
