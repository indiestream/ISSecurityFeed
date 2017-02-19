# ISAutoStart

Occasionally as a streaming engineer you often need to have a Live stream running to test on. Especially if you're working on a player. Here's a useful library to get one going in Wowza with the need of an encoder or webcam. By default the program runs on the application name ```autostart```. You can change that via the properites explained below. You can learn more about how it works [here](http://www.indiestre.am/index.php/2017/02/16/auto-starting-an-application-using-wowza/).

---

## Setup

1.) Copy ISAutoStart.jar to your Wowza ```lib/``` folder.

2.) Edit the Wowza configuration file located at ```conf/Server.xml``` Scroll down to the bottom and add the following in the ```<ServerListeners>``` node.

```
<ServerListener>
  <BaseClass>com.indiestream.ISAutoStart</BaseClass>
</ServerListener>
```

3.) Further down in the same file add the following two properties in the ```<Properties>``` node. You can update the values to represet a different VHost and the application name then the default ones.

```
<Property>
  <Name>autoStartVHostName</Name>
  <Value>_defaultVHost_</Value>
  <Type>String</Type>
</Property>
<Property>
  <Name>autoStartApplicationName</Name>
  <Value>autostart</Value>
  <Type>String</Type>
</Property>
```

Where:
* **autoStartVHostName** - The name of the VHost the application will run on
* **autoStartApplicationName** - The name of application that will be started on boot.


4.) Now create the application by adding the ```autostart``` folder in both the ```applications``` and ```conf```. Copy the application configuration file ```Application.xml``` from the ```live``` application into the new folder.

5.) Edit the newly created ```Application.xml``` file located in ```conf/autostart/Application.xml```. Scroll down to the bottom and add the following properties in the ```<Properties>``` node. You can update the default values to your own.

```
<Property>
  <Name>autoStartFileName</Name>
  <Value>mp4:sample.mp4</Value>
  <Type>String</Type>
</Property>
<Property>
  <Name>autoStartStartTime</Name>
  <Value>0</Value>
  <Type>Integer</Type>
</Property>
<Property>
  <Name>autoStartDuration</Name>
  <Value>10</Value>
  <Type>Integer</Type>
</Property>
<Property>
  <Name>autoStartStreamName</Name>
  <Value>feed</Value>
  <Type>String</Type>
</Property>
```

Where:
* **autoStartFileName** - The name of the file to play in the content folder.
* **autoStartStartTime** - The start time of the movie.
* **autoStartDuration** - The duration of playback time of the movie.
* **autoStartStreamName** - The name of the Live output stream.


---
