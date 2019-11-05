package com.oasisfeng.nevo.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.LruCache;


/**
 * Author: TimothyZhang023
 * Icon Cache
 */
public class IconCache {

    private volatile static IconCache cache = null;

    private LruCache<String, Icon> mIconMemoryCaches;

    private IconCache() {
        mIconMemoryCaches = new LruCache<>(100);
    }

    public static IconCache getInstance() {
        if (cache == null) {
            synchronized (IconCache.class) {
                if (cache == null) {
                    cache = new IconCache();
                }
            }
        }
        return cache;
    }


    public Icon getIconCache(final Context ctx, final String pkg) {
        Icon cached = mIconMemoryCaches.get(pkg);
        if (cached == null) {
            cached = getIcon(ctx, pkg);
            if (cached != null) {
                mIconMemoryCaches.put(pkg, cached);
            }
        }
        return cached;
    }

    private Icon getIcon(Context ctx, String pkg) {
        PackageManager pm = ctx.getPackageManager();
        Bitmap rawIconBitmap = null;
        try {
            Drawable icon = pm.getApplicationInfo(pkg, PackageManager.GET_UNINSTALLED_PACKAGES).loadIcon(pm);
            rawIconBitmap = ImgUtils.drawableToBitmap(icon);
        } catch (Throwable ignored) {
        }

        try {
            Drawable icon = ctx.getPackageManager().getApplicationIcon(pkg);
            rawIconBitmap = ImgUtils.drawableToBitmap(icon);
        } catch (Throwable ignored) {
        }

        if (rawIconBitmap == null) {
            return null;
        }

        //scaleImage to 64dp
        int dip2px = dip2px(ctx, 64);
        Bitmap whiteIconBitmap = ImgUtils.scaleImage(ImgUtils.convertToTransparentAndWhite(rawIconBitmap), dip2px, dip2px);
        return Icon.createWithBitmap(whiteIconBitmap);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (64 * scale + 0.5f);
    }

}
