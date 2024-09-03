    package org.example;
    import jakarta.xml.bind.JAXBContext;
    import jakarta.xml.bind.JAXBException;
    import jakarta.xml.bind.Marshaller;
    import jakarta.xml.bind.annotation.XmlRootElement;
    import lombok.*;
    import java.io.StringWriter;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @XmlRootElement
    public class City {
        private String slug;
        private Coords coords;

        public String toXML() throws JAXBException {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);
            return stringWriter.toString();
        }


    }
