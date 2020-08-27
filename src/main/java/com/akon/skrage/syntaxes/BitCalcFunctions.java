package com.akon.skrage.syntaxes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.registrations.Classes;

public class BitCalcFunctions {

	static {
		ClassInfo<Number> numberClass = Classes.getExactClassInfo(Number.class);
		Functions.registerFunction(new JavaFunction<Number>("and", new Parameter[]{new Parameter<>("val1", numberClass, true, null), new Parameter<>("val2", numberClass, true, null)}, numberClass, true) {

			@Override
			public Number[] execute(FunctionEvent e, Object[][] params) {
				long param1 = ((Number)params[0][0]).longValue();
				long param2 = ((Number)params[0][1]).longValue();
				return new Number[]{param1 & param2};
			}

		});
		Functions.registerFunction(new JavaFunction<Number>("not", new Parameter[]{new Parameter<>("val", numberClass, true, null)}, numberClass, true) {

			@Override
			public Number[] execute(FunctionEvent e, Object[][] params) {
				long param1 = ((Number)params[0][0]).longValue();
				return new Number[]{~ param1};
			}

		});
		Functions.registerFunction(new JavaFunction<Number>("or", new Parameter[]{new Parameter<>("val1", numberClass, true, null), new Parameter<>("val2", numberClass, true, null)}, numberClass, true) {

			@Override
			public Number[] execute(FunctionEvent e, Object[][] params) {
				long param1 = ((Number)params[0][0]).longValue();
				long param2 = ((Number)params[0][1]).longValue();
				return new Number[]{param1 | param2};
			}

		});
		Functions.registerFunction(new JavaFunction<Number>("xor", new Parameter[]{new Parameter<>("val1", numberClass, true, null), new Parameter<>("val2", numberClass, true, null)}, numberClass, true) {

			@Override
			public Number[] execute(FunctionEvent e, Object[][] params) {
				long param1 = ((Number)params[0][0]).longValue();
				long param2 = ((Number)params[0][1]).longValue();
				return new Number[]{param1 ^ param2};
			}

		});
	}

}
