//package com.yxhuang.androiddailydemo.typeclass;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by yxhuang
// * Date: 2021/3/13
// * Description:
// */
//public class Client {
//
////    public static void main(String[] args) throws Exception {
////        List<IHuman> humanList = new ArrayList<>();
////        humanList.add(new Teacher());
////        humanList.add(new Student());
////        School school = new School();
////        getMethodReturnType(School.class);
////
////    }
//
//    public static void getMethodReturnType(Class school) throws Exception {
//        Method method = school.getMethod("addHuman", null);
//        System.out.println(method.getReturnType());
//        Type retrunType = method.getGenericReturnType();
//        System.out.println(retrunType);
//        if (retrunType instanceof ParameterizedType) {
//            ParameterizedType type = (ParameterizedType) retrunType;
//            Type[] typeArguments = type.getActualTypeArguments();
//            for (Type typeArgument : typeArguments) {
//                Class typeArgClass = (Class) typeArgument;
//                System.out.println("泛型类型：" + typeArgClass);
//            }
//        }
//    }
//
////    public static void getGenericFieldTypes() throws Exception {
////        Field field = MyClass.class.getField("stringList");
////        Type genericsFieldType = field.getGenericType();
////        if (genericsFieldType instanceof ParameterizedType) {
////            ParameterizedType parameterizedType = (ParameterizedType) genericsFieldType;
////            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
////            for (Type fieldArgType : fieldArgTypes) {
////                Class fieldArgClass = (Class) fieldArgType;
////                System.out.println("泛型字段的类型：" + fieldArgClass);
////            }
////        }
////    }
////
////    public static void getMethodParameterTypes() throws Exception {
////        Method method = MyClass.class.getMethod("setList", List.class);
////        Type[] genericParameterTypes = method.getGenericParameterTypes();
////        for (Type genericType : genericParameterTypes) {
////            if (genericType instanceof ParameterizedType) {
////                ParameterizedType parameterizedType = (ParameterizedType) genericType;
////                Type[] types = parameterizedType.getActualTypeArguments();
////                for (Type type : types) {
////                    Class realType = (Class) type;
////                    System.out.println("方法参数的类型：" + realType);
////                }
////            }
////        }
////    }
//}
