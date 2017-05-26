package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityStatus;

public class ActivityStatusMap {
	private static Map<Integer, String> map = null;
    private static Map<Integer, String> outDraftMap = null;
    private static Map<Integer, String> activityMap = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Integer, String>();
			map.put(ActivityStatus.DRAFT.getStatus(),
					ActivityStatus.DRAFT.getTitle());
			map.put(ActivityStatus.PRE_AUDIT.getStatus(),
					ActivityStatus.PRE_AUDIT.getTitle());
			map.put(ActivityStatus.NOT_ENTRY_TIME.getStatus(),
					ActivityStatus.NOT_ENTRY_TIME.getTitle());
			map.put(ActivityStatus.ENTRY_IN.getStatus(),
					ActivityStatus.ENTRY_IN.getTitle());
			map.put(ActivityStatus.ACTIVITY_NOT_START.getStatus(),
					ActivityStatus.ACTIVITY_NOT_START.getTitle());
			map.put(ActivityStatus.ACTIVITY_IN.getStatus(),
					ActivityStatus.ACTIVITY_IN.getTitle());
			map.put(ActivityStatus.ACTIVITY_END.getStatus(),
					ActivityStatus.ACTIVITY_END.getTitle());
			map.put(ActivityStatus.ACTIVITY_CANCELL.getStatus(),
					ActivityStatus.ACTIVITY_CANCELL.getTitle());
			map.put(ActivityStatus.ACTIVITY_STOP.getStatus(),
					ActivityStatus.ACTIVITY_STOP.getTitle());
		}
		return map;
	}

    /**
     * 获取活动活动时的状态
     * @return
     */
    public static Map<Integer, String> getActivityMap() {
        if (activityMap == null) {
            activityMap = new LinkedHashMap<Integer, String>();
            activityMap.put(ActivityStatus.NOT_ENTRY_TIME.getStatus(),
                    ActivityStatus.NOT_ENTRY_TIME.getTitle());
            activityMap.put(ActivityStatus.ENTRY_IN.getStatus(),
                    ActivityStatus.ENTRY_IN.getTitle());
            activityMap.put(ActivityStatus.ACTIVITY_NOT_START.getStatus(),
                    ActivityStatus.ACTIVITY_NOT_START.getTitle());
            activityMap.put(ActivityStatus.ACTIVITY_IN.getStatus(),
                    ActivityStatus.ACTIVITY_IN.getTitle());
            activityMap.put(ActivityStatus.ACTIVITY_END.getStatus(),
                    ActivityStatus.ACTIVITY_END.getTitle());
            activityMap.put(ActivityStatus.ACTIVITY_STOP.getStatus(),
                    ActivityStatus.ACTIVITY_STOP.getTitle());
        }
        return activityMap;
    }

	public static Map<Integer, String> getMapOutDraftCancell() {
      if (outDraftMap == null) {
          outDraftMap = new LinkedHashMap<Integer, String>();
          outDraftMap.put(ActivityStatus.NOT_ENTRY_TIME.getStatus(),
                  ActivityStatus.NOT_ENTRY_TIME.getTitle());
          outDraftMap.put(ActivityStatus.ENTRY_IN.getStatus(),
                  ActivityStatus.ENTRY_IN.getTitle());
          outDraftMap.put(ActivityStatus.ACTIVITY_NOT_START.getStatus(),
                  ActivityStatus.ACTIVITY_NOT_START.getTitle());
          outDraftMap.put(ActivityStatus.ACTIVITY_IN.getStatus(),
                  ActivityStatus.ACTIVITY_IN.getTitle());
          outDraftMap.put(ActivityStatus.ACTIVITY_END.getStatus(),
                  ActivityStatus.ACTIVITY_END.getTitle());
          outDraftMap.put(ActivityStatus.ACTIVITY_STOP.getStatus(),
                  ActivityStatus.ACTIVITY_STOP.getTitle());
      }
      return outDraftMap;
  }

	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
	
}
