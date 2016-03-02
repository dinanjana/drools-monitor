package org.drools.connector;

import org.drools.core.management.DroolsManagementAgent;
import org.drools.core.management.DroolsManagementAgentBean;
import org.drools.core.management.KieSessionMonitoringImpl;
import org.kie.api.management.KieManagementAgentMBean;
import org.kie.api.management.KieSessionMonitoringMBean;

import javax.management.*;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dinanjanag on 3/1/2016.
 */
public class JMXConnector {

    public static void connectToMbeanServer() {
        echo("\nCreate an RMI connector client and " +
                "connect it to the RMI connector server");
        JMXServiceURL url =
                null;
        try {
            url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");

            javax.management.remote.JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

            // Create listener
            //
            ClientListener listener = new ClientListener();

            // Get an MBeanServerConnection
            //
            echo("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            // Get domains from MBeanServer
            //
            echo("\nDomains:");
            String domains[] = mbsc.getDomains();
            Arrays.sort(domains);
            for (String domain : domains) {
                echo("\tDomain = " + domain);
            }

            // Get MBeanServer's default domain
            //
            echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

            // Get MBean count
            //
            echo("\nMBean count = " + mbsc.getMBeanCount());

            // Query MBean names
            //
            echo("\nQuery MBeanServer MBeans:");
            Set<ObjectName> names =
                    new TreeSet<ObjectName>(mbsc.queryNames(null, null));
            for (ObjectName name : names) {
                echo("\tObjectName = " + name);
            }
            ObjectName mbeanName = new ObjectName("org.drools:type=DroolsManagementAgent");

            KieManagementAgentMBean mbeanProxy =
                    JMX.newMBeanProxy(mbsc, mbeanName,KieManagementAgentMBean.class, true);

            //mbeanProxy.getKieBaseCount();
            echo("\nSession Count :" + mbeanProxy.getSessionCount());
            echo("\nBase Count :" + mbeanProxy.getKieBaseCount());



            mbeanName = new ObjectName("org.drools.kbases:type=dfa0f269-ea9a-4c5c-ba41-10ae8648a32b,group=Sessions,sessionId=Session-0");
            KieSessionMonitoringMBean mbeanProxy1 = JMX.newMBeanProxy(mbsc, mbeanName,KieSessionMonitoringMBean.class, true);


            echo(mbeanProxy1.getTotalFactCount()+" "+ mbeanProxy1.getTotalMatchesCreated()+
                    " "+ mbeanProxy1.getTotalProcessInstancesCompleted());
//            mbeanProxy1.
            echo("\nClose the connection to the server");
            jmxc.close();
            echo("\nBye! Bye!");

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }
    private static void echo(String msg) {
        System.out.println(msg);
    }

    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification,
                                       Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass().getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }


}
