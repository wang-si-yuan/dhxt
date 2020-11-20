package top.vicp.wsy19990317.dhxt.common.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class EntityClassUtil {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    
    /**
     * List对象转为为List对象
     * @param sources   源数据
     * @param clazz 目标Class
     * @param <T>
     * @return
     */
    public static <T> List<T> transforList(List<?> sources, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (sources == null) {
            return list;
        }
        for (Object o : sources) {
            T t = transfor(o, clazz);
            list.add(t);
        }
        return list;
    }

    /**
     * 源数据转换为目标对象
     * @param source    源数据
     * @param target    目标数据
     */
    public static void transfor(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        mapper.map(source, target);
    }

    /**
     * 源数据转换为目标class
     * @param source    源数据
     * @param target    目标class
     * @param <T>
     * @return
     */
    public static <T,W> T transfor(W source, Class<T> target) {
        if (source == null) {
            return null;
        }
        return mapper.map(source, target);
    }


}
