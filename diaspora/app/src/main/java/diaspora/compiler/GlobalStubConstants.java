package diaspora.compiler;

public class GlobalStubConstants {
    public static final String POLICY_STUB_PACKAGE = "diaspora.dms.stubs";
    public static final String STUB_SUFFIX = "_Stub";
    public static final String STUB_PACKAGE_PART = "stubs";
    
    public static String getAppPackageName(String classPackageName) {
    	return classPackageName + ".stubs";
    }
    
    public static String getPolicyPackageName() {
    	return POLICY_STUB_PACKAGE;
    }
}
