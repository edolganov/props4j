package examples;

import base.props4j.DefVal;
import base.props4j.DefValKey;

/**
 * Example of enum keys for using in props (See "Ex5_Enum_by_key.java" file)
 *
 * @author Evgeny Dolganov
 */
public enum Keys implements DefValKey {
	
	
	planets_in_solar_system(8),
	
	is_frog_green(true),
	
	myName("Evgeny"),
	
	star_wars_episodes_count(999.999d),
	
	
	;
	
	private final DefVal val;
	
	private Keys(Object val) {
		this.val = new DefVal(val);
	}

	@Override
	public String getName() {
		return name();
	}
	
	@Override
	public DefVal getDefVal() {
		return val;
	}

}
