package org.cubixmc.event

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Giovanni
 * Doesn't use EventPriority as seen in Bukkit-BUILD 291
 */

@Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Events {



    /**
     * @boolean type(default, false);
     */
    boolean ignoreCancelled() default false;
}