package code 
package snippet 

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import java.util.Date
import Helpers._
import js.JsCmds._
import js.JE.{JsRaw, JsArray}
import js.JsCmds.JsCrVar
import js.{JsObj, JE, JsCmd}
import JE._
import _root_.scala.xml.{NodeSeq, Text}

class GoogleMaps {

  // replace the contents of the element with what map to render
  def jsData = renderGoogleMap()

  // converts a the location into a json object
  def makeLocation(title: String, lat: String, lng: String,iwc: String): JsObj = {
    JsObj(("title", title),
      ("lat", lat),
      ("lng", lng),
      ("iwc",iwc))
  }

   // called by renderGoogleMap which passes the list of locations 
   // into the javascript function as json objects
  def ajaxFunc(locobj: List[JsObj]): JsCmd = {
    //JsCrVar is a JSCmds type that creates the java script var named "locations" and assingns it to
    //the JsObj using the js.JE type member JsArray to hold the objects sj.JE type member JsRaw holds 
    //the expression that will be evaluated 
    JsCrVar("locations", JsObj(("loc", JsArray(locobj: _*)))) & JsRaw("drawmap(locations)").cmd
  }

  // render the google map
  def renderGoogleMap(): NodeSeq = {
    // setup some locations (Marker/infoWindow content) to display on the map
	val locations: List[JsObj] = List(makeLocation("loc1","40.744715", "-74.0046","Info window content text for loc1 ....."),makeLocation("loc2","40.75684", "-73.9966","<div style='border: 1px solid #000;margin:5px;color:green'>Info window content text for loc2 .....</div>"))
	
	// where the magic happens
    (<head_merge>
      {Script(OnLoad(ajaxFunc(locations)))}
    </head_merge>)
  }
}

