package lv.latcraft.utils

import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest
import groovy.transform.Canonical

@Canonical
class Webhook {

  {
    Unirest.objectMapper = new ObjectMapper() {
        def jackson = new com.fasterxml.jackson.databind.ObjectMapper()

        public Object readValue(String value, Class valueType) {
          jackson.readValue(value, valueType)
        }

        public String writeValue(Object value) {
          jackson.writeValueAsString(value)
        }
    }
  }

  String url

  def trigger(body) {
    def response = Unirest.post(url)
    .header("Content-Type", "application/json")
    .body(body)
    .asString()

    response.status
  }

}
