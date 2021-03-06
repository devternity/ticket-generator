package lv.latcraft.devternity.tickets

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@CompileStatic
@TypeChecked
@Canonical
class TicketInfo {

  String webhook
  String email
  String name
  String company
  String product
  String ticketId
  String when
  String what

}
