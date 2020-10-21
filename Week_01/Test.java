import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * TODO (一句话描述这个类的作用)
 *
 * @author hf
 * @date 2020/10/21
 * @see
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        HelloClassLoader loader = new HelloClassLoader("D:\\jikedaxue\\JAVA-000\\Week_01");
        Class<?> c = loader.loadClass("Hello");
        Object obj = c.newInstance();

        Method[] methods = c.getDeclaredMethods();
        methods[0].invoke(obj, new Object[]{});

        Method method = c.getDeclaredMethod("show");
        method.invoke(obj, new Object[]{});

        System.out.println(c.getClassLoader());
    }
}
