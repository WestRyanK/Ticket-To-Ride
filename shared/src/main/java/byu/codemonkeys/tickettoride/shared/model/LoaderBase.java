package byu.codemonkeys.tickettoride.shared.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ryan on 10/31/2017.
 */

public class LoaderBase {
	protected static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	protected <T> List<T> getDataFromResource(String resourceName, Type type) {
		List<T> data = null;
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(classLoader.getResourceAsStream(resourceName));
			Gson gson = new Gson();
			data = gson.fromJson(isr, type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return data;
	}
}
