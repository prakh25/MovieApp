package com.example.prakhar.movieapp.utils.palette;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;

/**
 * Created by Prakhar on 4/1/2017.
 */

public class PaletteBitmapResource implements Resource<PaletteBitmap> {
    private PaletteBitmap paletteBitmap;
    private BitmapPool bitmapPool;

    public PaletteBitmapResource(PaletteBitmap paletteBitmap, BitmapPool bitmapPool) {
        this.paletteBitmap = paletteBitmap;
        this.bitmapPool = bitmapPool;
    }

    @Override
    public PaletteBitmap get() {
        return paletteBitmap;
    }

    @Override
    public int getSize() {
        return Util.getBitmapByteSize(paletteBitmap.bitmap);
    }

    @Override
    public void recycle() {
        if (!bitmapPool.put(paletteBitmap.bitmap)) {
            paletteBitmap.bitmap.recycle();
        }
    }
}