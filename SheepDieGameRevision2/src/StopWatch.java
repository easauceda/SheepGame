import java.util.Date;

public class StopWatch {
	private Date startTime;

	public void startTiming() {
		startTime = new Date();
	}

	public long stopTiming() {
		Date stopTime = new Date();
		long timediff = (stopTime.getTime() - startTime.getTime());

		return timediff;
	}

	public static void main(String args[]) {
		StopWatch stopwatch = new StopWatch();
		final int Min = 2;
		final int Max = 4;

		stopwatch.startTiming();

		long total = 0;
		for (int i = 0; i < 10; i++) {
			try {
				long rnd = Min + (int) (Math.random() * ((Max - Min) + 1));
				long pause = rnd * 100;
				total += pause;
				System.out.println("pausing: " + pause);
				Thread.sleep(pause);
			} catch (Exception ex) {

			}
		}

		long interval = stopwatch.stopTiming();

		System.out.println("time: " + interval);
		System.out.println("total: " + total);
		System.out.println("overhead: " + (interval - total));
	}
}
