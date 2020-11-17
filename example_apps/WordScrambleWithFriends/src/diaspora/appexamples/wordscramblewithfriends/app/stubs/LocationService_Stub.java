/*
 * Stub for class diaspora.appexamples.wordscramblewithfriends.app.LocationService
 * Generated by Diaspora Compiler (sc).
 */
package diaspora.appexamples.wordscramblewithfriends.app.stubs;


public final class LocationService_Stub extends diaspora.appexamples.wordscramblewithfriends.app.LocationService implements diaspora.common.AppObjectStub {

    diaspora.policy.DiasporaPolicy.DiasporaClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public LocationService_Stub () {
        super();
    }


    public void $__initialize(diaspora.policy.DiasporaPolicy.DiasporaClientPolicy client) {
        $__client = client;
    }

    public void $__initialize(boolean directInvocation) {
        $__directInvocation = directInvocation;
    }

    public Object $__clone() throws CloneNotSupportedException {
        return super.clone();
    }



    // Implementation of initialize(Context)
    public void initialize(android.content.Context $param_Context_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.initialize( $param_Context_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void diaspora.appexamples.wordscramblewithfriends.app.LocationService.initialize(android.content.Context)";
                $__params.add($param_Context_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getLocation()
    public android.location.Location getLocation() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getLocation();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public android.location.Location diaspora.appexamples.wordscramblewithfriends.app.LocationService.getLocation()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((android.location.Location) $__result);
    }

    // Implementation of deactivateLocationService()
    public void deactivateLocationService() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.deactivateLocationService();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void diaspora.appexamples.wordscramblewithfriends.app.LocationService.deactivateLocationService()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of activateLocationService()
    public void activateLocationService() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.activateLocationService();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void diaspora.appexamples.wordscramblewithfriends.app.LocationService.activateLocationService()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
