package lv.latcraft.devternity.tickets

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import groovy.util.logging.Commons
import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil
import lv.latcraft.utils.HttpEndpoint

import static lv.latcraft.utils.FileMethods.temporaryFile
import static lv.latcraft.utils.QRMethods.renderQRCodePNGImage
import static lv.latcraft.utils.S3Methods.anyoneWithTheLink
import static lv.latcraft.utils.S3Methods.s3
import static lv.latcraft.utils.SanitizationMethods.sanitizeCompany
import static lv.latcraft.utils.SanitizationMethods.sanitizeName
import static lv.latcraft.utils.SvgMethods.renderPDF
import static lv.latcraft.utils.SvgMethods.renderPNG
import static lv.latcraft.utils.XmlMethods.setAttributeValue
import static lv.latcraft.utils.XmlMethods.setElementValue

@Commons
class TicketGenerator {

    public static final String BUCKET_NAME = 'devternity-images'

    public static final String FIREBASE_DB = 'devternity-22e74';
    public static final String FIREBASE_COLLECTION = 'tickets.json';


    static Map<String, String> generate(Map<String, String> data, Context context) {
        log.info "STEP 1: Received data: ${data}"
        TicketInfo ticket = new TicketInfo(data)
        File svgFile = temporaryFile('ticket', '.svg')
        byte[] qrPngData = renderQRCodePNGImage(getQRData(ticket))
        log.info "STEP 2: Generated QR image"
        File qrFile = temporaryFile('ticket-qr', '.png')
        qrFile.bytes = qrPngData
        log.info "STEP 3: Saved QR image"
        s3.putObject(putRequest(ticket, qrFile, '-qr.png'))
        log.info "STEP 4: Uploaded PDF ticket"
        svgFile.text = prepareSVG(getSvgTemplate(ticket.product), ticket, qrPngData)
        log.info "STEP 5: Pre-processed SVG template"
        File pdfFile = renderPDF(svgFile)
        log.info "STEP 6: Generated PDF ticket ($pdfFile)"
        s3.putObject(putRequest(ticket, pdfFile, '.pdf'))
        log.info "STEP 7: Uploaded PDF ticket"
        File pngFile = renderPNG(svgFile)
        log.info "STEP 8: Generated PNG preview ($pngFile)"
        s3.putObject(putRequest(ticket, pngFile, '.png'))
        log.info "STEP 9: Uploaded PNG preview"
        svgFile.delete()
        def response = [
                status: 'OK',
                qr    : "https://s3-eu-west-1.amazonaws.com/${BUCKET_NAME}/ticket-${ticket.ticketId}-qr.png".toString(),
                pdf   : "https://s3-eu-west-1.amazonaws.com/${BUCKET_NAME}/ticket-${ticket.ticketId}.pdf".toString(),
                png   : "https://s3-eu-west-1.amazonaws.com/${BUCKET_NAME}/ticket-${ticket.ticketId}.png".toString()
        ]


        def firebase = new HttpEndpoint(url: "https://${FIREBASE_DB}.firebaseio.com/${FIREBASE_COLLECTION}")
        def firebaseStatus = firebase.post([
                ticketId: ticket.ticketId,
                holder  : ticket.name,
                email   : ticket.email
        ])
        log.info "STEP 10: Firebase write responded with ($firebaseStatus)"


        if (ticket.webhook) {
            def webHook = new HttpEndpoint(url: ticket.webhook)
            def status = webHook.post(response + [data: data])
            log.info "STEP 11: Webhook ($webHook.url) responded with ($status)"
        }

        response
    }

    static PutObjectRequest putRequest(TicketInfo ticket, File file, String extension) {
        def metadata = new ObjectMetadata()
        metadata.addUserMetadata "name", ticket.name

        new PutObjectRequest(
                BUCKET_NAME,
                "ticket-${ticket.ticketId}${extension}",
                file
        )
                .withMetadata(metadata)
                .withAccessControlList(anyoneWithTheLink())
    }


    static String getSvgTemplate(String product) {
        String templateName = "DEVTERNITY_TICKET_${product}.svg"
        getClass().getResource("/${templateName}")?.text ?: new File(templateName).text
    }

    static String prepareSVG(String svgText, TicketInfo ticket, byte[] qrImage) {
        GPathResult svg = new XmlSlurper().parseText(svgText)
        setElementValue(svg, 'ticket-name', sanitizeName(ticket.name).toUpperCase())
        setElementValue(svg, 'ticket-company', sanitizeCompany(ticket.company))
        setElementValue(svg, 'ticket-when', ticket.when)
        setElementValue(svg, 'ticket-what', ticket.what.replaceAll('_', ' '))
        setAttributeValue(svg, 'ticket-qr', 'xlink:href', "data:image/png;base64,${qrImage.encodeBase64().toString().toList().collate(76)*.join('').join(' ')}".toString())
        XmlUtil.serialize(svg)
    }

    static getQRData(TicketInfo ticket) {
        "${ticket.ticketId}"
    }

}
