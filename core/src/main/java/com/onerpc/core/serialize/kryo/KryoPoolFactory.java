package com.onerpc.core.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * @version $Id: KryoPoolFactory.java
 */
public class KryoPoolFactory {

    private Class<?> genericClass;

    private final ThreadLocal<Kryo> holder = ThreadLocal.withInitial(() -> createKryo());

    public KryoPoolFactory(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    public Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setWarnUnregisteredClasses(true);

        // 所有类型都需要注册，包括子对象类型
        kryo.register(genericClass);
        kryo.register(Class.class);
        kryo.register(Class[].class);
        kryo.register(Object[].class);
        return kryo;
    }

    public Kryo getKryo() {
        return holder.get();
    }
}