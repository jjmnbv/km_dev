package com.kmzyc.framework.drools;


import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class RulesEngine {
    //	private static JmsTemplate jmsTemplate;
//	private static Destination destination;
    final static KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    final static KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
    final static StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession();

    static {
        knowledgeBuilder.add(ResourceFactory.newClassPathResource("drools.drl", RulesEngine.class), ResourceType.DRL);
        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
    }

    public static void insert(Object object) {

        statefulKnowledgeSession.insert(object);
        statefulKnowledgeSession.fireAllRules();
        statefulKnowledgeSession.dispose();

    }

}
