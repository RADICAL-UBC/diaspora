package diaspora.appexamples.simpletwitter.device;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import diaspora.appexamples.simpletwitter.app.Timeline;
import diaspora.appexamples.simpletwitter.app.TagManager;
import diaspora.appexamples.simpletwitter.app.Tweet;
import diaspora.appexamples.simpletwitter.app.TwitterManager;
import diaspora.appexamples.simpletwitter.app.User;
import diaspora.appexamples.simpletwitter.app.UserManager;
import diaspora.kernel.server.KernelServer;
import diaspora.kernel.server.KernelServerImpl;
import diaspora.oms.OMSServer;

public class TwitterActivityTwo {
	public static void main(String[] args) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(args[0],Integer.parseInt(args[1]));
			OMSServer server = (OMSServer) registry.lookup("DiasporaOMS");

            KernelServer nodeServer = new KernelServerImpl(new InetSocketAddress("10.0.2.15", Integer.parseInt(args[1])), new InetSocketAddress(args[2], Integer.parseInt(args[3])));
            
			TwitterManager tm = (TwitterManager) server.getAppEntryPoint();
            System.out.println("Received Twitter Manager Stub: " + tm);
            
            UserManager userManger = tm.getUserManager();
            TagManager tagManager = tm.getTagManager();
            
            User me = userManger.addUser("aaasz", "aaasz");
            Timeline myTimeline = me.getTimeline();
            User peer = userManger.getUser("iyzhang");
            
            peer.addFollowing(me);
            me.addFollower(peer);
            
            Timeline peerTimeline = peer.getTimeline();
            List<Tweet> peerTweets = peerTimeline.getTweets(0, 1);
            myTimeline.retweet(peerTweets.get(0));

            List<Tweet> myTweets = myTimeline.getTweets(0, 1);
            System.out.println("My tweet:" + myTweets.get(0).getText());
            
            List<Tweet> tweetsForTag = tagManager.getTweetsForTag("#goodlife", 0, 1);
            System.out.println(tweetsForTag.get(0).getText());

            System.out.println("Retweets: " + peerTimeline.getRetweets(peerTweets.get(0).getId(), 0, 1).get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
