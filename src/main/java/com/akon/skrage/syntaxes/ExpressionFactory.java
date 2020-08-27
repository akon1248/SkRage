package com.akon.skrage.syntaxes;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.SkRage;
import com.akon.skrage.utils.ReflectionUtil;
import lombok.Getter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;

//ByteBuddyを使って動的にExpressionを作成、登録する
public class ExpressionFactory {

	public static <T, U> void registerExpression(String className, String pattern, Class<T> type, Class<U> returnType, Function<T, U> getter, @Nullable BiConsumer<T, U> setter, @Nullable String... description) {
		ExpressionMethodInterceptor<T, U> methodInterceptor = new ExpressionMethodInterceptor<>(pattern, type, returnType, getter, setter);
		try {
			DynamicType.Builder<?> builder = new ByteBuddy()
				.subclass(TypeDescription.Generic.Builder.parameterizedType(SimpleExpression.class, methodInterceptor.getSingleReturnType()).build())
				.name("com.akon.skrage.syntaxes.expressions." + className);
			if (description != null) {
				builder = builder.annotateType(AnnotationDescription.Builder.ofType(Description.class).defineArray("value", description).build());
			}
			Class<?> clazz = builder
				.method(setter == null ? ElementMatchers.isAbstract() : ElementMatchers.isAbstract().or(ElementMatchers.named("acceptChange")).or(ElementMatchers.named("change")))
				.intercept(MethodDelegation.to(methodInterceptor))
				.make()
				.load(ExpressionFactory.class.getClassLoader())
				.getLoaded();
			ReflectionUtil.invokeStaticMethod(Skript.class, "registerExpression", new Class[]{Class.class, Class.class, ExpressionType.class, String[].class}, new Object[]{clazz, methodInterceptor.getSingleReturnType(), ExpressionType.COMBINED, methodInterceptor.getPatterns()});
		} catch (Exception ex) {
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			Logger logger = SkRage.getInstance().getLogger();
			logger.warning("Expressionの登録に失敗しました: " + methodInterceptor.getPatterns()[0]);
			Arrays.stream(writer.toString().split(System.lineSeparator())).forEach(logger::warning);
		}
	}

	public static class ExpressionMethodInterceptor<T, U> {

		@Getter
		private final String[] patterns;
		@Getter
		private final boolean isEvent;
		@Getter
		private final Function<T, U> getter;
		@Getter
		private final BiConsumer<T, U> setter;
		@Getter
		private final Class<T> type;
		@Getter
		private final Class<U> returnType;
		@Getter
		private Class<?> singleReturnType;
		private WeakHashMap<SimpleExpression<?>, Expression<?>> exprs = new WeakHashMap<>();

		public ExpressionMethodInterceptor(String pattern, Class<T> type, Class<U> returnType, Function<T, U> getter, BiConsumer<T, U> setter) {
			this.isEvent = Event.class.isAssignableFrom(type);
			if (this.isEvent) {
				this.patterns = new String[]{pattern};
			} else {
				ClassInfo<?> classInfo = Classes.getExactClassInfo(type);
				String typeName = classInfo == null ? "object" : classInfo.getCodeName();
				this.patterns = new String[]{pattern + " of %" + typeName + "%", "%" + typeName + "%'s " + pattern};
			}
			this.getter = getter;
			this.setter = setter;
			this.type = type;
			this.returnType = returnType;
			this.singleReturnType = this.returnType.isArray() ? this.returnType.getComponentType() : this.returnType;
		}

		private Object[] get(SimpleExpression<?> thiz, Event event) {
			boolean flag = false;
			Object obj = null;
			if (this.isEvent) {
				if (this.type.isInstance(event)) {
					obj = event;
					flag = true;
				}
			} else if (this.exprs.get(thiz) != null) {
				if (this.type.isInstance(obj = this.exprs.get(thiz).getSingle(event))) {
					flag = true;
				}
			}
			if (flag) {
				Object result = this.getter.apply((T)obj);
				if (result != null && result.getClass().isArray()) {
					return (Object[])result;
				}
				Object arr = Array.newInstance(this.singleReturnType, 1);
				Array.set(arr, 0, result);
				return (Object[])arr;
			}
			return null;
		}

		@RuntimeType
		public Object intercept(@This SimpleExpression<?> thiz, @Origin Method method, @AllArguments Object[] params) {
			if (method.getName().equals("init")) {
				if (!this.isEvent){
					this.exprs.put(thiz, ((Expression<?>[]) params[0])[0]);
				} else if (!ScriptLoader.isCurrentEvent((Class<? extends Event>)this.type)) {
					Skript.error(this.patterns[0] + "は" + this.type.getSimpleName() + "でのみ使用可能です");
					return false;
				}
				return true;
			} else if (method.getName().equals("isSingle")) {
				return this.singleReturnType == this.returnType;
			} else if (method.getName().equals("getReturnType")) {
				return this.singleReturnType;
			} else if (method.getName().equals("toString")) {
				return this.patterns[0];
			} else if (method.getName().equals("get")) {
				return this.get(thiz, (Event)params[0]);
			} else if (method.getName().equals("acceptChange") && this.setter != null) {
				Changer.ChangeMode mode = (Changer.ChangeMode)params[0];
				if (mode == Changer.ChangeMode.SET || (this.returnType.equals(Number.class) && (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE))) {
					return CollectionUtils.array(this.returnType);
				}
			} else if (method.getName().equals("change") && this.setter != null) {
				boolean flag = false;
				Object obj = null;
				Event event = (Event)params[0];
				if (this.isEvent) {
					if (this.type.isInstance(event)) {
						obj = event;
						flag = true;
					}
				} else if (this.exprs.get(thiz) != null) {
					if (this.type.isInstance(obj = this.exprs.get(thiz).getSingle(event))) {
						flag = true;
					}
				}
				if (flag) {
					Changer.ChangeMode mode = (Changer.ChangeMode)params[2];
					Object value = this.singleReturnType == this.returnType ? ((Object[])params[1])[0] : params[1];
					if (this.returnType.isInstance(value)) {
						Object newValue = null;
						if (mode == Changer.ChangeMode.SET) {
							newValue = value;
						} else if (mode == Changer.ChangeMode.ADD && this.returnType.equals(Number.class)) {
							newValue = (((Number)this.get(thiz, event)[0]).doubleValue() + ((Number)value).doubleValue());
						} else if (mode == Changer.ChangeMode.REMOVE && this.returnType.equals(Number.class)) {
							newValue = (((Number)this.get(thiz, event)[0]).doubleValue() - ((Number)value).doubleValue());
						}
						this.setter.accept((T)obj, (U)newValue);
					}
				}
			}
			return null;
		}

	}

}
