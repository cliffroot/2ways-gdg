package com.paradiseoctopus.happysquirrel.helpers;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/*
 * Class for android volley NetworkImage, totally copypasted from the Internet.
 */
public class BitmapLruCache extends android.support.v4.util.LruCache<String, Bitmap> implements ImageCache {

	public BitmapLruCache() {
		this((int) (Runtime.getRuntime().maxMemory() / 1024) / 4);
	}

	public BitmapLruCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
		return size;
	}

	public boolean contains(String key) {
		return get(key) != null;
	}

	@Override
	public Bitmap getBitmap(String key) {
		Bitmap bitmap = get(key);
		return bitmap;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
}