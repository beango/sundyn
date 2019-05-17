package com.sundyn.util;

import com.sundyn.utils.NumberUtils;

import java.util.regex.Pattern;

public class InetAddressUtils {
    public static void main(String[] args) {
        String addr = "127.1.6.5";
        System.out.println(isIPv4Address2(addr));
    }

    private InetAddressUtils() {
    }

    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    public static boolean isIPv4Address2(final String input) {
        if (com.xuan.xutils.utils.StringUtils.isBlank(input))
            return false;

        String[] arr = input.split("\\.");

        if (arr.length > 4)
            return false;
        if (arr.length == 1 && !input.equals("*"))
            return false;
        for (int i = 0; i < arr.length; i++) {
            if (!NumberUtils.isNumber(arr[i])) {
                if (i != arr.length - 1)
                    return false;
                if (!arr[i].equals("*"))
                    return false;
            } else {
                if (Integer.valueOf(arr[i]) < 0 || Integer.valueOf(arr[i]) > 255)
                    return false;
            }
        }
        return true;
    }

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }
}
