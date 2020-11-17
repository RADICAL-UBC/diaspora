package diaspora.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Modifier;

import org.apache.harmony.rmi.common.RMIUtil;

import diaspora.app.DiasporaObject;
import diaspora.dms.DiasporaPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;

public class StubGenerator {

	public static void generateStub(String stubType, Class<?> c, String destFolder) {
		System.out.println("Generating stub for: " + c.getName());
        Stub s;
        
		try {
			if (stubType.equals("policy"))
				s = new PolicyStub(c);
			else
				s = new AppStub(c);
			
			String shortClassName = RMIUtil.getShortName(c);
			String stubName = destFolder + File.separator + shortClassName + GlobalStubConstants.STUB_SUFFIX + ".java";
			File dest = new File(stubName);
			dest.createNewFile();

			BufferedWriter out = new BufferedWriter(new FileWriter(dest));
			out.write(s.getStubSource());
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String removeExtension(String s) {

	    String separator = File.separator;
	    String filename;

	    // Remove the path up to the filename.
	    int lastSeparatorIndex = s.lastIndexOf(separator);
	    if (lastSeparatorIndex == -1) {
	        filename = s;
	    } else {
	        filename = s.substring(lastSeparatorIndex + 1);
	    }

	    // Remove the extension.
	    int extensionIndex = filename.lastIndexOf(".");
	    if (extensionIndex == -1)
	        return filename;

	    return filename.substring(0, extensionIndex);
	}
	
	public static void generateStubs(String srcFolder, String packageName, String destFolder) {
		File directory = new File(srcFolder);
		File[] fList = directory.listFiles();
		for (File file : fList){
			if (file.isFile() && file.getName().endsWith(".class")) {
				try {
					Class<?> c = Class.forName(StubGenerator.removeExtension(packageName + "." + file.getName()));
					
					if (Modifier.isAbstract(c.getModifiers()))
						continue;
					
					if (DiasporaPolicy.class.isAssignableFrom(c)) {
						Class<?> [] policyClasses = c.getDeclaredClasses();

						Class<?> diasporaServerPolicyClass = null;
						Class<?> diasporaGroupPolicyClass = null;

						for (Class<?> cls : policyClasses) {
							if (DiasporaServerPolicy.class.isAssignableFrom(cls)) {
								diasporaServerPolicyClass = cls;
								continue;
							}
							if (DiasporaGroupPolicy.class.isAssignableFrom(cls)) {
								diasporaGroupPolicyClass = cls;
								continue;
							}
						}
						StubGenerator.generateStub("policy", diasporaServerPolicyClass, destFolder);
						StubGenerator.generateStub("policy", diasporaGroupPolicyClass, destFolder);
					}
					else
						if (DiasporaObject.class.isAssignableFrom(c))
							StubGenerator.generateStub("app", c, destFolder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory() && ! file.getName().equals(GlobalStubConstants.STUB_PACKAGE_PART)){
				generateStubs(file.getAbsolutePath(), packageName + "." + file.getName(), destFolder);
			}
		}
	}
	
	/**
	 * Generates the stubs for the kernel/app objects
	 * 
	 * @param args[0] Path to the folder where the classes are.
	 * @param args[1] Name of the package.
	 * @param args[2] Path to the folder where to put the resulting java files.
	 */
	
	/* TODO: Support for multiple packages for app stubs; right now you must run this for each app package that contains a DiasporaObject */
	public static void main(String args[]) {
		// TODO: Create destFolder if it doesn't exist or delete existing stubs
		generateStubs(args[0], args[1], args[2]);
	}
}
