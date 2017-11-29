package com.bh.timetracker.utility;

import java.util.HashMap;
import java.util.Map;

import com.bh.timetracker.entity.User;

public class DataFacade {
	static Map map = new HashMap();

	public static void addValue(String key, Object value) {
		map.put(key, value);
	}
}
