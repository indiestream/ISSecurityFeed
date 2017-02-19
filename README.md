# ISSecurityFeed

Occasionally as a streaming engineer you often need to have a Live stream running to test on. Especially if you're working on a player. Here's a useful library to get one going in Wowza with the need of an encoder or webcam. By default the program runs on the application name ```securityfeed```. You can change that via the properites explained below. You can learn more about how it works [here](http://www.indiestre.am/index.php/2017/02/16/auto-starting-an-application-using-wowza/).

---

## Setup

1.) Copy ISSecurityFeed.jar to your Wowza ```lib/``` folder.

2.) Edit the Wowza configuration file located at ```conf/Server.xml``` Scroll down to the bottom and add the following in the ```<ServerListeners>``` node.

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
* **autoStartVHostName** - The name of the VHost the application will run on
* **autoStartApplicationName** - The name of application that will be started on boot.


4.) Now create the application by adding the ```securityfeed``` folder in both the ```applications``` and ```conf```. Copy the application configuration file ```Application.xml``` from the ```live``` application into the new folder.

5.) Edit the newly created ```Application.xml``` file located in ```conf/securityfeed/Application.xml```. Scroll down to the bottom and add the following properties in the ```<Properties>``` node. You can update the default values to your own.

```
<Property>
  <Name>securityFeedNoSignalFileName</Name>
  <Value>mp4:sample.mp4</Value>
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
* **securityFeedNoSignalFileName** - The name of the file to play when there are no cameras publishing.
* **securityFeedRotationDelay** - The delay between camera feeds.
* **securityFeedStreamName** - The name of the Live stream.


---
