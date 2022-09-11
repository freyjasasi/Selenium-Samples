package amazon;

public class MainAmazon {

	public static void main(String[] args) throws InterruptedException {

		SamsungPhones samsungPhones = SamsungPhones.getInstance();
		samsungPhones.extractPhones();

	}

}
