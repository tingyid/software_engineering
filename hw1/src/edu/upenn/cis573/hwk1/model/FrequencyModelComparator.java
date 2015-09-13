package edu.upenn.cis573.hwk1.model;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for sorting HashMaps by value
 * in FrequencyModels
 * @author davehand
 */
public final class FrequencyModelComparator implements Comparator<Object> {
	@SuppressWarnings("unchecked")
	public int compare(Object lhs, Object rhs) {
		Integer leftInt = (Integer) (((Map.Entry<Character,Integer>) lhs).getValue());
		Integer rightInt = (Integer) (((Map.Entry<Character,Integer>) rhs).getValue());
		return leftInt.compareTo(rightInt);
	}
}
