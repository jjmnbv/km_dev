import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/4 16:26
 */
public class Test {

    public void test() {
        String s = DigestUtils.md5Hex("123456").toLowerCase();
        System.out.println(s);
    }

    @org.junit.Test
    public void test2() {

        String deleteImgIds = ",123,234,345,";

        List<Long> imgIdsList = Lists.newArrayList(StringUtils.split(deleteImgIds, ","))
                .stream().map(imgId -> Long.valueOf(imgId))
                .collect(Collectors.toList());
        System.out.println(imgIdsList.size());
        imgIdsList.stream().forEach(aLong -> System.out.println(aLong.getClass()));


        Integer a = null;
        System.out.println(a>0);
    }


}
