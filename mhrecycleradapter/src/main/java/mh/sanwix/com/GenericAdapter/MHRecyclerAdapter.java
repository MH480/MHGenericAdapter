package mh.sanwix.com.GenericAdapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by m.hoseini on 8/26/2017.
 */

public class MHRecyclerAdapter<Model, VHModel> extends RecyclerView.Adapter<MHViewHolder<VHModel>> implements MHIBaseAdapter<Model>
{
    //region Const Values
    private static final int EMPTY_VIEW_TYPE = 455;
    private static final int MAIN_VIEW_TYPE = 479;
    private static final int LAZY_VIEW_TYPE = 915;
    private static final int HEADER_VIEW_TYPE = 466;
    //endregion

    //region Models
    private Class<VHModel> MyVHolder;
    private List<Model> items;
    private Class<?> MyEmptyVH;

    //endregion

    //region Varriables
    private SparseBooleanArray _selectedItems;
    private List<MyKeyValue> listeners;
    private List<String> propertiesNames;
    private List<String> methodsNames;
    private boolean isSelectable;
    private boolean isMultiSelection;
    private boolean hasEmptyView;
    private View LazyView;
    private boolean hasLazyView;
    private boolean isLazyLoading;
    private int lazyLoadingPosition;
    private MHItemHeaderDecoration StickyHeader;

    //endregion

    //interface
    private MHIonBindView bindView;
    private MHIstickyHeader StickyHeaderListener;

    @LayoutRes
    private int resId;
    @LayoutRes
    private int resId_empty;
    @LayoutRes
    private int resId_lazy;


    public MHRecyclerAdapter(Class<VHModel> myVHModelClass)
    {

        initilize(getViewID(myVHModelClass), myVHModelClass, false, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, isMultiSelection, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, isMultiSelection, list);
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass)
    {
        initilize(_resId, myVHModelClass, false, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable)
    {
        initilize(_resId, myVHModelClass, isSelectable, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection)
    {
        initilize(_resId, myVHModelClass, isSelectable, isMultiSelection, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        initilize(_resId, myVHModelClass, isSelectable, isMultiSelection, list);
    }

    private void initilize(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        resId = _resId;
        if (resId == 0)
            throw new RuntimeException("Could not found layout in class " + myVHModelClass.getSimpleName() + "\n are you missing an annotaion ?");
        MyVHolder = myVHModelClass;
        this.isSelectable = isSelectable;
        this.isMultiSelection = isMultiSelection;
        items = list != null ? list : new ArrayList<Model>();
        _selectedItems = new SparseBooleanArray();
        listeners = new ArrayList<>();
        hasEmptyView = false;
        isLazyLoading = false;
        resId_lazy = -1;
        propertiesNames = new ArrayList<>();
        methodsNames = new ArrayList<>();


    }

    /**
     * find Layout id in given Model
     *
     * @param model
     * @return
     */
    private int getViewID(Class<VHModel> model)
    {
        MHBindView col = model.getAnnotation(MHBindView.class);
        return (col != null) ? col.value() : 0;
    }

    /**
     * Exit point of onbindView in Adapter
     *
     * @param bindView Interface MH IonBindView
     */
    public void setBindViewCallBack(MHIonBindView bindView)
    {
        this.bindView = bindView;
    }

    /**
     * add View With Given ID as Clickable View on top of stack
     *
     * @param propertyID ID of clickable view
     */
    public void addListener(@IdRes int propertyID)
    {
        this.listeners.add(new MyKeyValue(propertyID, null));
    }

    /**
     * remove clickable view from top of stack
     */
    public void remLastListener()
    {
        if (this.listeners.size() > 0)
        {
            this.listeners.remove(listeners.size() - 1);
        }
    }

    //region External interfaces Methods


    @Override
    public <EmptyVH extends Object> void setEmptyView(Class<EmptyVH> emptyVHModelClass)
    {
        MHBindView col = emptyVHModelClass.getAnnotation(MHBindView.class);
        int ID = (col != null) ? col.value() : 0;
        setEmptyView(ID, emptyVHModelClass);
    }


    @Override
    public <EmptyVH extends Object> void setEmptyView(@LayoutRes int id, Class<EmptyVH> emptyVHModelClass)
    {
        resId_empty = id;
        if (resId_empty == 0)
            throw new RuntimeException("Could not found layout in class " + emptyVHModelClass.getSimpleName() + "\n are you missing an annotaion ?");
        MyEmptyVH = emptyVHModelClass;
        hasEmptyView = true;
    }

    @Override
    public void setLazyView(View _lazyView)
    {
        if (_lazyView != null)
        {
            this.LazyView = _lazyView;
            hasLazyView = true;
        }
    }

    @Override
    public void setLazyView(@LayoutRes int _lazyViewId)
    {
        resId_lazy = _lazyViewId;
        hasLazyView = true;
    }


    @Override
    public boolean hasEmptyView()
    {
        return hasEmptyView;
    }


    @Override
    public List<Model> getItems()
    {
        return items;
    }


    @Override
    public void setItems(List<Model> _items)
    {
        if (_items != null && _items.size() > 0)
        {
            int size = items.size();
            items.clear();
            if (size > 0)
                notifyItemRangeRemoved(0, size);
            items.addAll(_items);
            propertiesNames = getPropertiesNames(items.get(0));
            methodsNames = getMethodsNames(items.get(0));
            notifyItemRangeInserted(0, _items.size());
        }
    }


    public void setItems_NoAdnim(List<Model> _items)
    {
        if (_items != null && _items.size() > 0)
        {
            items = new ArrayList<>();
            items.addAll(_items);
            propertiesNames = getPropertiesNames(items.get(0));
            methodsNames = getMethodsNames(items.get(0));
            notifyDataSetChanged();
        }
    }

    @Override
    public void clearItems()
    {
        int size = items.size();
        items.clear();
        if (size > 0)
            notifyItemRangeRemoved(0, size);

    }

    @Override
    public int addItem(Model _item)
    {
        if (items != null)
        {
            items.add(_item);
            int pos = items.size() - 1;
            notifyItemInserted(pos);
            return pos;
        }
        else
            return -1;
    }

    private List<String> getPropertiesNames(Model m)
    {
        List<String> propssss = new ArrayList<>();
        Class<Model> clazz = (Class<Model>) m.getClass();

        for (Field f : clazz.getDeclaredFields())
            if (Modifier.isPublic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()))
            {
                MHBindView col = f.getAnnotation(MHBindView.class);
                if (col != null)
                {
                    propssss.add(f.getName());
                    if (col.isClickable())
                        addListener(col.value());
                }
            }
        return propssss;
    }

    private List<String> getMethodsNames(Model model)
    {
        List<String> propssss = new ArrayList<>();
        Class<Model> clazz = (Class<Model>) model.getClass();

        for (Method m : clazz.getDeclaredMethods())
            if (Modifier.isPublic(m.getModifiers()) && !Modifier.isFinal(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
            {
                MHBindViewAction col = m.getAnnotation(MHBindViewAction.class);
                if (col != null)
                {
                    propssss.add(m.getName());
                }
            }
        return propssss;
    }




    @Override
    public Model getItem(int index)
    {
        return items.get(index);
    }


    @Override
    public void upItem(int index, Model obj)
    {
        items.set(index, obj);
        notifyItemChanged(index);
    }


    @Override
    public void remItem(int index)
    {
        items.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public void remRangeItems(List<Integer> indices)
    {
        for (int i = 0; i < indices.size(); i++)
            items.remove(indices.get(i));
        notifyDataSetChanged();
    }


    @Override
    public void toggleItem(int index)
    {
        if (isSelectable || isMultiSelection)
            selectItem(index, true);
    }


    @Override
    public void selectItem(int index, boolean selected)
    {
        if (_selectedItems.get(index, !selected))
            _selectedItems.delete(index);
        else
        {
            if (!isMultiSelection == isSelectable)
                _selectedItems.clear();

            _selectedItems.put(index, selected);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getSelectedItemsCount()
    {
        return _selectedItems.size();
    }


    @Override
    public boolean isSelected(int index)
    {
        return (isSelectable || isMultiSelection) && _selectedItems.get(index, false);
    }


    @Override
    public void ClearSelection()
    {
        if (isSelectable || isMultiSelection)
        {
            _selectedItems.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void ClearSelectionAt(int index)
    {
        if (isSelectable || isMultiSelection)
        {
            _selectedItems.delete(index);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public List<Model> getSelectedItems()
    {
        List<Model> mlist = new ArrayList<>();
        for (int i = 0; i < _selectedItems.size(); i++)
            mlist.add(getSelectedItem(i));
        return mlist;
    }

    @NonNull
    @Override
    public List<Integer> getSelectedItemsIndices()
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < _selectedItems.size(); i++)
            list.add(_selectedItems.keyAt(i));
        return list;
    }

    @Override
    public void setAllItemsSelected()
    {
        if (isMultiSelection || isSelectable)
        {
            _selectedItems.clear();
            for (int i = 0; i < items.size(); i++)
                _selectedItems.put(i, true);
        }
        notifyDataSetChanged();
    }

    @Override
    public Model getSelectedItem(int index)
    {
        return getItem(_selectedItems.keyAt(index));
    }

    @Override
    public void appendItems(List<Model> appends)
    {
        if (propertiesNames == null || propertiesNames.size() == 0)
            throw new RuntimeException("you must use 'setItems' method for first time to initialize data");
        int size = items.size();
        items.addAll(appends);
        notifyItemRangeInserted(size, appends.size());
    }

    @Override
    public void beginLazyLoading()
    {
        if (hasLazyView)
        {
            isLazyLoading = true;
            lazyLoadingPosition = addItem(null);
        }
    }

    @Override
    public boolean isLazyLoading()
    {
        return isLazyLoading;
    }

    @Override
    public void endLazyLoading()
    {
        if (hasLazyView && isLazyLoading)
        {
            isLazyLoading = false;
            remItem(lazyLoadingPosition);
        }
    }

    @Override
    public RecyclerView.OnItemTouchListener buildTouchItemListener(RecyclerView _rv, MHOnItemClickListener listener)
    {
        List<Integer> ids = new ArrayList<>();
        for (MyKeyValue kv : listeners)
        {
            ids.add(kv.Key);
        }
        MHTouchItemClick<VHModel> click = new MHTouchItemClick<VHModel>(_rv, listener, MyVHolder);
        click.setIds(ids);
        return click;
    }

    public RecyclerView.OnScrollListener buildScrollListener(MHIonScrolling scrolling)
    {
        return new MHonScroll(scrolling);
    }

    public RecyclerView.ItemDecoration buildStickyHeader(RecyclerView _rv, MHIstickyHeader listener)
    {
        return StickyHeader = new MHItemHeaderDecoration(_rv, StickyHeaderListener = listener);
    }


    public MHViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MHViewHolder<?> mhvh = null;
        View v;
        switch (viewType)
        {
            case EMPTY_VIEW_TYPE:
                if (hasEmptyView)
                {
                    v = LayoutInflater.from(parent.getContext()).inflate(resId_empty, parent, false);
                    mhvh = new MHViewHolder<>(v, MyEmptyVH);
                }
                break;
            case LAZY_VIEW_TYPE:
                if (hasLazyView)
                {
                    if (LazyView == null && resId_lazy != -1)
                        v = LayoutInflater.from(parent.getContext()).inflate(resId_lazy, parent, false);
                    else
                        v = LazyView;

                    mhvh = new MHViewHolder<>(v, null);
                }
                break;
            case HEADER_VIEW_TYPE:
                if (StickyHeaderListener != null && StickyHeader != null)
                {
                    int pos = StickyHeader.getPosition();
                    v = LayoutInflater.from(parent.getContext()).inflate(StickyHeaderListener.getHeaderLayout(pos), parent, false);
                    mhvh = new MHViewHolder<VHModel>(v, null);
                }
                break;
            case MAIN_VIEW_TYPE:
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
                mhvh = new MHViewHolder<VHModel>(v, MyVHolder);

                break;
        }

        return mhvh;
    }

    @Override
    public void onBindViewHolder(MHViewHolder holder, int position)
    {
        if (holder == null)
            return;

        switch (getItemViewType(position))
        {
            case EMPTY_VIEW_TYPE:
            case LAZY_VIEW_TYPE:
                return;
            case HEADER_VIEW_TYPE:
                if (StickyHeaderListener != null && StickyHeader != null)
                {
                    StickyHeaderListener.bindHeaderData(holder.itemView, position);
                }
                return;
            default:
        }


        Model model = items.get(position);
        List<View> childs = new ArrayList<>();
        Class<Model> clazz = (Class<Model>) model.getClass();
        for (String propName : propertiesNames) // propertiesNames is string because i need property value with column i can`t get property value
        {
            Field f = null;
            try
            {
                f = clazz.getDeclaredField(propName);
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            MHBindView col = f != null ? f.getAnnotation(MHBindView.class) : null;
            if (col != null)
            {
                f.setAccessible(true);
                Object value = null;
                try
                {
                    value = f.get(model);
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                View j = holder.setValue(col, col.isPosition() ? position : value);
                childs.add(j);
            }
        }

        for (String methName : methodsNames)
        {
            Method m = null;
            try
            {
                m = clazz.getDeclaredMethod(methName);
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            MHBindViewAction col = m != null ? m.getAnnotation(MHBindViewAction.class) : null;
            if (col != null)
            {
                m.setAccessible(true);
                Object value = null;
                try
                {
                    if (m.getGenericParameterTypes().length == 0)
                    {
                        value = m.invoke(model,new Object[]{});
                    }
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
                View j = holder.setValue(col, value);
                childs.add(j);
            }
        }


        if (bindView != null && holder.itemView instanceof ViewGroup)
        {
            View[] array = new View[0];

            if (childs.size() > 0)
            {
                array = new View[childs.size()];
                childs.toArray(array);
            }
            bindView.BindViewHolder((ViewGroup) holder.itemView, holder.getMyModel(), position, array);

        }

    }

    @Override
    public int getItemViewType(int position)
    {
        int result = hasEmptyView && items.size() == 0 ? EMPTY_VIEW_TYPE : MAIN_VIEW_TYPE;
        if (isLazyLoading && hasLazyView && position == lazyLoadingPosition)
        {
            result = LAZY_VIEW_TYPE;
        }
        if (StickyHeaderListener != null && StickyHeaderListener.isHeader(position) && StickyHeader != null)
        {
            result = HEADER_VIEW_TYPE;
            StickyHeader.setPosition(position);
        }
        return result;
    }

    @Override
    public int getItemCount()
    {
        return hasEmptyView && items.size() == 0 ? 1 : items.size();
    }
}
