package ntut.csie.sslab.ddd.model.common;

import java.util.function.BooleanSupplier;

import static java.lang.String.format;

public class Contract {

    // TODO: To read the initial value from environment variables or system properties such as java -Dcheck-pre="true"
    public static boolean CHECK_PRE = true;
    public static boolean CHECK_POSt = true;

    public static void require(String annotation, boolean value){
        if (!CHECK_PRE)
            return;

        if (!value){
            throw new PreconditionViolationException(annotation);
        }
    }

    public static void requireNotNull(String annotation, Object obj){
        if (!CHECK_PRE)
            return;

        require(format("[%s] cannot be null", annotation), null != obj);
    }

    public static void require(String annotation, BooleanSupplier exp){
        if (!CHECK_PRE)
            return;

        require(annotation, exp.getAsBoolean());
    }

    public static boolean ensure(String annotation, boolean value){
        if (!CHECK_POSt)
            return true;

        if (!value){
            throw new PostconditionViolationException(annotation);
        }
        return true;
    }
    public static boolean ensureNotNull(String annotation, Object obj){
        if (!CHECK_PRE)
            return true;

        return ensure(format("[%s] cannot be null", annotation), null != obj);
    }
    public static boolean ensure(String annotation, BooleanSupplier exp){
        if (!CHECK_PRE)
            return true;

        return ensure(annotation, exp.getAsBoolean());
    }

}
