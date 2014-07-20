package laplab.lib.tablecreator;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentValues extends HashMap {

    ContentValues() {
        super();
    }

    @Override
    public Object put(Object key, Object value) {
        return super.put(key, value);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Object put(String key, int value) {
        return super.put(key, value);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
