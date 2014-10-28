package Utilities;

import java.util.List;

public class ChangeManager {

	private List<ChangeIndicator> indicatorList;
	
	private ChangeIndicator getElementWithId(int id) {
		ChangeIndicator ci = null;
		for(int i=0; i<indicatorList.size(); i++) {
			if(indicatorList.get(i).getId() == id) ci = indicatorList.get(i);
		}
		
		if(ci == null) {
			ci = new ChangeIndicator();
			ci.setId(id);
			indicatorList.add(ci);
		}
		
		return ci;
	}
	
	public void changeIndicatorValue(int lectureId, boolean question, boolean speed, boolean pause) {
		
	}
}
