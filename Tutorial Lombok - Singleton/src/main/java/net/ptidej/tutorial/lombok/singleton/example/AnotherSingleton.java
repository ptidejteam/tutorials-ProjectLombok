package net.ptidej.tutorial.lombok.singleton.example;

public class AnotherSingleton {
	private AnotherSingleton() {
	}

	private static class AnotherSingletonHolder {
		private static AnotherSingleton UNIQUE_INSTANCE = new AnotherSingleton();
	}

	public static AnotherSingleton getInstance() {
		return AnotherSingletonHolder.UNIQUE_INSTANCE;
	}

	// Some other methods
}