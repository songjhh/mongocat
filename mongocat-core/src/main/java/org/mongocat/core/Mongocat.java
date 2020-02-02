package org.mongocat.core;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by @author songjhh on 2020/1/29
 */
@Slf4j
public class Mongocat {

    @Setter
    private boolean enabled;

    private String mongocatLogsPackage;

    public Mongocat(boolean enabled, String mongocatLogsPackage) {
        this.enabled = enabled;
        this.mongocatLogsPackage = mongocatLogsPackage;
    }

    /**
     * execute mongocat
     */
    public void execute() {
        if (!this.enabled) {
            log.info("Mongocat is disabled. Exiting.");
            return;
        }
        // TODO: lock

        // get methods
        Reflections reflections = new Reflections(mongocatLogsPackage);
        Set<Method> migrationMethodSet = reflections.getMethodsAnnotatedWith(MigrationLog.class);
        List<Method> migrationMethodList = new ArrayList<>(migrationMethodSet);
        migrationMethodList.sort(new MigrationMethodListComparator());
    }

    private static class MigrationMethodListComparator implements Comparator<Method> {
        @Override
        public int compare(Method o1, Method o2) {
            MigrationLog migrationLog1 = o1.getDeclaredAnnotation(MigrationLog.class);
            MigrationLog migrationLog2 = o2.getDeclaredAnnotation(MigrationLog.class);
            String version1 = migrationLog1.version();
            String version2 = migrationLog2.version();
            String[] split1 = version1.split("_");
            String[] split2 = version2.split("_");
            for (int i = 0; i < 3; i++) {
                int i1 = Integer.getInteger(split1[i]);
                int i2 = Integer.getInteger(split2[i]);
                if (i1 != i2) {
                    return i1 > i2 ? 1 : -1;
                }
            }
            return 0;
        }
    }


}
