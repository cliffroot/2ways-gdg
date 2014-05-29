package com.paradiseoctopus.happysquirrel.helpers;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/*
 * The only purpose of the class it to create config file in res/raw firectory from which DAO is afterwards created
 * some kind of magic :| 
 * it needs to be launched as Java Application as well (Run Configurations)
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
	
	private static final Class<?>[] classes = new Class[] {
	// Wish.class,
	};
	
	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}