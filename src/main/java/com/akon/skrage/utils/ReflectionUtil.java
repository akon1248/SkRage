package com.akon.skrage.utils;

import java.lang.reflect.*;

public class ReflectionUtil {

	/**
	 * オブジェクトのフィールドを取得する
	 *
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field;
		try {
			field = obj.getClass().getField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> clazz = obj.getClass();
			while (true) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException ex) {
					if (clazz == Object.class) {
						throw ex;
					}
					clazz = clazz.getSuperclass();
				}
			}
		}
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * オブジェクトのフィールドを取得する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static Object getField(Class<?> clazz, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * 静的フィールドを取得する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static Object getStaticField(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(null);
	}

	/**
	 * オブジェクトのフィールドの値を変更する
	 *
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static void setField(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
		Field field;
		try {
			field = obj.getClass().getField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> clazz = obj.getClass();
			while (true) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException ex) {
					if (clazz == Object.class) {
						throw ex;
					}
					clazz = clazz.getSuperclass();
				}
			}
		}
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & -17);
		field.set(obj, value);
	}

	/**
	 * オブジェクトのフィールドの値を変更する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static void setField(Class<?> clazz, Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(obj, value);
	}

	/**
	 * 静的フィールドの値を変更する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 * @throws NoSuchFieldException フィールドが存在しない場合
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static void setStaticField(Class<?> clazz, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & -17);
		field.set(null, value);
	}

	/**
	 * オブジェクトのメソッドを引数なしで実行する
	 *
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeMethod(Object obj, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokeMethod(obj, methodName, new Class[0], new Object[0]);
	}

	/**
	 * オブジェクトのメソッドを実行する
	 *
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @param paramTypes パラメータ配列
	 * @param params メソッドの引数
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeMethod(Object obj, String methodName, Class[] paramTypes, Object[] params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method;
		try {
			method = obj.getClass().getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {
			Class<?> clazz = obj.getClass();
			while (true) {
				try {
					method = clazz.getDeclaredMethod(methodName, paramTypes);
					break;
				} catch (NoSuchMethodException ex) {
					if (clazz == Object.class) {
						throw ex;
					}
					clazz = clazz.getSuperclass();
				}
			}
		}
		method.setAccessible(true);
		return method.invoke(obj, params);
	}

	/**
	 * オブジェクトのメソッドを引数なしで実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeMethod(Class<?> clazz, Object obj, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokeMethod(clazz, obj, methodName, new Class[0], new Object[0]);
	}

	/**
	 * オブジェクトのメソッドを実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @param paramTypes パラメータ配列
	 * @param params メソッドの引数
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeMethod(Class<?> clazz, Object obj, String methodName, Class[] paramTypes, Object[] params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = clazz.getDeclaredMethod(methodName, paramTypes);
		method.setAccessible(true);
		return method.invoke(obj, params);
	}

	/**
	 * 静的メソッドを引数なしで実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeStaticMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokeStaticMethod(clazz, methodName, new Class[0], new Object[0]);
	}

	/**
	 * 静的メソッドを実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param methodName メソッドの名前
	 * @param paramTypes パラメータ配列
	 * @param params メソッドの引数
	 * @return メソッドの戻り値
	 * @throws NoSuchMethodException 指定されたメソッドが存在しない場合
	 * @throws IllegalAccessException MethodオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 * @throws InvocationTargetException 基本となるメソッドが例外をスローする場合
	 */
	public static Object invokeStaticMethod(Class<?> clazz, String methodName, Class[] paramTypes, Object[] params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = clazz.getDeclaredMethod(methodName, paramTypes);
		method.setAccessible(true);
		return method.invoke(null, params);
	}

	/**
	 * コンストラクタを引数なしで実行する
	 *
	 * @param clazz コンストラクタを宣言しているクラス
	 * @return コンストラクタによって生成されたインスタンス
	 * @throws InvocationTargetException 基本となるコンストラクタが例外をスローする場合
	 * @throws NoSuchMethodException 引数なしのコンストラクタが存在しない場合
	 * @throws InstantiationException 指定されたクラスが抽象クラスの場合
	 * @throws IllegalAccessException ConstructorオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static <T> T invokeConstructor(Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		return invokeConstructor(clazz, new Class[0], new Object[0]);
	}

	/**
	 * コンストラクタを引数なしで実行する
	 *
	 * @param clazz コンストラクタを宣言しているクラス
	 * @param paramTypes パラメータ配列
	 * @param params コンストラクタの引数
	 * @return コンストラクタによって生成されたインスタンス
	 * @throws InvocationTargetException 基本となるコンストラクタが例外をスローする場合
	 * @throws NoSuchMethodException 引数なしのコンストラクタが存在しない場合
	 * @throws InstantiationException 指定されたクラスが抽象クラスの場合
	 * @throws IllegalAccessException ConstructorオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static <T> T invokeConstructor(Class<T> clazz, Class[] paramTypes, Object[] params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Constructor<T> constructor = clazz.getDeclaredConstructor(paramTypes);
		constructor.setAccessible(true);
		return constructor.newInstance(params);
	}

	/**
	 * obj1のフィールドをobj2にシャローコピーします
	 *
	 * @param clazz コピーするフィールドが宣言されているクラス
	 * @param obj1 フィールドのコピー元
	 * @param obj2 フィールドのコピー先
	 * @throws IllegalAccessException FieldオブジェクトがJava言語アクセス制御を実施しており、基本となるフィールドにアクセスできない場合
	 */
	public static void copyFields(Class<?> clazz, Object obj1, Object obj2) throws IllegalAccessException {
		for (Field field: clazz.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				field.set(obj2, field.get(obj1));
			}
		}
	}

}

