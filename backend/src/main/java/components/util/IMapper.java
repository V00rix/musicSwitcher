package components.util;

import java.util.*;

/**
 * Map utils
 */
public class IMapper {
    /**
     * Sort a Map by value
     *
     * @param map Map to sort
     * @param comparator Comparing function
     * @param <K>
     * @param <V>
     * @return Sorted map
     * @see <a href="https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values">Stack Overflow</a>
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
