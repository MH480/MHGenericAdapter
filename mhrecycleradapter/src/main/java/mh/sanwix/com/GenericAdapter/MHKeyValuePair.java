package mh.sanwix.com.GenericAdapter;

/**
 * Created by m.hoseini on 2/3/2018.
 */

public class MHKeyValuePair<TKey,TValue>
{
    private TKey key;
    private TValue value;

    public TKey getKey()
    {
        return key;
    }

    public TValue getValue()
    {
        return value;
    }

    public MHKeyValuePair(TKey key, TValue value)
    {
        this.key = key;
        this.value = value;
    }
}
