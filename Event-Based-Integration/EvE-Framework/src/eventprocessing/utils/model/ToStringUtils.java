package eventprocessing.utils.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import eventprocessing.utils.TextUtils;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Für die textuelle Repräsentation von Objekten.
 * Funktioniert nicht bei enum-Typen sowie bei Klassen die
 * eine gegenseitige Referenz aufeinander besitzen.
 * 
 * @author IngoT
 *
 */
public final class ToStringUtils {

	// @param aObject the object for which a <tt>toString</tt> result is required.

	static String getText(Object aObject) {
		return getTextAvoidCyclicRefs(aObject, null, null);
	}

	/**
	 * As in {@link #getText}, but, for return values which are instances of
	 * <tt>aSpecialClass</tt>, then call <tt>aMethodName</tt> instead of
	 * <tt>toString</tt>.
	 * 
	 * <P>
	 * If <tt>aSpecialClass</tt> and <tt>aMethodName</tt> are <tt>null</tt>, then
	 * the behavior is exactly the same as calling {@link #getText}.
	 */
	static String getTextAvoidCyclicRefs(Object aObject, Class<?> aSpecialClass, String aMethodName) {
		StringBuilder result = new StringBuilder();
		addStartLine(aObject, result);

		// ruft alle Methoden der Klasse auf
		Method[] methods = aObject.getClass().getMethods();
		// für jede Methode wird geschaut, ob es sich um eine get-Methode handelt.
		for (Method method : methods) {
			if (isContributingMethod(method, aObject.getClass())) {
				// Wenn ja, wird das Feld übernommen.
				addLineForGetXXXMethod(aObject, method, result, aSpecialClass, aMethodName);
			}
		}

		addEndLine(result);
		return result.toString();
	}

	// PRIVATE //

	/*
	 * Names of methods in the <tt>Object</tt> class which are ignored.
	 */
	private static final String fGET_CLASS = "getClass";
	private static final String fCLONE = "clone";
	private static final String fHASH_CODE = "hashCode";
	private static final String fTO_STRING = "toString";

	private static final String fGET = "get";
	private static final Object[] fNO_ARGS = new Object[0];
	private static final Class<?>[] fNO_PARAMS = new Class<?>[0];
	/*
	 * Previous versions of this class indented the data within a block. That style
	 * breaks when one object references another. The indentation has been removed,
	 * but this variable has been retained, since others might prefer the
	 * indentation anyway.
	 */
	private static final String fINDENT = "";
	private static final String fAVOID_CIRCULAR_REFERENCES = "[circular reference]";
	private static final Logger fLogger = LoggerFactory.getLogger(ToStringUtils.class.getName());
	private static final String NEW_LINE = System.getProperty("line.separator");

	// prevent construction by the caller
	private ToStringUtils() {
		// empty
	}

	/**
	 * erzeugt die erste Zeile der Ausgabe.
	 * 
	 * @param aObject, welches dargestellt werden soll.
	 * @param aResult, der StringBuilder, der das Ergebnis in sich trägt.
	 */
	private static void addStartLine(Object aObject, StringBuilder aResult) {
		aResult.append(aObject.getClass().getName());
		aResult.append(" {");
		aResult.append(NEW_LINE);
	}

	/**
	 * erzeugt die letzte Zeile der Ausgabe.
	 * 
	 * @param aResult, der StringBuilder, der das Ergebnis in sich trägt.
	 */
	private static void addEndLine(StringBuilder aResult) {
		aResult.append("}");
		aResult.append(NEW_LINE);
	}

	/**
	 * Return <tt>true</tt> only if <tt>aMethod</tt> is public, takes no args,
	 * returns a value whose class is not the native class, is not a method of
	 * <tt>Object</tt>.
	 */
	private static boolean isContributingMethod(Method aMethod, Class<?> aNativeClass) {
		boolean isPublic = Modifier.isPublic(aMethod.getModifiers());
		boolean hasNoArguments = aMethod.getParameterTypes().length == 0;
		boolean hasReturnValue = aMethod.getReturnType() != Void.TYPE;
		boolean returnsNativeObject = aMethod.getReturnType() == aNativeClass;
		boolean isMethodOfObjectClass = aMethod.getName().equals(fCLONE) || aMethod.getName().equals(fGET_CLASS)
				|| aMethod.getName().equals(fHASH_CODE) || aMethod.getName().equals(fTO_STRING);
		return isPublic && hasNoArguments && hasReturnValue && !isMethodOfObjectClass && !returnsNativeObject;
	}

	private static void addLineForGetXXXMethod(Object aObject, Method aMethod, StringBuilder aResult,
			Class<?> aCircularRefClass, String aCircularRefMethodName) {
		aResult.append(fINDENT);
		aResult.append(getMethodNameMinusGet(aMethod));
		aResult.append(": ");
		Object returnValue = getMethodReturnValue(aObject, aMethod);
		if (returnValue != null && returnValue.getClass().isArray()) {
			aResult.append(TextUtils.getArrayAsString(returnValue));
		} else {
			if (aCircularRefClass == null) {
				aResult.append(returnValue);
			} else {
				if (aCircularRefClass == returnValue.getClass()) {
					Method method = getMethodFromName(aCircularRefClass, aCircularRefMethodName);
					if (isContributingMethod(method, aCircularRefClass)) {
						returnValue = getMethodReturnValue(returnValue, method);
						aResult.append(returnValue);
					} else {
						aResult.append(fAVOID_CIRCULAR_REFERENCES);
					}
				}
			}
		}
		aResult.append(NEW_LINE);
	}

	private static String getMethodNameMinusGet(Method aMethod) {
		String result = aMethod.getName();
		if (result.startsWith(fGET)) {
			result = result.substring(fGET.length());
		}
		return result;
	}

	/** Return value is possibly-null. */
	private static Object getMethodReturnValue(Object aObject, Method aMethod) {
		Object result = null;
		try {
			result = aMethod.invoke(aObject, fNO_ARGS);
		} catch (IllegalAccessException ex) {
			vomit(aObject, aMethod);
		} catch (InvocationTargetException ex) {
			vomit(aObject, aMethod);
		}

		return result;
	}

	private static Method getMethodFromName(Class<?> aSpecialClass, String aMethodName) {
		Method result = null;
		try {
			result = aSpecialClass.getMethod(aMethodName, fNO_PARAMS);
		} catch (NoSuchMethodException ex) {
			vomit(aSpecialClass, aMethodName);
		}
		return result;
	}

	private static void vomit(Object aObject, Method aMethod) {
		fLogger.severe("Cannot get return value using reflection. Class: " + aObject.getClass().getName() + " Method: "
				+ aMethod.getName());
	}

	private static void vomit(Class<?> aSpecialClass, String aMethodName) {
		fLogger.severe("Reflection fails to get no-arg method named: " + TextUtils.quote(aMethodName) + " for class: "
				+ aSpecialClass.getName());
	}

}
