package com.hcs.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author fernando.ramirez
 *
 */
public class SecureUtils {

	public static String getSecureStr(String string) {
		String secureStr = new String(Hex.encodeHex(DigestUtils.sha256(string)));
		return secureStr;
	}
}
