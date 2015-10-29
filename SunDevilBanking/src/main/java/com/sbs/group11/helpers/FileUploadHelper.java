package com.sbs.group11.helpers;

import java.io.InputStream;
import java.util.Scanner;

public class FileUploadHelper {

	public static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
