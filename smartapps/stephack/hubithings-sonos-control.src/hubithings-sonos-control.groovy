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
			input "volAdj", "number", title: "+- volume by this value", multiple: false, required: true, submitOnChange: true
			//input "myPreset", "device.VirtualContainer", title: "Preset Container", multiple: false, required: true, submitOnChange: true
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
    subscribe(myVirt, "level", myHandler)
}

def myHandler(evt){
	//log.debug evt.value
    def myLevel = evt.value.toInteger()
	switch(myLevel){
		case 11:
        	log.debug "Pushed 1 event Received"
            myVirt.setLevel(100)
            mySonos.currentValue('status').contains('playing')? mySonos.pause() : mySonos.play()
        break
        case 21:
        	log.debug "Pushed 2 event Received"
            myVirt.setLevel(100)
            def currentVol = mySonos.currentValue('level')
    		def newVol = currentVol + volAdj
  			mySonos.setLevel(newVol)
        break
        case 31:
        	log.debug "Pushed 3 event Received"
            myVirt.setLevel(100)
            //myPreset.cycleChild()
        break
        case 41:
        	log.debug "Pushed 4 event Received"
            myVirt.setLevel(100)
            def currentVol = mySonos.currentValue('level')
    		def newVol = currentVol - volAdj
  			mySonos.setLevel(newVol)
       	break
        case 51:
        	log.debug "Pushed 5 event Received"
            myVirt.setLevel(100)
            mySonos.nextTrack()
        break
        case 100:
        	log.debug "Value reset"
        break
        default: 
        	log.debug "No HubiThings command associated with that value."
        break
    }
}