package com.common;

import java.util.Base64;

public class Crypt {
	// 使用 `Base64` 編碼器對字符串進行編碼
	public String encodeBase64(String str) {
		Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(str.getBytes());
        System.out.println("Encoded Data: " + encoded);
		return encoded;
	}
	
	// 解碼編碼數據
	public String decodeBase64(String str) {
		Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(str));
//        System.out.println("Decoded Data: " + decoded);
		return decoded;
	}
}
