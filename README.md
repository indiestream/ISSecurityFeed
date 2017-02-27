# ISSecurityFeed

Here's an interesting Wowza module. What this does is take all the IP cameras that are publishing to the application and streams them into a live feed that rotates through each camera at a set delay. If no cameras are publishing then it plays a poster video that's stored in the content folder. By default it looks for `offline.mp4`. This project builds off a previous one [here](http://www.indiestre.am/?p=7) in which it starts the application on boot streaming the feed Live. You can learn more about how it works [here](http://http://www.indiestre.am/?p=1).

---

## Setup

1.) Copy `lib/ISSecurityFeed.jar` to your Wowza `lib/` folder and `assets/offline.mp4` to the `content/` folder.

2.) The following two steps are optional and involve starting the stream at server boot. This can be useful if you enable recording and you want to save the feed without a player currently streaming. Edit the Wowza configuration file located at ```conf/Server.xml``` Scroll down to the bottom and add the following in the ```<ServerListeners>``` node.

```
<ServerListener>
  <BaseClass>com.indiestream.issecurityfeed.ISSecurityFeedServer</BaseClass>
</ServerListener>
```

3.) Further down in the same file add the following two properties in the ```<Properties>``` node. You can update the values to represet a different VHost and the application name then the default ones.

```
<Property>
  <Name>securityFeedVHostName</Name>
  <Value>_defaultVHost_</Value>
  <Type>String</Type>
</Property>
<Property>
  <Name>securityFeedApplicationName</Name>
  <Value>securityfeed</Value>
  <Type>String</Type>
</Property>
```

Where:
* **securityFeedVHostName** - The name of the VHost the application will run on
* **securityFeedApplicationName** - The name of application that will be started on boot.


4.) Now create the application by adding the ```securityfeed``` folder in both the ```applications``` and ```conf```. Copy the application configuration file ```Application.xml``` from the ```live``` application into the new folder.

5.) Edit the newly created ```Application.xml``` file located in ```conf/autostart/Application.xml```. Scroll down near the bottom and add the module to the ```<Modules>``` node.

```
<Module>
	<Name>ISSecurityFeed</Name>
	<Description>Rotates through a series of IP Cameras</Description>
	<Class>com.indiestream.issecurityfeed.ISSecurityFeed</Class>
</Module>
```

6.) Scroll down a little further and add the following properties in the ```<Properties>``` node. You can update the default values to your own.

```
<Property>
  <Name>securityFeedNoSignalFileName</Name>
  <Value>mp4:offline.mp4</Value>
  <Type>String</Type>
</Property>
<Property>
  <Name>securityFeedRotationDelay</Name>
  <Value>15</Value>
  <Type>Integer</Type>
</Property>
<Property>
  <Name>securityFeedStreamName</Name>
  <Value>feed</Value>
  <Type>String</Type>
</Property>
```

Where:
* **securityFeedNoSignalFileName** - The name of the file to play when there are no cameras publishing. An example is included in the assets folder.
* **securityFeedRotationDelay** - The delay between camera feeds.
* **securityFeedStreamName** - The name of the Live stream.


---
