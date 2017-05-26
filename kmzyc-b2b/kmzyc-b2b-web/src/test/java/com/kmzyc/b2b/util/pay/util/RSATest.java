package com.kmzyc.b2b.util.pay.util;

import org.junit.Test;

import com.kmzyc.b2b.util.pay.util.RSA;

/**
 * @author min 2016-12-02 17:52
 */
public class RSATest {
    static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM85KVf/5JefpXry" +
            "ERPYGV1oFMdv5c82XYWAL9D7/ptNJXJFoupUUD5S8LQHmDxOGEDgcPwiNS+o445x" +
            "8HVFskDybulvmEcUEyQq7ooCuQAs7eJZXeePXeoUVGoaExyO6wi4Zqhla/SCsY2z" +
            "buh/3ckhaggmlK4Ci3+LHDxPdtwXAgMBAAECgYAbbtHOWI4GlJfvosQoAF+7QeAe" +
            "Vu3xdJ0E42dxHyshnCDs89MRen/1AD3lV2wL77q+O/g5KL4pxQ36+ZJ/s+5Y9bP2" +
            "BSi7ANRCv6tXRrTg48fGCbR+I/kszTTyKzZkaejBv/bupFNHgtA3G6sE4hwUJrbO" +
            "QfcZVoxddXPTu+3swQJBAPDeM2LtjiSwB0IeeYzqHO9uCKrkI60ugLLn5l+DeZg2" +
            "44to7IZ1070YNHU+WmA7VWLNs08hlQiQI/Xq2F2ziqkCQQDcPd1Rv9gqik9tS40n" +
            "bw/Vu/2EnpgU9PctrTYuNfKBO4gtzBXyf0eosMd7vKL7IsxrFBmRxhdGh+1pA1Mx" +
            "GCi/AkBoBo8K2q3ZCm0YETFNuo5kmZEEvSLrWHocyiqkdZO+OXZsbxQ3vguETz/Y" +
            "ouVFSlrGITCfrFFBMX9WZeasES6BAkACxORbesYFAugsv55uuH7CeTKe0D+pQ8s3" +
            "z822lwcNiOoVaVN9UQxdmptnet+xuKZJUl9f7dWtFVhu0ZPiMiORAkEA8NU0pu6F" +
            "zXWSAVJG4mdCTRKU2pKr38RvOdEoYAXGYaMmHED/TEjFYFL8yoYNHKDiPULq1fUd" + "Iu5RAZa+epleSA==";

    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPOSlX/+SXn6V68hET2BldaBTH" +
            "b+XPNl2FgC/Q+/6bTSVyRaLqVFA+UvC0B5g8ThhA4HD8IjUvqOOOcfB1RbJA8m7p" +
            "b5hHFBMkKu6KArkALO3iWV3nj13qFFRqGhMcjusIuGaoZWv0grGNs27of93JIWoI" +
            "JpSuAot/ixw8T3bcFwIDAQAB";

    @Test
    public void sign() throws Exception {

        System.out.println(RSA.sign("abc", privateKey, "UTF-8"));
    }

    @Test
    public void name() throws Exception {
        String abc = "abc";
        String input_charset = "UTF-8";
        System.out.println(RSA.verify(abc, RSA.sign(abc, privateKey, input_charset), publicKey,
                input_charset));
    }
}
