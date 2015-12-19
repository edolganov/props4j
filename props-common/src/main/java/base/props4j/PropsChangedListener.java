package base.props4j;

import java.util.Set;

public interface PropsChangedListener {
	
	void onChanged(Set<String> keys);

}
