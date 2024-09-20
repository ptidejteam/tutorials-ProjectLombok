// https://www.baeldung.com/lombok-custom-annotation
package net.ptidej.tutorial.lombok.singleton.test;

import org.junit.Assert;
import org.junit.Test;

import net.ptidej.tutorial.lombok.singleton.example.MySingleton;

public class TestSingleton {
	@Test
	public void test() {
		final MySingleton msc = MySingleton.getInstance();
		System.out.println(msc);
		Assert.assertNotNull(msc);
	}
}
