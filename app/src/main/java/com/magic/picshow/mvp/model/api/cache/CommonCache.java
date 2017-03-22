package com.magic.picshow.mvp.model.api.cache;

import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.model.entity.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<User>>> getUsers(Observable<List<User>> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.MINUTES)
    Observable<BaseJson<LoginInfo>> login(Observable<LoginInfo> loginInfoObservable, DynamicKey account);

}
