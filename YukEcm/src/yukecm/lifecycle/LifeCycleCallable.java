package yukecm.lifecycle;

import java.util.concurrent.Callable;

import yukcommon.exception.NotSupportException;
import yukcommon.model.Doc;

public class LifeCycleCallable implements Callable<Boolean>{
	protected Doc doc;

	public LifeCycleCallable(Doc doc){
		this.doc = doc;
	}

	@Override
	public Boolean call() throws Exception {
		throw new NotSupportException("You Can't Use This Class. Overriding This");
	}
}
