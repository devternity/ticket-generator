package lv.latcraft.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.AccessControlList
import com.amazonaws.services.s3.model.GroupGrantee
import com.amazonaws.services.s3.model.Permission
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
@CompileStatic
class S3Methods {

  static AmazonS3Client getS3() {
    new AmazonS3Client()
  }

  static AccessControlList anyoneWithTheLink() {
    AccessControlList acl = new AccessControlList()
    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read)
    acl
  }

}
