/**
 *  Boot Me Up Scottie
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
    name: "Boot Me Up Scottie",
    namespace: "stephack",
    author: "Stephan Hackett",
    description: "Wake On Lan (WOL) functionality",
    category: "My Apps",
    iconUrl: "https://raw.githubusercontent.com/stephack/Virtual/master/resources/images/power.png",
    iconX2Url: "https://raw.githubusercontent.com/stephack/Virtual/master/resources/images/power.png",
    iconX3Url: "https://raw.githubusercontent.com/stephack/Virtual/master/resources/images/power.png")

preferences {
    section("Choose Device to Activate WOL") {
        input "myDevice", "capability.momentary", title: "Choose a switch",  required: true
        input "myMac", "text", title: "MAC of workstation", required: true
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
	subscribe(myDevice, "on", myWOLCommand)
}

def myWOLCommand(evt) {
	def newMac = myMac.replaceAll(":","").replaceAll("-","")
    sendHubCommand(new physicalgraph.device.HubAction (
        "wake on lan ${newMac}",
        physicalgraph.device.Protocol.LAN,
        null))
}