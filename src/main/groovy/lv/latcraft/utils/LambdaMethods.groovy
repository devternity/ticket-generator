package lv.latcraft.utils

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@CompileStatic
@TypeChecked
class LambdaMethods {

  static boolean isInsideLambda() {
    new File('/var/task').exists()
  }

}
