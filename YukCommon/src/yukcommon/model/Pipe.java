package yukcommon.model;

import yukcommon.adaptor.PipeAdaptor;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Pipe extends AbsModel{
	private String id = "";
	private String name = "";
	private String className = "";
	private boolean passOnError = false;
	private transient PipeAdaptor adt;

	public PipeAdaptor getAdt() {
		if(adt == null)
			throw new NotNullAllowedException("PipeAdaptor is null." + id);
		return adt;
	}

	public void setAdt(PipeAdaptor adt) {
		if(adt == null)
			throw new NotNullAllowedException("PipeAdaptor is null." + id);
		this.adt = adt;
	}

	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("pipe id is empty");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("pipe id can't be empty");
		this.id = id;
	}

	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("pipe name is empty");
		return name;
	}

	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("pipe name can't be empty");
		this.name = name;
	}

	public String getClassName() {
		if(className.isEmpty())
			throw new WrongOperationException("pipe className is empty");
		return className;
	}

	public void setClassName(String className) {
		if(className == null || className.isEmpty())
			throw new NotNullAllowedException("pipe className can't be empty");
		this.className = className;
	}

	public boolean isPassOnError() {
		return passOnError;
	}

	public void setPassOnError(boolean passOnError) {
		this.passOnError = passOnError;
	}
	
	@Override
	public String toString() {		
		return "Current Pipe Data: id = " + id + ".name=" + name + ".className=" + className + ".passOnError=" + passOnError;
	}
}
