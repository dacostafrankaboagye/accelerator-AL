package reflec_anno;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Computer comp = new Computer(123, "MacBook");

        Class<?> clazz = comp.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }

        // instantiating objects dynamically

        Constructor<?> constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();

        Field field = clazz.getDeclaredField("macAddress");
        field.setAccessible(true);
        field.set(instance, 900);

        Field field2 = clazz.getDeclaredField("name");
        field2.setAccessible(true);
        field2.set(instance, "Dell XP");

        System.out.println( instance.toString());

        System.out.println(".....");
        // methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // System.out.println(method.getName());
        }

        Method method = clazz.getDeclaredMethod("getMacAddress");
        method.setAccessible(true);
        System.out.println(method.invoke(instance));  // invoking it
        System.out.println(method.getReturnType());

    }
}





class Computer{
    int macAddress; String name;
    public Computer(int macAddress, String name) {
        this.macAddress = macAddress;
        this.name = name;
    }
    public Computer(){}
    public void start(){
        System.out.println("Computer is starting");
    }
    public void shutdown(){
        System.out.println("Computer is shutting down");
    }
    // getters and setters
    void setMacAddress(int macAddress){
        this.macAddress = macAddress;
    }
    int getMacAddress(){
        return this.macAddress;
    }
    void setName(String name){
        this.name = name;
    }
    String getName(){
        return this.name;
    }

    public String toString(){
        return "Computer [macAddress=" + macAddress + ", name=" + name + "]";
    }




}
