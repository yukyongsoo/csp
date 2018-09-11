package yukcommon.model.lifecycle;

import yukcommon.dic.EtcDic;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class LifeCycleInfo extends AbsModel{
	private String id = "";
	private String state = EtcDic.NOTSTARTED;
	private int nextRowNum = 0;
	private String start = "";
	private String end = "";
	private long totalTarget = 0;
	private long error = 0;
	private long excuted = 0;
	//not in db
	private String runTime = "0 Second";

	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("cycle info id is empty");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("cycle info id can't be empty");
		this.id = id;
	}

	public String getState() {
		if(state.isEmpty())
			throw new WrongOperationException("cycle info state is empty");
		return state;
	}

	public void setState(String state) {
		if(state == null || state.isEmpty())
			throw new NotNullAllowedException("cycle info state can't be empty");
		this.state = state;
	}

	public int getNextRowNum() {
		return nextRowNum;
	}

	public void setNextRowNum(int nextRowNum) {
		this.nextRowNum = nextRowNum;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public long getTotalTarget() {
		return totalTarget;
	}

	public void setTotalTarget(long totalTarget) {
		this.totalTarget = totalTarget;
	}

	public long getError() {
		return error;
	}

	public void setError(long error) {
		this.error = error;
	}
	
	public void addError(long value) {
		error = error + value;
	}

	public long getExcuted() {
		return excuted;
	}
	
	public void addExcuted(long value) {
		excuted = excuted + value;
	}

	public void setExcuted(long excuted) {
		this.excuted = excuted;
	}

	public String getRunTime() {
		if(end.isEmpty())
			throw new WrongOperationException("cycle info runtime is empty");
		return runTime;
	}

	public void setRunTime(String runTime) {
		if(runTime == null || runTime.isEmpty())
			throw new NotNullAllowedException("cycle info runTime can't be empty");
		this.runTime = runTime;
	}

	@Override
	public String toString() {
		return "state :" + state + ",start:"+ start + ",end:" + end + ",total:"
				+ totalTarget + ",excuted:" + excuted + ",error:" + error;
	}
}
