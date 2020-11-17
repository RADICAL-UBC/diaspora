package diaspora.common;

import java.io.Serializable;

import diaspora.dms.DiasporaPolicy.DiasporaClientPolicy;

public interface AppObjectStub extends Serializable, Cloneable {
	public void $__initialize(DiasporaClientPolicy client);
	public void $__initialize(boolean directInvocation);
	public Object $__clone() throws CloneNotSupportedException;
}
