/**
 *  Virtual Switch
 *
 *  Copyright 2017 Stephan Hackett
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
 *	Icons by http://www.icons8.com	
 */

metadata {
	definition (name: "Virtual Switch", namespace: "stephack", author: "Stephan Hackett") {
	capability "Switch"
	capability "Switch Level"
	capability "Sensor"
	capability "Actuator"
    capability "Momentary"
    attribute "conLabel", "string"	//stores container name for access by other smartApps
    attribute "buttonNo", "number"	//keeps track of what number this switch is in the container
	}

	tiles(scale: 2) {
    	standardTile("switch", "switch", decoration: "flat", width: 5, height: 1) {
			state "off", label: "", action: on, icon: "https://cdn.rawgit.com/stephack/Virtual/master/resources/images/switchoffb.png"
			state "on",  label: "", action: off, icon: "https://cdn.rawgit.com/stephack/Virtual/master/resources/images/switchonb.png"//backgroundColor: "#00A0DC"
        }
    	valueTile("buttonNo", "buttonNo", decoration: "flat", width: 5, height: 1) {
			state "buttonNo", label:'${currentValue}', action: on
        }
		main "switch"
		details(["switch"])

	}

}

def on() {
   	log.info "VMS $whichChild turned ON"	
	int whichChild = device.currentValue('buttonNo')
	sendEvent(name: "switch", value: "on", isStateChange: true, displayed: false)
    parent.childOn(whichChild)
}

def off() {
   	log.info "VMS $whichChild turned OFF"	
	int whichChild = device.currentValue('buttonNo')
	sendEvent(name: "switch", value: "off", isStateChange: true, displayed: false)
    parent.childOff(whichChild)
}

def setLevel(val){
	int whichChild = device.currentValue('buttonNo')
    log.info "VMS set to LEVEL $val"
    parent.childLevel(val, whichChild)
    sendEvent(name:"level",value:val)
}


