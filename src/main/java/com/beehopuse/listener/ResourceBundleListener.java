package com.beehopuse.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

@WebListener
public class ResourceBundleListener implements ServletContextListener {

    private static final Control XML_CONTROL = new XMLResourceBundleControl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, Map<String, String>> messages = new HashMap<>();

        // Load English messages
        ResourceBundle enBundle = ResourceBundle.getBundle("i18n.messages", new Locale("en"), XML_CONTROL);
        Map<String, String> enMessages = new HashMap<>();
        enBundle.keySet().forEach(key -> enMessages.put(key, enBundle.getString(key)));
        messages.put("en", enMessages);

        // Load French messages
        ResourceBundle frBundle = ResourceBundle.getBundle("i18n.messages", new Locale("fr"), XML_CONTROL);
        Map<String, String> frMessages = new HashMap<>();
        frBundle.keySet().forEach(key -> frMessages.put(key, frBundle.getString(key)));
        messages.put("fr", frMessages);

        // Store messages in application scope
        sce.getServletContext().setAttribute("messages", messages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up if needed
    }

    private static class XMLResourceBundleControl extends Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                boolean reload)
                throws IllegalAccessException, InstantiationException, java.io.IOException {
            if (format.equals("xml")) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, "xml");
                try (java.io.InputStream stream = loader.getResourceAsStream(resourceName)) {
                    if (stream != null) {
                        return new XMLResourceBundle(stream);
                    }
                }
            }
            return super.newBundle(baseName, locale, format, loader, reload);
        }

        @Override
        public List<String> getFormats(String baseName) {
            return Collections.singletonList("xml");
        }
    }

    private static class XMLResourceBundle extends ResourceBundle {
        private final Map<String, String> properties = new HashMap<>();

        public XMLResourceBundle(java.io.InputStream stream) throws java.io.IOException {
            try {
                javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory
                        .newInstance();
                javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document document = builder.parse(stream);
                org.w3c.dom.NodeList entries = document.getElementsByTagName("entry");

                for (int i = 0; i < entries.getLength(); i++) {
                    org.w3c.dom.Node node = entries.item(i);
                    if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                        String key = element.getAttribute("key");
                        String value = element.getTextContent();
                        properties.put(key, value);
                    }
                }
            } catch (Exception e) {
                throw new java.io.IOException("Error parsing XML resource bundle", e);
            }
        }

        @Override
        protected Object handleGetObject(String key) {
            return properties.get(key);
        }

        @Override
        public java.util.Enumeration<String> getKeys() {
            return java.util.Collections.enumeration(properties.keySet());
        }
    }
}