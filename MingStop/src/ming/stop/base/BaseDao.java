package ming.stop.base;

import ming.stop.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ming on 2017/8/22.
 */
public class BaseDao<T> {
    private Class targetClass;
    private String tableName;
    //通用的查找根据id 查找对象
    public T findById(String id) throws Exception {
        //获取表名
        setTableName();
        String idName = targetClass.getDeclaredFields()[0].getName();
        String sql = "select * from "+ tableName+" where "+idName+" = ? ";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        T t = (T) runner.query(sql, new BeanHandler(targetClass), new Object[]{id});
        return t;
    }

    //通用的删除
    public boolean deleteById(String id) throws Exception{
        setTableName();
        String idName = targetClass.getDeclaredFields()[0].getName();
        String sql = "delete from "+tableName+" where  "+idName+" = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        int update = runner.update(sql, id);
        return  update >0 ? true : false;
    }

    //通用的查找所有
    public List<T> findAll() throws Exception {
        setTableName();
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from "+tableName;
        return (List<T>) runner.query(sql, new BeanListHandler(targetClass));
    }
    //通用的增加
    public boolean save(T t) throws Exception {
        setTableName();
        Field[] declaredFields = targetClass.getDeclaredFields();
        int length = declaredFields.length;//取多少个问号
        //设sql
        String mark = "";
        for (int i = 0 ; i < length;i ++){
            mark += "?,";
        }
        mark = mark.substring(0,mark.length()-1);
        String sql = "insert into "+tableName+" values ("+mark+")";
        //设 插入的值
        Object object[] = new Object[length];// 插入的数
        int i = 0;
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            char c = Character.toUpperCase(fieldName.charAt(0));
            fieldName = c + fieldName.substring(1, fieldName.length());

            //拿get方法,没有参数
            Method method = targetClass.getDeclaredMethod("get"+fieldName);
            Object value = method.invoke(t);
            object[i++] = value;
        }
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        int result = 0;
        result = queryRunner.update(sql,object);
        return  result > 0 ? true:false;
    }
    //通用的更新
    public boolean update(T t) throws Exception {
        setTableName();
        //设置 sql
        String sqlParam = "";
        Field[] declaredFields = targetClass.getDeclaredFields();
        //获取参数
        Object ob[] = new Object[declaredFields.length + 1];
        int i = 0;
        for (Field declaredField : declaredFields) {
            sqlParam +=  declaredField.getName()+" = ?,";
            String methodName = declaredField.getName();
            methodName = Character.toUpperCase(methodName.charAt(0))+methodName.substring(1,methodName.length());
            methodName = "get"+methodName;
            Method method = targetClass.getMethod(methodName);
            ob[i++] = method.invoke(t);
        }
        ob[i] = ob[0];
        //去掉 分号
        String idName = declaredFields[0].getName();//id名字
        sqlParam = sqlParam.substring(0,sqlParam.length()-1)+" where "+idName+" = ?";
        String sql = "update "+tableName+" set "+sqlParam;
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        int len = queryRunner.update(sql,ob);
        return len > 0 ? true:false;
    }

    //获取泛型参数类名
    private void setTableName() {
        Class aClass = this.getClass();
        Type type = aClass.getGenericSuperclass();
        ParameterizedType paramertype = (ParameterizedType) type;
        Type[] types = paramertype.getActualTypeArguments();
        Type target = types[0];
        //强转成 class类型
        //Class classes =  target.getClass();  这个无效，因为这个是获取运行时的类
        targetClass = (Class)target;
        tableName = targetClass.getSimpleName().toLowerCase();
    }

}
