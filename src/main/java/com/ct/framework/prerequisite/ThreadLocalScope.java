package com.ct.framework.prerequisite;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalScope implements Scope {
    private static final ThreadLocal<Map<Key<?>, Object>> threadLocalScope = ThreadLocal.withInitial(HashMap::new);

    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
        return () -> {
            Map<Key<?>, Object> scopedObjects = threadLocalScope.get();
            if (!scopedObjects.containsKey(key)) {
                T object = unscoped.get();
                scopedObjects.put(key, object);
            }
            return (T) scopedObjects.get(key);
        };
    }

    public void clear() {
        threadLocalScope.remove();
    }
}


