package org.mongocat.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The migration log. If change success, it will be added to the database.
 *
 * Created by @author songjhh on 2020/1/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MigrationLog {

    /**
     * Author of the migration.
     *
     * @return author
     */
    String author() default "";

    /**
     * Unique version of the migration.
     * example:
     * 0_0_1 -> v0.0.1
     * 1_1_12 -> v1.1.12
     *
     * @return migration version
     */
    String version();

    /**
     * even if it has been run before, it will run again.
     *
     * @return will run always?
     */
    boolean always() default false;

}
