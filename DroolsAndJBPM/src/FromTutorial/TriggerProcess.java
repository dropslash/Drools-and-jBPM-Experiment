package FromTutorial;
//package de.doehring.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;

public class TriggerProcess {
public static void main(String[] args) throws Exception {
		KnowledgeBase kbase = readKnowledgeBase();

		//create Drools Knowledge Session
		final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		WSHumanTaskHandler wsh = new WSHumanTaskHandler();
		wsh.setConnection("127.0.0.1", 9123);
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", wsh);
		ksession.getWorkItemManager().registerWorkItemHandler("Utilization", new UtilizationWorkitemHandler());
		
		new Thread(new Runnable() {
			  public void run() {
			    ksession.fireUntilHalt();
			  }
			}).start();
		
		Thread.sleep(2000);
		ksession.getWorkingMemoryEntryPoint("DEFAULT").insert(new WarningEvent(2, "HARDWARE"));
		Thread.sleep(2000);
		ksession.getWorkingMemoryEntryPoint("DEFAULT").insert(new WarningEvent(2, "HARDWARE"));
		Thread.sleep(2000);
		ksession.getWorkingMemoryEntryPoint("DEFAULT").insert(new WarningEvent(2, "HARDWARE"));
		
	}
		
	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		config.setOption( EventProcessingOption.STREAM ); 
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("de/doehring/drools/startprocess.drl"), ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource("de/doehring/drools/decide247.drl"), ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource("de/doehring/drools/demoworkflow.bpmn"), ResourceType.BPMN2);
		KnowledgeBase kb = KnowledgeBaseFactory.newKnowledgeBase(config);
        kb.addKnowledgePackages(kbuilder.getKnowledgePackages());

        if (kbuilder.hasErrors()) System.out.println(kbuilder.getErrors());
		return kb;
	}
}
