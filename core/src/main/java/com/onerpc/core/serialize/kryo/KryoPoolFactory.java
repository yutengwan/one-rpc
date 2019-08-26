package com.onerpc.core.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @version $Id: KryoPoolFactory.java
 */
public class KryoPoolFactory {

    private Class<?> genericClass;

    private GenericObjectPool<Kryo> kryoPool;

    public KryoPoolFactory(Class<?> genericClass) {
        this.genericClass = genericClass;
        kryoPool = new GenericObjectPool<>(new BasePooledObjectFactory<Kryo>() {
            @Override
            public Kryo create() throws Exception {
                return createKryo();
            }

            @Override
            public PooledObject<Kryo> wrap(Kryo kryo) {
                return new DefaultPooledObject<>(kryo);
            }
        });
    }

    public Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        kryo.setWarnUnregisteredClasses(true);

        // 所有类型都需要注册，包括子对象类型
        kryo.register(genericClass);
        kryo.register(Class.class);
        kryo.register(Class[].class);
        kryo.register(Object[].class);
        kryo.register(String.class);
        return kryo;
    }

    public Kryo getKryo() throws Exception {
        return kryoPool.borrowObject();
    }

    public void returnKryo(Kryo kryo) {
        kryoPool.returnObject(kryo);
    }
}