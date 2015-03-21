package liu.chun.dong.common.util;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:53
 * @comment JMXUtil
 */
public class JMXUtil {
    public static ObjectName register(String name, Object mbean) {
        try {
            ObjectName objectName = new ObjectName(name);

            MBeanServer mbeanServer = ManagementFactory
                    .getPlatformMBeanServer();

            try {
                mbeanServer.registerMBean(mbean, objectName);
            } catch (InstanceAlreadyExistsException ex) {
                mbeanServer.unregisterMBean(objectName);
                mbeanServer.registerMBean(mbean, objectName);
            }

            return objectName;
        } catch (JMException e) {
            throw new IllegalArgumentException(name, e);
        }
    }

    public static void unregister(String name) {
        try {
            MBeanServer mbeanServer = ManagementFactory
                    .getPlatformMBeanServer();

            mbeanServer.unregisterMBean(new ObjectName(name));
        } catch (InstanceNotFoundException e) {

        } catch (JMException e) {
            throw new IllegalArgumentException(name, e);
        }

    }
}
