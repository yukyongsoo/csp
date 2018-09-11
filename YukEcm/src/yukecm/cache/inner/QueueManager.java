package yukecm.cache.inner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import yukecm.cache.CacheModel;

public class QueueManager {
	
	private static class LazyHolder {
	    private static final QueueManager manager = new QueueManager();
	}

	public static QueueManager getInstance(){
		return LazyHolder.manager;
	}
	
	private BlockingQueue<CacheModel> sendQueue;
	
	private QueueManager() {
		sendQueue = new ArrayBlockingQueue<CacheModel>(500, true);
	}
	
	public void putTarget(CacheModel model) throws InterruptedException{
		sendQueue.put(model);
	}
	
	public CacheModel getTarget() throws InterruptedException{
		return sendQueue.take();
	}
}
