package com.cypods.dbupdater.Configuration;

import org.springframework.stereotype.Component;


@Component
public class JsonRestTemplate {

//    public JsonRestTemplate(
//            ClientHttpRequestFactory clientHttpRequestFactory) {
//        super(clientHttpRequestFactory);
//
//        // Force a sensible JSON mapper.
//        // Customize as needed for your project's definition of "sensible":
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new Jdk8Module())
//                .registerModule(new JavaTimeModule())
//                .configure(
//                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter() {
//
//            public boolean canRead(java.lang.Class<?> clazz,
//                                   org.springframework.http.MediaType mediaType) {
//                return true;
//            }
//            public boolean canRead(java.lang.reflect.Type type,
//                                   java.lang.Class<?> contextClass,
//                                   org.springframework.http.MediaType mediaType) {
//                return true;
//            }
//            protected boolean canRead(
//                    org.springframework.http.MediaType mediaType) {
//                return true;
//            }
//        };
//
//        jsonMessageConverter.setObjectMapper(objectMapper);
//        messageConverters.add(jsonMessageConverter);
//        super.setMessageConverters(messageConverters);
//
//    }
//
//    @Override
//    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
//        return null;
//    }
}