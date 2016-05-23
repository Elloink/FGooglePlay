package com.example.gooleplay.utils;

import java.io.Closeable;
import java.io.IOException;

public class QuiteClose {
	public static void quiteClose(Closeable stream) {
		if(stream == null) {
			return;
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
