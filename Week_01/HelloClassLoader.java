import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO (一句话描述这个类的作用)
 *
 * @author hf
 * @date 2020/10/20
 * @see
 */
public class HelloClassLoader extends ClassLoader {

    private String rootDir;

    public HelloClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 先去加载器里面看看已经加载到的类中是否有这个类
        Class<?> c = findLoadedClass(name);
        //应该要先查询有没有加载过这个类。如果已经加载，则直接返回加载好的类。如果没有，则加载新的类。
        if (c != null) {
            return c;
        } else {
            ClassLoader parent = this.getParent();
            try {
                //委派给父类加载  调用JVM委托机制来加载，如果一层层上抛都没有加载到Hello的话则调用自己的类加载
                c = parent.loadClass(name);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (c != null) {
                return c;
            } else {//
                byte[] classData = getClassData(name);
                if (classData == null) {
                    throw new ClassNotFoundException();
                } else {
                    // 将字节码处理成 Class实例返回
                    c = defineClass(name, classData, 0, classData.length);
                }
            }
        }
        return c;
    }

    private byte[] getClassData(String classname) {
        String path = rootDir + "/" + classname.replace('.', '/') + ".class";
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int temp = 0;
            while ((temp = is.read(buffer)) != -1) {
                baos.write(buffer, 0, temp);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
