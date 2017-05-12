package lv.latcraft.utils

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
@CompileStatic
class QRMethods {

  static byte[] renderQRCodePNGImage(String content, int width = 300, int height = 300) {
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream()
    EnumMap hints = new EnumMap<EncodeHintType, Object>(EncodeHintType)
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8")
    hints.put(EncodeHintType.MARGIN, 0)
    BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)
    MatrixToImageWriter.writeToStream(bitMatrix, "png", byteStream)
    byteStream.toByteArray()
  }

}
