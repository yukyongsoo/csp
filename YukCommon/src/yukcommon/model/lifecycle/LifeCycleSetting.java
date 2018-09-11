package yukcommon.model.lifecycle;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class LifeCycleSetting extends AbsModel{
	@SerializedName("type")
	private RuleType type;
	private String id = "";
	private String name = "";
	private String workId = "";
	private String startingCron = "";
	private String endingCron = "0 0 12 1 1 ? 9999";
	private String startingRange = "0 0 12 1 1 ? 9999";
	private String endRange = "";
	private int thread  = 1;
	private boolean loopBack = false;

	public int getThread() {
		return thread;
	}
	public void setThread(int thread) {
		this.thread = thread;
		if(this.thread < 1)
			this.thread = 1;
	}
	public RuleType getType() {
		if(type == null)
			throw new WrongOperationException("cycle setting type is empty");
		return type;
	}
	public void setType(RuleType type) {
		if(type == null)
			throw new NotNullAllowedException("cycle setting type can't be null");
		this.type = type;
	}
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("cycle setting id is empty");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("cycle setting id can't be null");
		this.id = id;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("cycle setting name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("cycle setting name can't be null");
		this.name = name;
	}
	public String getWorkId() {
		if(workId.isEmpty())
			throw new WrongOperationException("cycle setting workId is empty");
		return workId;
	}
	public void setWorkId(String workId) {
		if(workId == null || workId.isEmpty())
			throw new NotNullAllowedException("cycle setting workId can't be null");
		this.workId = workId;
	}
	public String getStartingCron() {
		if(startingCron.isEmpty())
			throw new WrongOperationException("cycle setting startingCron is empty");
		return startingCron;
	}
	public void setStartingCron(String startingCron) {
		if(startingCron == null || startingCron.isEmpty())
			throw new NotNullAllowedException("cycle setting startingCron can't be null");
		this.startingCron = startingCron;
	}
	public String getEndingCron() {
		if(endingCron.isEmpty())
			throw new WrongOperationException("cycle setting endingCron is empty");
		return endingCron;
	}
	public void setEndingCron(String endingCron) {
		if(endingCron == null || endingCron.isEmpty())
			throw new NotNullAllowedException("cycle setting endingCron can't be null");
		this.endingCron = endingCron;
	}
	public String getStartingRange() {
		return startingRange;
	}
	public void setStartingRange(String startingRange) {
		this.startingRange = startingRange;
	}
	public String getEndRange() {
		return endRange;
	}
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	public boolean isLoopBack() {
		return loopBack;
	}
	public void setLoopBack(boolean loopBack) {
		this.loopBack = loopBack;
	}
}
