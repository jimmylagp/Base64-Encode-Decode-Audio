# Base64 encode/decode MP3 audio
================================

You can encode and decode Base64 MP3 audio with this plugin for whatever you need on you cordova project.

Usage:

<script>
	//Encode Audio MP3 to Base64
	var pathToFile = "/pathToFile/audio.mp3";
	
	Base64ToAudio.readAudio(pathToFile, 
		function(sourceAudio)
		{
			console.log(sourceAudio) //Return Base64 String
		},
		function(error)
		{
			console.log(error);
		}
	);

	//Decode Audio MP3 from Base64 and save file on storage.
	var conf = { "b64string": sourceAudio, "filename": "new-voice.mp3", "folder": "/pathToDirectory/", "overwrite": true };
	
	Base64ToAudio.saveAudio(conf,
		function(success){
			console.log(success); //Return OK when the file is generated.
		},
		function(error){
			console.log(error);
		}
	);
</script>

This is a cordova plugin for Android only. Forked from dpfauwadel/PhonegapPlugin