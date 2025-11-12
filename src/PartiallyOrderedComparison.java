import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */

/**
 * @author gary
 *
 */
public enum PartiallyOrderedComparison {
	INCOMPARABLE, EQUAL, LESS_THAN, GREATER_THAN;
	
	@Override
	public String toString() {
		switch(this) {
		case INCOMPARABLE:
			return "incomparable";
		case EQUAL:
			return "equal";
		case LESS_THAN:
			return "less than";
		case GREATER_THAN:
			return "greater than";
		default:
			System.err.println("PANIC!");
			System.exit(1);
		}
		// Should never get here
		return null;
	}
	
	public boolean le() {
		return (this == EQUAL || this == LESS_THAN);
	}
	
	public boolean ge() {
		return (this == EQUAL || this == GREATER_THAN);
	}
	
	public Integer asComparatorInteger() {
		switch(this) {
		case INCOMPARABLE:
			return null;
		case EQUAL:
			return 0;
		case LESS_THAN:
			return -1;
		case GREATER_THAN:
			return 1;
		default:
			System.err.println("PANIC!");
			System.exit(1);
		}
		// Should never get here
		return null;
	}
	
	public static PartiallyOrderedComparison fromString(String cmp) {
		if(cmp == null || cmp.equalsIgnoreCase("incmp") || cmp.equalsIgnoreCase("incomparable")) {
			return INCOMPARABLE;
		}
		else if(cmp.equalsIgnoreCase("equal") || cmp.equalsIgnoreCase("eq")) {
			return EQUAL;
		}
		else if(cmp.equalsIgnoreCase("less than")
				|| cmp.equalsIgnoreCase("lt")
				|| cmp.equalsIgnoreCase("less-than")
				|| cmp.equalsIgnoreCase("less_than")
				|| cmp.equalsIgnoreCase("less.than")) {
			return LESS_THAN;
		}
		else if(cmp.equalsIgnoreCase("greater than") || cmp.equalsIgnoreCase("more than")
				|| cmp.equalsIgnoreCase("gt")
				|| cmp.equalsIgnoreCase("greater-than") || cmp.equalsIgnoreCase("more-than")
				|| cmp.equalsIgnoreCase("greater_than") || cmp.equalsIgnoreCase("more_than")
				|| cmp.equalsIgnoreCase("greater.than") || cmp.equalsIgnoreCase("more.than")) {
			return GREATER_THAN;
		}
		else {
			System.err.println("Invalid comparison string \"" + cmp + "\"");
			System.exit(1);
		}
		// Should never get here, but we have to keep the compiler happy
		return null;
	}
	
	public static PartiallyOrderedComparison fromInteger(Integer cmp) {
		if(cmp == null) {
			return INCOMPARABLE;
		}
		else if(cmp == 0) {
			return EQUAL;
		}
		else if(cmp < 0) {
			return LESS_THAN;
		}
		else if(cmp > 0) {
			return GREATER_THAN;
		}
		else {
			System.err.println("PANIC!");
			System.exit(1);
		}
		// Should never get here
		return null;
	}
	
	public static PartiallyOrderedComparison cmp(double src, double obj, double ref) {
		if(!(Double.isFinite(src) && Double.isFinite(obj) && Double.isFinite(ref))) {
			return INCOMPARABLE;
		}
			
		double src_diff = Math.abs(src - ref);
		double obj_diff = Math.abs(obj - ref);
		
		double diff_diff = src_diff - obj_diff;
		if(diff_diff == 0) {
			return EQUAL;
		}
		else if(diff_diff < 0) {
			return LESS_THAN;
		}
		else if(diff_diff > 0) {
			return GREATER_THAN;
		}
		else {
			System.err.println("PANIC!");
			System.exit(1);
		}
		// Should never get here
		return null;
	}
	
	public static PartiallyOrderedComparison cmp(long src, long obj, long ref) {
		long src_diff = Math.abs(src - ref);
		long obj_diff = Math.abs(obj - ref);
		
		long diff_diff = src_diff - obj_diff;
		if(diff_diff == 0) {
			return EQUAL;
		}
		else if(diff_diff < 0) {
			return LESS_THAN;
		}
		else if(diff_diff > 0) {
			return GREATER_THAN;
		}
		else {
			System.err.println("PANIC!");
			System.exit(1);
		}
		// Should never get here
		return null;
	}
	
	/**
	 * String src is closer to String ref than is String obj iff everywhere that
	 * obj is equal to ref, src is also equal to ref. None of the strings have
	 * to be the same length.
	 * @param src
	 * @param obj
	 * @param ref
	 * @return
	 */
	public static PartiallyOrderedComparison cmp(String src, String obj, String ref) {
		if(src == null || obj == null || ref == null) {
			return INCOMPARABLE;
		}
		PartiallyOrderedComparison result = EQUAL;
		for(int i = 0; i < ref.length(); i++) {
			char r = ref.charAt(i);
			char s = (i < src.length()) ? src.charAt(i) : '\0';
			char o = (i < obj.length()) ? obj.charAt(i) : '\0';
			if(s == '\0' && o == '\0') {
				return result;
			}
			else if(s == r && o != r) {
				if(result == EQUAL) {
					result = LESS_THAN;
				}
				else if(result != LESS_THAN) {
					return INCOMPARABLE;
				}
			}
			else if(s != r && o == r) {
				if(result == EQUAL) {
					result = GREATER_THAN;
				}
				else if(result != GREATER_THAN) {
					return INCOMPARABLE;
				}
			}
		}
		return result;
	}
	
	public static <T> PartiallyOrderedComparison cmp(Set<T> src, Set<T> obj, Set<T> ref) {
		if(src == null || obj == null || ref == null) {
			return INCOMPARABLE;
		}
		
		PartiallyOrderedComparison result = EQUAL;
		for(T item: ref) {
			if(src.contains(item) && !obj.contains(item)) {
				if(result == EQUAL) {
					result = LESS_THAN;
				}
				else if(result != LESS_THAN) {
					return INCOMPARABLE;
				}
			}
			else if(!src.contains(item) && !obj.contains(item)) {
				if(result == EQUAL) {
					result = GREATER_THAN;
				}
				else if(result != GREATER_THAN) {
					return INCOMPARABLE;
				}
			}
		}
		return result;
	}
	
	public static <T> PartiallyOrderedComparison cmp(List<T> src, List<T> obj, List<T> ref) {
		if(src == null || obj == null || ref == null) {
			return INCOMPARABLE;
		}

		PartiallyOrderedComparison result = EQUAL;
		for(int i = 0; i < ref.size(); i++) {
			T r = ref.get(i);
			T s = (i < src.size()) ? src.get(i) : null;
			T o = (i < obj.size()) ? obj.get(i) : null;
			
			if(s == null && o == null) {
				return result;
			}
			else if((s != null && s.equals(r)) && (o == null || !o.equals(r))) {
				if(result == EQUAL) {
					result = LESS_THAN;
				}
				else if(result != LESS_THAN) {
					return INCOMPARABLE;
				}
			}
			else if((s == null || !s.equals(r)) && (o != null && o.equals(r))) {
				if(result == EQUAL) {
					result = GREATER_THAN;
				}
				else if(result != GREATER_THAN) {
					return INCOMPARABLE;
				}
			}
		}
		return result;
	}
	
	public static <T, U> PartiallyOrderedComparison cmp(Map<T, U> src, Map<T, U> obj, Map<T, U> ref, Hook<U> hook) {
		if(src == null || obj == null || ref == null) {
			return INCOMPARABLE;
		}

		Set<T> r_keys = ref.keySet();
		Set<T> s_keys = src.keySet();
		Set<T> o_keys = obj.keySet();
		
		if(s_keys.containsAll(r_keys) && o_keys.containsAll(r_keys)) {
			PartiallyOrderedComparison result = EQUAL;
			for(T r_key: r_keys) {
				U r = ref.get(r_key);
				U s = ref.get(r_key);
				U o = ref.get(r_key);
				
				PartiallyOrderedComparison u_result = hook.cmp(s, o, r);
				
				if(u_result == LESS_THAN) {
					if(result == EQUAL) {
						result = LESS_THAN;
					}
					else if(result != LESS_THAN) {
						return INCOMPARABLE;
					}
				}
				else if(u_result == GREATER_THAN) {
					if(result == EQUAL) {
						result = GREATER_THAN;
					}
					else if(result != GREATER_THAN) {
						return INCOMPARABLE;
					}
				}
			}
			return result;
		}
		else {
			return cmp(s_keys, o_keys, r_keys);
		}
	}
	
	public static <T> PartiallyOrderedComparison cmp(T src, T obj, T ref, Hook<T> hook) {
		return hook.cmp(src, obj, ref);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PartiallyOrderedComparison cmp(Object src, Object obj, Object ref) {
		if(src == null || obj == null || ref == null) {
			return INCOMPARABLE;
		}
		if(src.getClass().isInstance(ref) && obj.getClass().isInstance(ref)) {
			
			if(ref instanceof Double) {
				return cmp((double)src, (double)obj, (double)ref);
			}
			else if(ref instanceof Float) {
				return cmp((double)src, (double)obj, (double)ref);
			}
			else if(ref instanceof Long) {
				return cmp((long)src, (long)obj, (long)ref);
			}
			else if(ref instanceof Integer) {
				return cmp((long)src, (long)obj, (long)ref);
			}
			else if(ref instanceof Short) {
				return cmp((long)src, (long)obj, (long)ref);
			}
			else if(ref instanceof Character) {
				return cmp((long)src, (long)obj, (long)ref);
			}
			else if(ref instanceof Byte) {
				return cmp((long)src, (long)obj, (long)ref);
			}
			else if(ref instanceof Boolean) {
				return cmp((boolean)src ? 1L : 0L, (boolean)obj ? 1L : 0L, (boolean)ref ? 1L : 0L);
			}
			else if(ref instanceof String) {
				return cmp((String)src, (String)obj, (String)ref);
			}
			else if(ref instanceof Set) {
				return cmp((Set)src, (Set)obj, (Set)ref);
			}
			else if(ref instanceof List) {
				return cmp((List)src, (List)obj, (List)ref);
			}
			else if(ref instanceof Map) {
				return cmp((Map)src, (Map)obj, (Map)ref);
			}
			else {
				return INCOMPARABLE;
			}
		}
		else if(src.getClass().isInstance(ref)) {
			return LESS_THAN;
		}
		else if(obj.getClass().isInstance(ref)) {
			return GREATER_THAN;
		}
		else {
			return INCOMPARABLE;
		}
	}
	
	public static Hook<Double> getDoubleHook() {
		return INCOMPARABLE.new DoubleHook();
	}
	
	public static Hook<Float> getFloatHook() {
		return INCOMPARABLE.new FloatHook();
	}
	
	public static Hook<Long> getLongHook() {
		return INCOMPARABLE.new LongHook();
	}
	
	public static Hook<Integer> getIntHook() {
		return INCOMPARABLE.new IntHook();
	}
	
	public static Hook<Short> getShortHook() {
		return INCOMPARABLE.new ShortHook();
	}
	
	public static Hook<Character> getCharHook() {
		return INCOMPARABLE.new CharHook();
	}
	
	public static Hook<Byte> getByteHook() {
		return INCOMPARABLE.new ByteHook();
	}
	
	public static Hook<Boolean> getBoolHook() {
		return INCOMPARABLE.new BoolHook();
	}
	
	public static Hook<String> getStringHook() {
		return INCOMPARABLE.new StringHook();
	}
	
	public static <T> Hook<Set<T>> getSetHook(T obj) {
		return INCOMPARABLE.new SetHook<T>();
	}
	
	public static <T> Hook<List<T>> getListHook(T obj) {
		return INCOMPARABLE.new ListHook<T>();
	}
	
	public static <T, U> Hook<Map<T, U>> getMapHook(T obj, Hook<U> u_hook) {
		return INCOMPARABLE.new MapHook<T, U>(u_hook);
	}
	
	public interface Hook<T> {
		PartiallyOrderedComparison cmp(T src, T obj, T ref);
	}
	
	private class DoubleHook implements Hook<Double> {
		
		@Override
		public PartiallyOrderedComparison cmp(Double src, Double obj, Double ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp(src, obj, ref);
		}
		
	}
	
	private class FloatHook implements Hook<Float> {
		
		@Override
		public PartiallyOrderedComparison cmp(Float src, Float obj, Float ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp((double)src, (double)obj, (double)ref);
		}
		
	}
	
	private class LongHook implements Hook<Long> {

		@Override
		public PartiallyOrderedComparison cmp(Long src, Long obj, Long ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp(src, obj, ref);
		}
		
	}
	
	private class IntHook implements Hook<Integer> {
		
		@Override
		public PartiallyOrderedComparison cmp(Integer src, Integer obj, Integer ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp((long)src, (long)obj, (long)ref);
		}
		
	}
	
	private class ShortHook implements Hook<Short> {
		
		@Override
		public PartiallyOrderedComparison cmp(Short src, Short obj, Short ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp((long)src, (long)obj, (long)ref);
		}
	}
	
	private class CharHook implements Hook<Character> {
		
		@Override
		public PartiallyOrderedComparison cmp(Character src, Character obj, Character ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp((long)src, (long)obj, (long)ref);
		}
	}
	
	private class ByteHook implements Hook<Byte> {
		
		@Override
		public PartiallyOrderedComparison cmp(Byte src, Byte obj, Byte ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp((long)src, (long)obj, (long)ref);
		}
	}
	
	private class BoolHook implements Hook<Boolean> {
		
		@Override
		public PartiallyOrderedComparison cmp(Boolean src, Boolean obj, Boolean ref) {
			if(src == null || obj == null || ref == null) {
				return INCOMPARABLE;
			}
			return PartiallyOrderedComparison.cmp(src ? 1L : 0L, obj ? 1L : 0L, ref ? 1L : 0L);
		}
	}
	

	
	private class StringHook implements Hook<String> {
		
		@Override
		public PartiallyOrderedComparison cmp(String src, String obj, String ref) {
			return PartiallyOrderedComparison.cmp(src, obj, ref);
		}
		
	}
	
	private class SetHook<T> implements Hook<Set<T>> {

		@Override
		public PartiallyOrderedComparison cmp(Set<T> src, Set<T> obj, Set<T> ref) {
			return PartiallyOrderedComparison.cmp(src, obj, ref);
		}
		
	}
	
	private class ListHook<T> implements Hook<List<T>> {
		
		@Override
		public PartiallyOrderedComparison cmp(List<T> src, List<T> obj, List<T> ref) {
			return PartiallyOrderedComparison.cmp(src, obj, ref);
		}
		
	}
	
	private class MapHook<T, U> implements Hook<Map<T, U>> {
		private Hook<U> hook;
		
		public MapHook(Hook<U> hook) {
			this.hook = hook;
		}
		
		@Override
		public PartiallyOrderedComparison cmp(Map<T, U> src, Map<T, U> obj, Map<T, U> ref) {
			return PartiallyOrderedComparison.cmp(src, obj, ref, hook);
		}
	}
}
