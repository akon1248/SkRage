package com.akon.skrage.utils;

import com.akon.skrage.utils.exceptionsafe.ThrowsBiFunction;
import com.akon.skrage.utils.exceptionsafe.ThrowsRunnable;
import com.akon.skrage.utils.exceptionsafe.ThrowsSupplier;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionUtil {

	public static final ReflectionUtil DEFAULT = new ReflectionUtil();
	private static final Field MODIFIER_FIELD = initModifierField();

	private final HashBasedTable<Class<?>, String, Class<?>> classDeclaringFieldCache = HashBasedTable.create();
	private final HashBasedTable<Class<?>, String, Class<?>> classDeclaringMethodCache = HashBasedTable.create();
	private final HashBasedTable<Class<?>, String, Field> fieldCache = HashBasedTable.create();
	private final HashBasedTable<Class<?>, String, Executable> methodCache = HashBasedTable.create();

	@SneakyThrows
	private static Field initModifierField() {
		Field field = Field.class.getDeclaredField("modifiers");
		field.setAccessible(true);
		return field;
	}

	private static <R, C, V, X extends Throwable> V computeIfAbsent(Table<R, C, V> table, R row, C column, ThrowsBiFunction<? super R, ? super C, ? extends V, ? extends X> mappingFunction) throws X, NoSuchMethodException {
		Validate.notNull(mappingFunction);
		if (table.contains(row, column)) {
			return table.get(row, column);
		}
		V value = mappingFunction.apply(row, column);
		table.put(row, column, value);
		return value;
	}

	private static String toTypeDescription(Class<?> clazz) {
		Validate.notNull(clazz);
		if (clazz == void.class) {
			return "V";
		} else if (clazz == byte.class) {
			return "B";
		} else if (clazz == short.class) {
			return "S";
		} else if (clazz == int.class) {
			return "I";
		} else if (clazz == long.class) {
			return "J";
		} else if (clazz == float.class) {
			return "F";
		} else if (clazz == double.class) {
			return "D";
		} else if (clazz == char.class) {
			return "C";
		} else if (clazz == boolean.class) {
			return "Z";
		} else if (clazz.isArray()) {
			return '[' + toTypeDescription(clazz.getComponentType());
		} else {
			return 'L' + clazz.getName().replace('.', '/') + ';';
		}
	}

	private static String methodInfo(String name, Class<?>[] params) {
		return name + '(' + Arrays.stream(params).map(ReflectionUtil::toTypeDescription).collect(Collectors.joining()) + ')';
	}

	private static <T> T reflectiveAction(ThrowsSupplier<T, ReflectiveOperationException> action) {
		try {
			return action.get();
		} catch (ReflectiveOperationException e) {
			throw new UncheckedReflectiveOperationException(e);
		}
	}


	private static void runReflectiveAction(ThrowsRunnable<ReflectiveOperationException> action) {
		try {
			action.run();
		} catch (ReflectiveOperationException e) {
			throw new UncheckedReflectiveOperationException(e);
		}
	}

	private Field getField(Class<?> clazz, String fieldName) throws ReflectiveOperationException {
		return getDeclaredField(computeIfAbsent(classDeclaringFieldCache, clazz, fieldName, (c, name) -> {
			Field field = null;
			do {
				try {
					field = c.getDeclaredField(name);
				} catch (NoSuchFieldException e) {
					if (c == Object.class) {
						throw e;
					}
					c = c.getSuperclass();
				}
			} while (field == null);
			return c;
		}), fieldName);
	}

	private Field getDeclaredField(Class<?> clazz, String fieldName) throws ReflectiveOperationException {
		return computeIfAbsent(fieldCache, clazz, fieldName, (c, name) -> {
			Field field = c.getDeclaredField(name);
			field.setAccessible(true);
			MODIFIER_FIELD.setInt(field, field.getModifiers() & -17);
			return field;
		});
	}

	private Method getMethod(Class<?> clazz, String methodName, Class<?>... params) throws ReflectiveOperationException {
		return getDeclaredMethod(computeIfAbsent(classDeclaringMethodCache, clazz, methodInfo(methodName, params), (c, methodInfo) -> {
			Method method = null;
			do {
				try {
					method = c.getDeclaredMethod(methodName, params);
				} catch (NoSuchMethodException e) {
					if (c == Object.class) {
						throw e;
					}
					c = c.getSuperclass();
				}
			} while (method == null);
			return c;
		}), methodName, params);
	}

	private Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... params) throws ReflectiveOperationException {
		return (Method)computeIfAbsent(methodCache, clazz, methodInfo(methodName, params), (c, methodInfo) -> {
			Method method = c.getDeclaredMethod(methodName, params);
			method.setAccessible(true);
			return method;
		});
	}

	@SuppressWarnings("unchecked")
	private <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... params) throws ReflectiveOperationException {
		return (Constructor<T>)computeIfAbsent(methodCache, clazz, methodInfo("<init>", params), (c, methodInfo) -> {
			Constructor<?> constructor = c.getDeclaredConstructor(params);
			constructor.setAccessible(true);
			return constructor;
		});
	}

	public static Class<?> getClass(String name) {
		return reflectiveAction(() -> Class.forName(name));
	}

	/**
	 * オブジェクトのフィールドを取得する
	 *
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 */
	public Object getField(Object obj, String fieldName) throws UncheckedReflectiveOperationException {
		return reflectiveAction(() -> getField(obj.getClass(), fieldName).get(obj));
	}

	/**
	 * オブジェクトのフィールドを取得する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 */
	public Object getField(Class<?> clazz, Object obj, String fieldName) throws UncheckedReflectiveOperationException {
		return reflectiveAction(() -> getDeclaredField(clazz, fieldName).get(obj));
	}

	/**
	 * 静的フィールドを取得する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param fieldName フィールドの名前
	 * @return 取得されたフィールドの値
	 */
	public Object getStaticField(Class<?> clazz, String fieldName) throws UncheckedReflectiveOperationException {
		return getField(clazz, null, fieldName);
	}

	/**
	 * オブジェクトのフィールドの値を変更する
	 *
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 */
	public void setField(Object obj, String fieldName, Object value) throws UncheckedReflectiveOperationException {
		runReflectiveAction(() -> getField(obj.getClass(), fieldName).set(obj, value));
	}

	/**
	 * オブジェクトのフィールドの値を変更する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param obj フィールドを取得するオブジェクト
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 */
	public void setField(Class<?> clazz, Object obj, String fieldName, Object value) throws UncheckedReflectiveOperationException {
		runReflectiveAction(() -> getField(clazz, fieldName).set(obj, value));
	}

	/**
	 * 静的フィールドの値を変更する
	 *
	 * @param clazz フィールドを宣言しているクラス
	 * @param fieldName フィールドの名前
	 * @param value 指定されたフィールドに代入する値
	 */
	public void setStaticField(Class<?> clazz, String fieldName, Object value) throws UncheckedReflectiveOperationException {
		setField(clazz, null, fieldName, value);
	}

	/**
	 * オブジェクトのメソッドを引数なしで実行する
	 *
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 */
	public Object invokeMethod(Object obj, String methodName) throws UncheckedReflectiveOperationException {
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
	 */
	public Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object[] params) throws UncheckedReflectiveOperationException {
		return reflectiveAction(() -> getMethod(obj.getClass(), methodName, paramTypes).invoke(obj, params));
	}

	/**
	 * オブジェクトのメソッドを引数なしで実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param obj メソッドを実行するオブジェクト
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 */
	public Object invokeMethod(Class<?> clazz, Object obj, String methodName) throws UncheckedReflectiveOperationException {
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
	 */
	public Object invokeMethod(Class<?> clazz, Object obj, String methodName, Class<?>[] paramTypes, Object[] params) throws UncheckedReflectiveOperationException {
		return reflectiveAction(() -> getMethod(clazz, methodName, paramTypes).invoke(obj, params));
	}

	/**
	 * 静的メソッドを引数なしで実行する
	 *
	 * @param clazz メソッドを宣言しているクラス
	 * @param methodName メソッドの名前
	 * @return メソッドの戻り値
	 */
	public Object invokeStaticMethod(Class<?> clazz, String methodName) throws UncheckedReflectiveOperationException {
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
	 */
	public Object invokeStaticMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object[] params) throws UncheckedReflectiveOperationException {
		return invokeMethod(clazz, null, methodName, paramTypes, params);
	}

	/**
	 * コンストラクタを引数なしで実行する
	 *
	 * @param clazz コンストラクタを宣言しているクラス
	 * @return コンストラクタによって生成されたインスタンス
	 */
	public <T> T invokeConstructor(Class<T> clazz) throws UncheckedReflectiveOperationException {
		return invokeConstructor(clazz, new Class[0], new Object[0]);
	}

	/**
	 * コンストラクタを引数なしで実行する
	 *
	 * @param clazz コンストラクタを宣言しているクラス
	 * @param paramTypes パラメータ配列
	 * @param params コンストラクタの引数
	 * @return コンストラクタによって生成されたインスタンス
	 */
	public <T> T invokeConstructor(Class<T> clazz, Class<?>[] paramTypes, Object[] params) throws UncheckedReflectiveOperationException {
		return reflectiveAction(() -> getConstructor(clazz, paramTypes).newInstance(params));
	}

	/**
	 * obj1のフィールドをobj2にシャローコピーします
	 *
	 * @param clazz コピーするフィールドが宣言されているクラス
	 * @param obj1 フィールドのコピー元
	 * @param obj2 フィールドのコピー先
	 */
	public void shallowCopyFields(Class<?> clazz, Object obj1, Object obj2) throws UncheckedReflectiveOperationException {
		runReflectiveAction(() -> {
			for (Field field : clazz.getDeclaredFields()) {
				if (!Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					field.set(obj2, field.get(obj1));
				}
			}
		});
	}

}

