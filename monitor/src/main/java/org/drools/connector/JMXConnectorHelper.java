package org.drools.connector;

import org.apache.logging.log4j.Logger;
import org.drools.beans.KIESession;
import org.drools.beans.MBeanNamesRegister;
import org.kie.api.management.KieSessionMonitoringMBean;
import org.apache.logging.log4j.LogManager;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.TreeSet;

/**
 * Created by dinanjanag
 * on 3/2/2016.
 */
public class JMXConnectorHelper {

    MBeanNamesRegister mBeanNamesRegister = new MBeanNamesRegister();
    protected static final transient Logger logger = LogManager.getLogger(JMXConnectorHelper.class);

    public void extractKieSessionsAndKieBases(TreeSet<ObjectName> set, MBeanServerConnection mbsc){
        for(ObjectName mbeanName : set){
            if(mbeanName.getCanonicalName().contains(",group=Sessions,sessionId=Session-")){
                //ObjectName mbeanName1 = new ObjectName(mbeanName.getCanonicalName());
                KieSessionMonitoringMBean mBeanStub =
                        JMX.newMBeanProxy(mbsc, mbeanName, KieSessionMonitoringMBean.class, true);
                KIESession kieSession = new KIESession();
                kieSession.setKieSessionMonitoringMBean(mBeanStub);
                logger.info("Adding KieSession Mbean {}",mbeanName.getCanonicalName());
                System.out.println("Adding KieSession Mbean {}"+mbeanName.getCanonicalName());
                mBeanNamesRegister.addSession(mbeanName.getCanonicalName(),kieSession);

            }else if(mbeanName.getCanonicalName().contains("org.drools.kbases:type")
                    &&!mbeanName.getCanonicalName().contains("group=EntryPoints,EntryPoint=")){
                logger.info("Adding KieBase Mbean {}",mbeanName.getCanonicalName());
                mBeanNamesRegister.addBase(mbeanName.getCanonicalName());
            }
        }
    }


}
