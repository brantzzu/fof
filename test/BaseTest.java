import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import play.libs.Codec;

public class BaseTest {

	@Test
	public void testMD5() {
		String md5 = Codec.hexMD5("sunqw");
		System.out.println(md5);
		md5= StringUtils.upperCase(md5); 
		assertEquals("07C8EF48BDC917FE3B0EC18250DE9018", md5);
	}

	@Test
	public void testZgMd5() {
		String zg = "CF0832DEDF7457BBCBFA00BBD87B300A";
		String zgmd5 = Codec.hexMD5("中国");
		System.out.println(zgmd5);
		assertEquals(zg, zgmd5);
	}
}
