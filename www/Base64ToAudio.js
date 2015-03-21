var exec = require("cordova/exec");

/*cordova.define("org.voiceapp.convAudio", function(require, exports, module){
    
});*/

var Base64ToAudio = {
    saveAudio: function (conf, successCallback, failCallback)
    {
        function success(args) {
            if (typeof successCallback === 'function')
                successCallback(args);
        }

        function fail(args) {
            if (typeof failCallback === 'function')
                failCallback(args);
        }
        
        return exec(
            function (args) { success(args); },
            function (args) { fail(args); },
            'Base64ToAudio',
            'saveAudio',
            [conf]);
    },

    readAudio: function(filePath, successCallback, failCallback) {
        var args = {};
        args.filePath = filePath;

        function success(args) {
            if (typeof successCallback === 'function')
                successCallback(args);
        }

        function fail(args) {
            if (typeof failCallback === 'function')
                failCallback(args);
        }
       
        return exec(
            function (args) { success(args); },
            function (args) { fail(args); },
            "Base64ToAudio", 
            "readAudio", [
            args]);
    }
}
module.exports = Base64ToAudio;