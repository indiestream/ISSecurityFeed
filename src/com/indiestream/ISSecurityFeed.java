package com.indiestream;

import java.util.List;

import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.amf.AMFPacket;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.stream.IMediaStreamActionNotify2;
import com.wowza.wms.stream.publish.Playlist;
import com.wowza.wms.stream.publish.PlaylistItem;
import com.wowza.wms.stream.publish.Stream;

public class ISSecurityFeed extends ModuleBase {

	private Playlist _playlist;
	private Stream _stream;
	private String _streamName = "feed";
	private Boolean _publishing = false;
	
	public void onAppStart(IApplicationInstance appInstance) {
	
		String fullname = appInstance.getApplication().getName() + "/" + appInstance.getName();
		getLogger().info("onAppStart: " + fullname);

		// Create the playlist the cameras will cycle through
		_playlist = new Playlist("playlist");
		
		// Set the playlist so it repeats when it gets to the end of the list.
		_playlist.setRepeat(true);
		
		// Add the default stream which is a VOD file stored in the /content folder.
		// This file will play when no cameras are publishing to the app.
		_playlist.addItem("mp4:offline.mp4", 0, 40);

		// Create the stream that will be published out
		_stream = Stream.createInstance(appInstance, _streamName);
		
		// Open the stream using the content in the playlist. To start will be the 'Offline' video.
		_playlist.open(_stream);
	
	}

	public void onAppStop(IApplicationInstance appInstance) {
		String fullname = appInstance.getApplication().getName() + "/" + appInstance.getName();
		getLogger().info("onAppStop: " + fullname);
	}

	public void onConnect(IClient client, RequestFunction function, AMFDataList params) {
		getLogger().info("onConnect: " + client.getClientId());
	}

	public void onConnectAccept(IClient client) {
		getLogger().info("onConnectAccept: " + client.getClientId());
	}

	public void onConnectReject(IClient client) {
		getLogger().info("onConnectReject: " + client.getClientId());
	}

	public void onDisconnect(IClient client) {
		getLogger().info("onDisconnect: " + client.getClientId());
	}

	public void onStreamCreate(IMediaStream stream) {
		
		getLogger().info("onStreamCreate: " + stream.getSrc());
		
		// When a stream connects to the app, add an event listener to it.
		stream.addClientListener(new StreamNotify());
		
	}

	public void onStreamDestroy(IMediaStream stream) {
		getLogger().info("onStreamDestroy: " + stream.getSrc());
	}
	

	class StreamNotify implements IMediaStreamActionNotify2
	{

		public void onPlay(IMediaStream stream, String streamName, double playStart, double playLen, int playReset)
		{
		}

		public void onPause(IMediaStream stream, boolean isPause, double location)
		{
		}

		public void onSeek(IMediaStream stream, double location)
		{
		}

		public void onStop(IMediaStream stream)
		{
		}

		public void onMetaData(IMediaStream stream, AMFPacket metaDataPacket)
		{
		}

		public void onPauseRaw(IMediaStream stream, boolean isPause, double location)
		{
		}
		
		public void onPublish(IMediaStream stream, String streamName, boolean isRecord, boolean isAppend)
		{

			if(stream.getName() != _streamName && stream.getName() != "audio" && stream.getName() != "feed")
			{

				if(!_publishing)
				{
					
					_playlist.removeItem(0);
					_publishing = true;

				}
				
				_playlist.addItem(stream.getName(), -2, 15);

				_playlist.open(_stream);
				
			}
			
		}

		public void onUnPublish(IMediaStream stream, String streamName, boolean isRecord, boolean isAppend)
		{
			
			List<PlaylistItem> streams = _playlist.getItems();
			
			// Parse through the list of playlist items.
			for( int i = 0; i < streams.size(); i++)
			{

				if(streams.get(i).getName() == streamName)
				{
					
					_playlist.removeItem(i);

					if(_playlist.getItems().size() == 0) {
						_playlist.addItem("mp4:offline.mp4", 0, 40);
						_publishing = false;
					}
					
					_playlist.open(_stream);
					break;
					
				}
				
			}
			
		}
		
	}
	
}