package lv.latcraft.utils

import groovy.util.slurpersupport.GPathResult

class XmlMethods {

  static setElementValue(GPathResult svg, String elementId, String value) {
    findElementById(svg, elementId)?.replaceBody(value)
  }

  static setAttributeValue(GPathResult svg, String elementId, String attributeId, String value) {
    findElementById(svg, elementId)?.@"${attributeId}" = value
  }

  static Object findElementById(GPathResult svg, String elementId) {
    svg.depthFirst().find { it.@id == elementId }
  }

}
