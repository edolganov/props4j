package base.props4j.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set getUpdatedKeys(Map old, Map cur) {
		
		HashSet updatedKeys = new HashSet();
		
		for (Object e : old.entrySet()) {
			
			Map.Entry oldEntry = (Map.Entry) e;
			
			Object oldKey = oldEntry.getKey();
			if( ! cur.containsKey(oldKey)){
				updatedKeys.add(oldKey);
				continue;
			}
			
			Object oldVal = oldEntry.getValue();
			Object newVal = cur.get(oldKey);
			if(oldVal == null && newVal != null){
				updatedKeys.add(oldKey);
				continue;
			}
			if( ! oldVal.equals(newVal)){
				updatedKeys.add(oldKey);
				continue;
			}
			
		}
		for (Object e : cur.entrySet()) {
			
			Map.Entry newEntry = (Map.Entry) e;
			
			Object newKey = newEntry.getKey();
			if( ! old.containsKey(newKey)){
				updatedKeys.add(newKey);
				continue;
			}
		}
		return updatedKeys;
	}

}
