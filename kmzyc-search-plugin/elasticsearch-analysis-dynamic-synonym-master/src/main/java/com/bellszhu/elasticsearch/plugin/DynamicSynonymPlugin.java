/**
 * 
 */
package com.bellszhu.elasticsearch.plugin;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestModule;

import com.bellszhu.elasticsearch.plugin.action.SynonymReloadAction;
import com.bellszhu.elasticsearch.plugin.synonym.analysis.DynamicSynonymTokenFilterFactory;

/**
 * @author bellszhu
 *
 */
public class DynamicSynonymPlugin extends Plugin {

	@Override
	public String description() {
		return "Analysis-plugin for synonym";
	}

	@Override
	public String name() {
		return "analysis-dynamic-synonym";
	}
	
	public void onModule(AnalysisModule module) {
        module.addTokenFilter("dynamic_synonym", DynamicSynonymTokenFilterFactory.class);
    }
	
	public void onModule(RestModule restModule) {		
       restModule.addRestAction(SynonymReloadAction.class);
    }

}
