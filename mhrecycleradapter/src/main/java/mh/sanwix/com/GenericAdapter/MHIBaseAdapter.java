package mh.sanwix.com.GenericAdapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by m.hoseini on 8/26/2017.
 */

interface MHIBaseAdapter<T>
{
    /**
     * shows the view in recycler view when no items are set
     * @param emptyVHModelClass Empty layout Class Model
     * @param <EmptyVH> Type of class
     */
    <EmptyVH extends Object> void setEmptyView(Class<EmptyVH> emptyVHModelClass);
    /**
     * shows the view in recycler view when no items are set
     * @param id Empty layout id
     * @param emptyVHModelClass Empty layout Class Model
     * @param <EmptyVH> Type of class
     */
    <EmptyVH extends Object> void setEmptyView(@LayoutRes int id,Class<EmptyVH> emptyVHModelClass);

    void setLazyView(View _lazyView);
    void setLazyView(@LayoutRes int _lazyViewId);

    /**
     * determins adapter has an empty view
     * @return returns true if empty view set , otherwise false
     */
    boolean hasEmptyView();

    /**
     * get items model in adapters
     * @return retuens list of given generic type model
     */
    List<T> getItems();

    /**
     * initializes adapter model list items
     * it finds every property that marked with MHBindView annotation
     * it adds found property with clickable MHBindView annotation on top of stack
     * @param _items list of given generic type model
     */
    void setItems(List<T> _items);
    /**
     * it adds found property with clickable MHBindView annotation on top of stack
     * @param _item an item of given generic type model
     */
    int addItem(T _item);

    /**
     * returns item as model at specific index
     * @param index index of item to return
     * @return generic model calss
     */
    T getItem(int index);

    /**
     * updates item as model at specific index
     * @param index index of item to update
     * @param obj generic model calss
     */
    void upItem(int index, T obj);

    /**
     * removes item as model at specific index
     * @param index index of item to remove
     */
    void remItem(int index);

    /**
     * removes items as model in given range
     * @param indices list of items indices
     */
    void remRangeItems(List<Integer> indices);

    /**
     * marks view as selected at specific index if was not selected before , otherwise deselects the view
     * only if adapters has isSelectable = true or  isMultiSelection = true
     * @param index view row index
     */
    void toggleItem(int index);

    /**
     * marks view as selected or deselected at specific index
     *
     * @param index view row index
     * @param selected determinse view as selected if true, otherwise deselects
     */
    void selectItem(int index, boolean selected);

    /**
     * returns selected items count
     * @return size as int
     */
    int getSelectedItemsCount();

    /**
     * determise if view is selected or not at specific index only if adapter has isSelectable = true or  isMultiSelection = true
     * @param index view ro index
     * @return returns true if view is selected , otherwise false
     */
    boolean isSelected(int index);
    /**
     * deselects all selected rows only if adapter has isSelectable = true or  isMultiSelection = true
     */
    void ClearSelection();

    /**
     * deselects selected row at specific index only if adapter has isSelectable = true or  isMultiSelection = true
     * @param index view row index
     */
    void ClearSelectionAt(int index);

    /**
     * returns selected items as model class
     * @return list of class model
     */
    List<T> getSelectedItems();

    /**
     * returns selected item at specific index only if adapter has isSelectable = true or  isMultiSelection = true
     * @param index view row index
     * @return item as model class
     */
    T getSelectedItem(int index);

    /**
     * gets all selected items row index
     * @return list of interger
     */
    List<Integer> getSelectedItemsIndices();

    /**
     * set all items in recycler as selected
     */
    void setAllItemsSelected();


    /**
     * adds range if model to end of items
     * @param appends list of model
     */
    void appendItems(List<T> appends);

    /**
     * begins loading
     *
     */
    void beginLazyLoading();

    /**
     * ends loading
     */
    void endLazyLoading();

    /**
     * creates a listener item click for recyclerview
     * @param _rv recycler view to trigger item`s click
     * @param listener call back on row click or row childeren click
     * @return RecyclerView.OnItemTouchListener
     */
    RecyclerView.OnItemTouchListener buildTouchItemListener(RecyclerView _rv, MHOnItemClickListener listener);

}
