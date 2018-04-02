/**
 *  HubiThings Sonos Control
 *
 *  Copyright 2018 Stephan Hackett
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "HubiThings Sonos Control",
    namespace: "stephack",
    author: "Stephan Hackett",
    description: "Control Sonos in ST from Hubitat",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	page(name: "chooseVirt")
}


def chooseVirt() {
	dynamicPage(name: "chooseVirt", install: true, uninstall: true) {
		section("Step 1: Select Your Hubitat Virtual Device") {
			input "myVirt", "capability.switchLevel", title: "Hubitat Virtual Device", multiple: false, required: true, submitOnChange: true
			input "mySonos", "capability.musicPlayer", title: "Sonos Device", multiple: false, required: true, submitOnChange: true
		}
    }
}        
            
            

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(myVirt, "level", myHandler)
}

// TODO: implement event handlers


def myHandler(evt){
	log.debug evt.value
    def myLevel = evt.value.toInteger()
    //if(evt.name == "level"){
    	if(myLevel < 10){
        	log.debug "1's Event"
            myVirt.setLevel(100)
            mySonos.currentValue('status').contains('playing')? mySonos.pause() : mySonos.play()
        }
        if(myLevel >= 10 && myLevel < 20){
        	log.debug "10's Event"
            myVirt.setLevel(100)
            mySonos.nextTrack()
        }
    	if(myLevel >= 20 && myLevel < 30){
        	log.debug "20's Event"
            myVirt.setLevel(100)
            def currentVol = mySonos.currentValue('level')	//currentlevel return a list...[0] is first item in list ie volume level
    		def newVol = currentVol + 5
  			mySonos.setLevel(newVol)
        }
        if(myLevel >= 30 && myLevel < 40){
        	log.debug "30's Event"
            myVirt.setLevel(100)
            def currentVol = mySonos.currentValue('level')
    		def newVol = currentVol.toInteger() - 5
  			mySonos.setLevel(newVol)
        }
        if(myLevel >= 40 && myLevel < 50){
        	log.debug "40's Event"
            myVirt.setLevel(100)
            
        }
    
   // }
    
}