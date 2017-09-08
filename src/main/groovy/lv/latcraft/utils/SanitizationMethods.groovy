package lv.latcraft.utils

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@CompileStatic
@TypeChecked
class SanitizationMethods {

  static final String LV_CHARS = 'āēūīķņčžšļõģŗ'
  static final String TR_CHARS = 'aeuiknczslogr'

  static String transliterateLatvianLetters(String name) {
    name?.toString()?.tr(LV_CHARS, TR_CHARS)
  }

  static String sanitizeName(String name) {
    name.
      trim().
      split('\\s+').
      collect { String part -> part.trim().capitalize() }.
      join(' ')
  }

  static String sanitizeCompany(String company) {
    sanitizeName(
      company.
        replaceAll('n/a', '').
        replaceAll('LV', '').
        replaceAll('Intelligent Technologies', 'IT').
        replaceAll('VSIA', '').
        replaceAll('vsia', '').
        replaceAll('sia', '').
        replaceAll('SIA', '').
        replaceAll('LTD', '').
        replaceAll('AS', '').
        replaceAll('ltd', '').
        replaceAll('Ltd.', '').
        replaceAll('Trade & Finance Group', '').
        replaceAll('Self Employed', '').
        replaceAll('GmbH', '').
        replaceAll('No Company :\\(', '').
        replaceAll('-', '').
        replaceAll('Latvia', '').
        replaceAll('private', '').
        trim().
        capitalize()
    )
  }
}
