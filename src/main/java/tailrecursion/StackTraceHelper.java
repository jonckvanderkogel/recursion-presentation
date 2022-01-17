package tailrecursion;

import lombok.extern.slf4j.Slf4j;

import static tailrecursion.TailCalls.done;

@Slf4j
public class StackTraceHelper {
    public static void printStackTrace() {
        log.info(printStackTraceRecursively(new StringBuilder(), Thread.currentThread().getStackTrace(), 0).invoke().toString());
    }

    private static TailCall<StringBuilder> printStackTraceRecursively(final StringBuilder sb, final StackTraceElement[] ste, final int counter) {
        if (ste.length <= counter) {
            return done(sb);
        } else {
            sb.append(ste[counter].toString() + System.lineSeparator());
            int newCounter = counter + 1;
            return () -> printStackTraceRecursively(sb, ste, newCounter);
        }
    }
}
