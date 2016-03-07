package org.drools.connector;

import javax.management.MBeanServerConnection;

/**
 * Created by dinanjanag
 * on 3/4/2016.
 */
public class JMXConnectorThread extends Thread {

    private MBeanServerConnection mbs;
    private JMXConnectorHelper jmxConnectorHelper = new JMXConnectorHelper();
    public void initialize(){
        mbs = JMXConnector.createConnectionToMbeanServer();

    }
    public void run(){

        initialize();
        while (true){
            JMXConnector.getAllMbeansNames();
            jmxConnectorHelper.extractKieSessionsAndKieBases(JMXConnector.getMbeansNames(),mbs);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }
}
