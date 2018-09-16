/**
 * Interface class.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-09-13
 */
public interface SortInterface {

    /**
     * Public method per UML.
     *
     * @param list list to be sorted.
     * @throws UnsortedException Check for unsorted array.
     */
    void recursiveSort(int... list) throws UnsortedException;

    /**
     * Public method per UML.
     *
     * @param list list to be sorted.
     * @throws UnsortedException Check for unsorted array.
     */
    void iterativeSort(int... list) throws UnsortedException;

    /**
     * Public method per UML.
     *
     * @return count
     */
    int getCount();

    /**
     * Public method per UML.
     *
     * @return time
     */
    long getTime();

}