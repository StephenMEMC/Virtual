/**
 *  Virtual Momentary Button Device Handler
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
 *	
 */

metadata {
	definition (name: "Virtual Momentary Button", namespace: "stephack", author: "Stephan Hackett") {
	capability "Switch"
	capability "Sensor"
	capability "Actuator"
    capability "Momentary"
    
    attribute "buttonNo", "number"	//keeps track of what number this switch is in the container

	}

	tiles(scale: 2) {
    	standardTile("switch", "switch", width: 5, height: 1) {
			state "off", label:'Push', action: on, icon: "https://cdn.rawgit.com/stephack/SPC/master/resources/images/momentary buttonoff.png", nextState: "turningOn"
			state "turningOn", label:'Push', action: on, icon: "https://cdn.rawgit.com/stephack/SPC/master/resources/images/momentary buttonon.png", nextState: "on"
        }
    	valueTile("buttonNo", "buttonNo", decoration: "flat", width: 5, height: 1) {
			state "buttonNo", label:'${currentValue}', action: on
        }
		main "switch"
		details(["switch"])

	}

}

def push() {
	int whichChild = device.currentValue('buttonNo')
   	log.info "VMS $whichChild pushed"	
	sendEvent(name: "switch", value: "on", isStateChange: true, displayed: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, displayed: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
    parent.childOn(whichChild)
}

def on() {		 
	push()
}

def off() {
	push()
}