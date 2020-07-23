package us.lacchain.crossborder.management.mapper;

import us.lacchain.crossborder.management.clients.response.PaymentInitiationResponse;
import us.lacchain.crossborder.management.clients.response.PaymentStatusResponse;
import java.io.*;
import java.io.InputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import java.util.*;
import org.dom4j.XPath;
import org.apache.commons.codec.binary.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.stream.Collectors;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class PaymentInitiationMapper{

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String ACSP = "ACSP";

    public String xmlToPaymentInitiationRequest(int amount){
        try (InputStream inputStream = getClass().getResourceAsStream("/requestPaymentInitiation.xml")){
   
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            Map<String, String> namespaceContext = new HashMap<>();
            namespaceContext.put("ns", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");

            Element root = document.getRootElement();

            Element creDtTm = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:GrpHdr[1]/ns:CreDtTm[1]", document, namespaceContext);
            System.out.println(creDtTm.asXML()); 
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            creDtTm.setText(getTimeFormatted(timestamp,"yyyy-MM-dd'T'HH:mm:ss"));

            Element ctrlSum = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:GrpHdr[1]/ns:CtrlSum[1]", document, namespaceContext);
            ctrlSum.setText(getAmount(amount));
            
            Element reqdExctnDt = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:PmtInf[1]/ns:ReqdExctnDt[1]", document, namespaceContext);
            reqdExctnDt.setText(getTimeFormatted(timestamp,"yyyy-MM-dd"));

            Element msgId = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:GrpHdr[1]/ns:MsgId[1]", document, namespaceContext);
            msgId.setText("CBP"+getRandomId(7));

            Element instrId = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:PmtInf[1]/ns:CdtTrfTxInf[1]/ns:PmtId[1]/ns:InstrId[1]", document, namespaceContext);
            instrId.setText("INS"+getRandomId(9));

            Element endToEndId = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:PmtInf[1]/ns:CdtTrfTxInf[1]/ns:PmtId[1]/ns:EndToEndId[1]", document, namespaceContext);
            endToEndId.setText("END"+getRandomId(12));

            Element amt = (Element) select("//ns:CstmrCdtTrfInitn[1]/ns:PmtInf[1]/ns:CdtTrfTxInf[1]/ns:Amt[1]/ns:EqvtAmt[1]/ns:Amt[1]", document, namespaceContext);
            amt.setText(getAmount(amount));

            System.out.println("<<<paymentInitiationRequest>>"+document.asXML());

            byte[] bytesEncoded = Base64.encodeBase64(document.asXML().getBytes());

            String requestEncoded = "<Request><paymentBase64>"+new String(bytesEncoded)+"</paymentBase64></Request>";

            return requestEncoded;

        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public PaymentInitiationResponse mapPaymentInitiationResponse(String response){
        try{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(response));

        Element psr1 = (Element) document.selectSingleNode("//Response[1]/psr1[1]");
        System.out.println("psr1:"+psr1.getText()); 

        byte[] bytesDecoded = Base64.decodeBase64(psr1.getText().getBytes());

        document = reader.read(new StringReader(new String(bytesDecoded)));

        Map<String, String> namespaceContext = new HashMap<>();
        namespaceContext.put("ns", "urn:iso:std:iso:20022:tech:xsd:pain.002.001.03");

        Element msgId = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:GrpHdr[1]/ns:MsgId[1]", document, namespaceContext);
        System.out.println("msgId:"+msgId.getText()); 

        Element instrId = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:OrgnlInstrId[1]", document, namespaceContext);
        System.out.println("instrId:"+instrId.getText()); 

        Element endToEndId = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:OrgnlEndToEndId[1]", document, namespaceContext);
        System.out.println("endToEndId:"+endToEndId.getText()); 

        Element txSts = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:TxSts[1]", document, namespaceContext);
        System.out.println("txSts:"+txSts.getText()); 

        Element acctSvcrRef = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:AcctSvcrRef[1]", document, namespaceContext);
        System.out.println("acctSvcrRef:"+acctSvcrRef.getText()); 

        Element addtlInfElement = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:OrgnlTxRef[1]/ns:RmtInf[1]/ns:Strd[1]/ns:RfrdDocAmt[1]/ns:AdjstmntAmtAndRsn[1]/ns:AddtlInf[1]", document, namespaceContext);
        System.out.println("addtlInfElement:"+addtlInfElement.getText()); 

        DecimalFormat df = new DecimalFormat("0.0000");
        df.setMaximumFractionDigits(4);

        float addtlInf = Float.parseFloat(addtlInfElement.getText());
        System.out.println("valor:"+df.format(addtlInf));

        PaymentInitiationResponse paymentResponse =  new PaymentInitiationResponse(msgId.getText(), instrId.getText(), endToEndId.getText(), txSts.getText(), acctSvcrRef.getText(), addtlInf);

        return paymentResponse;
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public String xmlToPaymentStatusRequest(String pEndToEndId){
        try (InputStream inputStream = getClass().getResourceAsStream("/requestPaymentStatus.xml")){
   
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            Map<String, String> namespaceContext = new HashMap<>();
            namespaceContext.put("ns", "http://com.citi.citiconnect/services/types/inquiries/payment/v1");

            Element endToEndId = (Element) select("//ns:PaymentInquiryRequest[1]/ns:EndToEndId[1]", document, namespaceContext);
            System.out.println(endToEndId.asXML()); 
            
            endToEndId.setText(pEndToEndId);

            return document.asXML();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public PaymentStatusResponse mapPaymentStatusResponse(String response, String endToEndId){
        PaymentStatusResponse paymentStatusResponse = new PaymentStatusResponse(false,0.0f);
        try{
            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(response));

            Map<String, String> namespaceContext = new HashMap<>();
            namespaceContext.put("ns", "urn:iso:std:iso:20022:tech:xsd:pain.002.001.03");

            Element orgnlEndToEndId = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:OrgnlEndToEndId[1]", document, namespaceContext);
            System.out.println(orgnlEndToEndId.asXML()); 

            if (endToEndId.equalsIgnoreCase(orgnlEndToEndId.getText())){
                Element txSts = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:TxSts[1]", document, namespaceContext);
                System.out.println(txSts.asXML()); 
                if (ACSP.equalsIgnoreCase(txSts.getText())){
                    Element addtlInfElement = (Element) select("//ns:CstmrPmtStsRpt[1]/ns:OrgnlPmtInfAndSts[1]/ns:TxInfAndSts[1]/ns:OrgnlTxRef[1]/ns:RmtInf[1]/ns:Strd[1]/ns:RfrdDocAmt[1]/ns:AdjstmntAmtAndRsn[1]/ns:AddtlInf[1]", document, namespaceContext);
                    System.out.println(addtlInfElement.asXML()); 
                    DecimalFormat df = new DecimalFormat("0.0000");
                    df.setMaximumFractionDigits(4);

                    float addtlInf = Float.parseFloat(addtlInfElement.getText());
                    System.out.println("valor:"+df.format(addtlInf));

                    paymentStatusResponse.setExecuted(true);
                    paymentStatusResponse.setAddtlInf(Float.parseFloat(df.format(addtlInf)));    
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return paymentStatusResponse;
    }

    public static Object select(String expression, Branch contextNode, Map<String, String> namespaceContext) {
        XPath xp = contextNode.createXPath(expression);
        xp.setNamespaceURIs(namespaceContext);
        return xp.evaluate(contextNode);
    }

    private String getTimeFormatted(Timestamp timestamp, String format){
        Instant instant = timestamp.toInstant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault());
        System.out.println("format:"+formatter.format(instant));
        return formatter.format(instant);
    }

    private String getAmount(int amount){
        float flo = (float) amount/10000;
        DecimalFormat df = new DecimalFormat("########.00");
        df.setMaximumFractionDigits(2);
        return df.format(flo);
    }

    private String getRandomId(int count){
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}