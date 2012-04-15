package FromTutorial;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class UtilizationWorkitemHandler implements WorkItemHandler {
	
	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
            Map<String, Object> results = new HashMap<String, Object>();
		    results.put("utilization", 42);
		    manager.completeWorkItem(workItem.getId(), results);
	}

}
