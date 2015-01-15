package org.cubixmc.event;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Giovanni
 * Doesn't use EventPriority as seen in Bukkit-BUILD 291
 */

@Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface EventCaller {

    /**
     * @boolean type(default, false);
     */
    boolean ignoreCancelled() default false;
}