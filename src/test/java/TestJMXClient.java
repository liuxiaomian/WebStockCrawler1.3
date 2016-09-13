import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author Michael
 */
public class TestJMXClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String jndiPath = "jmxrmi";
            String serverhost = "119.29.137.254";
            String serverport = "1099";
            // url=service:jmx:rmi:///jndi/rmi://192.168.8.7:8088/jmxrmi
            String jmxurl = "service:jmx:rmi:///jndi/rmi://" + serverhost + ":"
                    + serverport + "/" + jndiPath;
            System.out.println("jmxurl:" + jmxurl);
            JMXServiceURL url = new JMXServiceURL(jmxurl);
            Map<String, Object> enviMap = new HashMap<String, Object>();

            JMXConnector connector = JMXConnectorFactory.connect(url, enviMap);

            MBeanServerConnection mbsc = connector.getMBeanServerConnection();
            System.out.println("successful connected ");
            connector.close();
            System.out.println("close connect");
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
