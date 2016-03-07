package org.drools.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.management.KieSessionMonitoringImpl;
import org.kie.api.management.KieManagementAgentMBean;
import org.kie.api.management.KieSessionMonitoringMBean;
import javax.management.*;
import javax.management.openmbean.TabularDataSupport;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dinanjanag
 * on 3/1/2016.
 */
public class JMXConnector {

    private static String jmxServiceURL = "service:jmx:rmi:///jndi/rmi://:9999/jmxrmi";
    private static MBeanServerConnection mbsc= null;
    private static TreeSet<ObjectName> mbeansNames = null;
    protected static final transient Logger logger = LogManager.getLogger(JMXConnector.class);
    public static MBeanServerConnection createConnectionToMbeanServer(){
        JMXServiceURL url = null;
        try {
            url = new JMXServiceURL(jmxServiceURL);
            javax.management.remote.JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            echo("\nGet a MBeanServerConnection");
            mbsc = jmxc.getMBeanServerConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mbsc;
        }
    }

    public static  void getAllMbeansNames(){
        if(mbsc!= null){
            try {
                logger.info("Getting Mbean Names from the MBean server");
                System.out.println("Getting Mbean Names from the MBean server ");
                mbeansNames= new TreeSet<ObjectName>(mbsc.queryNames(null, null));
                for (ObjectName name : mbeansNames) {
                    echo("\tObjectName = " + name.getCanonicalName());
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        createConnectionToMbeanServer();
        getAllMbeansNames();
    }

    public static TreeSet<ObjectName> getMbeansNames() {
        return mbeansNames;
    }

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




            mbeanName = new ObjectName("org.drools.kbases:type=bedb7aa0-29f7-4842-8ac7-434497707ddd,group=Sessions,sessionId=Session-0");
            KieSessionMonitoringMBean mbeanProxy1 = JMX.newMBeanProxy(mbsc, mbeanName,KieSessionMonitoringMBean.class, true);

            echo(mbeanProxy1.getTotalFactCount()+" "+ mbeanProxy1.getTotalMatchesCreated()+
                    " "+ mbeanProxy1.getTotalProcessInstancesCompleted());

            echo(mbeanProxy1.getKieSessionId() + " " + mbeanProxy1.getKieBaseId());

            if(mbeanProxy1 instanceof KieSessionMonitoringImpl ){
                KieSessionMonitoringImpl kieSessionMonitoring = (KieSessionMonitoringImpl) mbeanProxy1;
                echo(kieSessionMonitoring.getKbase().getId());

            }


            echo(mbeanProxy1.getStatsByRule().toString());
//

            mbeanName = new ObjectName("org.drools.kbases:type=bedb7aa0-29f7-4842-8ac7-434497707ddd");
            TabularDataSupport tabularType = (TabularDataSupport) mbsc.getAttribute(mbeanName, "Globals");

            echo(tabularType.getTabularType().toString());


            String []signature = null;

            mbsc.invoke(mbeanName,"startInternalMBeans",null,null);

            Set<ObjectName> names2 =
                    new TreeSet<ObjectName>(mbsc.queryNames(null, null));
            for (ObjectName name : names2) {
                echo("\tObjectName = " + name);
            }

            echo("\nClose the connection to the server");
            jmxc.close();
            echo("\nBye! Bye!");

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();

//        } catch (NotCompliantMBeanException e) {
//            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
//        } catch (InstanceAlreadyExistsException e) {
//            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
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
