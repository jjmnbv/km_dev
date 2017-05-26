package org.elasticsearch.plugin.analysis.ik;


import org.elasticsearch.common.inject.Module;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.IkAnalysisBinderProcessor;
import org.elasticsearch.indices.analysis.IKAnalysisSyncAction;
import org.elasticsearch.indices.analysis.IKIndicesAnalysisModule;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestModule;

import java.util.Collection;
import java.util.Collections;


public class AnalysisIkPlugin extends Plugin {

    @Override public String name() {
        return "analysis-ik";
    }


    @Override public String description() {
        return "ik analysis";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new IKIndicesAnalysisModule());
    }
    
    public void onModule(RestModule restModule) {
        restModule.addRestAction(IKAnalysisSyncAction.class);
    }    
    
    public void onModule(AnalysisModule module) {
        module.addProcessor(new IkAnalysisBinderProcessor());
    }

}
