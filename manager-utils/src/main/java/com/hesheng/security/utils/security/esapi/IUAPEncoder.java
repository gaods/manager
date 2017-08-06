//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.security.utils.security.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.*;
import org.owasp.esapi.codecs.MySQLCodec.Mode;
import org.owasp.esapi.errors.EncodingException;

import java.io.IOException;

public class IUAPEncoder {
    private static final Encoder encoder = ESAPI.encoder();
    private static final char HTML_PLACEHODER = '?';

    public IUAPEncoder() {
    }

    public String sqlEncode(String inputString, IUAPEncoder.DatabaseCodec dbcodec) {
        return encoder.encodeForSQL(dbcodec.codec(), inputString);
    }

    public String sqlPreparedString(String sqlTemplate, String[] paras, IUAPEncoder.DatabaseCodec dbcodec) {
        PreparedString sqlPreparedString = new PreparedString(sqlTemplate, dbcodec.codec());

        for(int i = 0; i < paras.length; ++i) {
            sqlPreparedString.set(i + 1, paras[i]);
        }

        return sqlPreparedString.toString();
    }

    public String htmlEncode(String inputString) {
        return encoder.encodeForHTML(inputString);
    }

    public String htmlAttributeEncode(String inputString) {
        return encoder.encodeForHTMLAttribute(inputString);
    }

    public String cssEncode(String inputString) {
        return encoder.encodeForCSS(inputString);
    }

    public String javaScriptEncode(String inputString) {
        return encoder.encodeForJavaScript(inputString);
    }

    public String urlEncode(String inputString) throws Exception {
        try {
            return encoder.encodeForURL(inputString);
        } catch (EncodingException var3) {
            throw new Exception(var3);
        }
    }

    public String urlDecode(String url) throws Exception {
        try {
            return encoder.decodeFromURL(url);
        } catch (EncodingException var3) {
            throw new Exception(var3);
        }
    }

    public String xmlEncode(String inputString) {
        return encoder.encodeForXML(inputString);
    }

    public String xmlAttributeEncode(String inputString) {
        return encoder.encodeForXMLAttribute(inputString);
    }

    public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs, char placeholder) {
        PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, IUAPEncoder.TextCodec.HTML.codec());

        for(int i = 0; i < paras.length; ++i) {
            clientSidePreparedString.set(i + 1, paras[i], codecs[i]);
        }

        return clientSidePreparedString.toString();
    }

    public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs) {
        return this.webPreparedString(strTemplate, paras, codecs, '?');
    }

    public String webPreparedString(String strTemplate, String[] paras, IUAPEncoder.TextCodec[] codecs, char placeholder) {
        PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, IUAPEncoder.TextCodec.HTML.codec());

        for(int i = 0; i < paras.length; ++i) {
            clientSidePreparedString.set(i + 1, paras[i], codecs[i].codec());
        }

        return clientSidePreparedString.toString();
    }

    public String webPreparedString(String strTemplate, String[] paras, IUAPEncoder.TextCodec[] codecs) {
        return this.webPreparedString(strTemplate, paras, codecs, '?');
    }

    public String webPreparedString(String strTemplate, String param, IUAPEncoder.TextCodec codec) {
        return this.webPreparedString(strTemplate, new String[]{param}, new IUAPEncoder.TextCodec[]{codec}, '?');
    }

    public String encodeForBase64(byte[] data) {
        return encoder.encodeForBase64(data, false);
    }

    public byte[] decodeFromBase64(String text) throws IOException {
        return encoder.decodeFromBase64(text);
    }

    public static enum TextCodec {
        CSS(new CSSCodec()),
        HTML(new HTMLEntityCodec()),
        JS(new JavaScriptCodec()),
        PERCENT(new PercentCodec()),
        XML(new XMLEntityCodec()),
        UNIX(new UnixCodec()),
        WINDOWS(new WindowsCodec()),
        VB(new VBScriptCodec());

        private Codec codec;

        private TextCodec(Codec codec) {
            this.codec = codec;
        }

        public Codec codec() {
            return this.codec;
        }
    }

    public static enum DatabaseCodec {
        ORACLE(new OracleCodec()),
        MYSQL_ANSI(new MySQLCodec(Mode.ANSI)),
        MYSQL_STANDARD(new MySQLCodec(Mode.STANDARD)),
        DB2(new DB2Codec());

        private Codec codec;

        private DatabaseCodec(Codec codec) {
            this.codec = codec;
        }

        public Codec codec() {
            return this.codec;
        }
    }
}
