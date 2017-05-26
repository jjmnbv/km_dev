package com.kmzyc.framework.container.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.UnknownHostException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XML {
    public static Element getRootElement(InputStream stream) throws DocumentException {
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEntityResolver(new DTDEntityResolver());
            saxReader.setMergeAdjacentText(true);
            return saxReader.read(stream).getRootElement();
        } catch (DocumentException e) {
            Throwable nested = e.getNestedException();
            if (nested != null) {
                if (nested instanceof FileNotFoundException) {
                    throw new RuntimeException("Can't find schema/DTD reference: " + nested.getMessage(), e);
                } else if (nested instanceof UnknownHostException) {
                    throw new RuntimeException("Cannot connect to host from schema/DTD reference: " +
                            nested.getMessage() +
                            " - check that your schema/DTD reference is current", e);
                }
            }
            throw e;
        }
    }


    /**
     * Parses an XML document safely, as to not resolve any external DTDs
     */
    public static Element getRootElementSafely(InputStream stream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        saxReader.setEntityResolver(new NullEntityResolver());
        saxReader.setMergeAdjacentText(true);
        return saxReader.read(stream).getRootElement();
    }


    public static class NullEntityResolver implements EntityResolver {
        private static final byte[] empty = new byte[0];

        public InputSource resolveEntity(String systemId, String publicId)
                throws SAXException, IOException {
            return new InputSource(new ByteArrayInputStream(empty));
        }

    }

    public static class DTDEntityResolver implements EntityResolver, Serializable {

        private static final long serialVersionUID = -4553926061006790714L;

        //private static final LogProvider log = Logging.getLogProvider(DTDEntityResolver.class);

        private static final String SEAM_NAMESPACE = null;//"http://jboss.com/products/seam/";
        private static final String USER_NAMESPACE = "classpath://";

        public InputSource resolveEntity(String publicId, String systemId) {
            if (systemId != null) {
                //log.debug("trying to resolve system-id [" + systemId + "]");
                if (systemId.startsWith(SEAM_NAMESPACE)) {
                    //log.debug("recognized Seam namespace; attempting to resolve on classpath under org/jboss/seam/");
                    String path = "org/jboss/seam/" + systemId.substring(SEAM_NAMESPACE.length());

                    InputStream dtdStream = resolveInSeamNamespace(path);
                    if (dtdStream == null) {
                        //log.warn("unable to locate [" + systemId + "] on classpath");
                    } else {
                        //log.debug("located [" + systemId + "] in classpath");
                        InputSource source = new InputSource(dtdStream);
                        source.setPublicId(publicId);
                        source.setSystemId(systemId);
                        return source;
                    }
                } else if (systemId.startsWith(USER_NAMESPACE)) {
                    //log.debug("recognized local namespace; attempting to resolve on classpath");
                    String path = systemId.substring(USER_NAMESPACE.length());

                    InputStream stream = resolveInLocalNamespace(path);
                    if (stream == null) {
                        //log.warn("unable to locate [" + systemId + "] on classpath");
                    } else {
                        //log.debug("located [" + systemId + "] in classpath");
                        InputSource source = new InputSource(stream);
                        source.setPublicId(publicId);
                        source.setSystemId(systemId);
                        return source;
                    }
                }
            }
            // use default behavior
            return null;
        }

        protected InputStream resolveInSeamNamespace(String path) {
            return this.getClass().getClassLoader().getResourceAsStream(path);
        }

        protected InputStream resolveInLocalNamespace(String path) {
            //try  {
            //    return Resources.getResourceAsStream(path, ServletLifecycle.getServletContext());
            //} catch (Throwable t) {
            return null;
            //}
        }
    }

}
