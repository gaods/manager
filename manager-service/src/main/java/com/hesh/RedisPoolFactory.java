//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh;

import java.util.List;
import org.springside.modules.nosql.redis.pool.JedisPool;
import org.springside.modules.nosql.redis.pool.JedisPoolBuilder;

public class RedisPoolFactory {
    public RedisPoolFactory() {
    }

    public static JedisPool createJedisPool(String url) {
        return (new JedisPoolBuilder()).setUrl(url).buildPool();
    }

    public static List<JedisPool> createShardedJedisPools(String shardedUrl) {
        return (new JedisPoolBuilder()).setUrl(shardedUrl).buildShardedPools();
    }
}
