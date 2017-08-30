package net.es.oscars.conf.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.es.oscars.conf.prop.StartupConfigEntry;

import java.io.IOException;

public class StartupCfgSerializer extends JsonSerializer<StartupConfigEntry> {
    @Override
    public void serialize(StartupConfigEntry value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject(); //top

        jgen.writeObjectFieldStart("server");
        jgen.writeObjectField("port", value.getServer_port());

        jgen.writeObjectFieldStart("ssl");
        jgen.writeObjectField("enabled", value.getSsl_enabled());
        jgen.writeObjectField("ciphers", value.getSsl_ciphers());
        jgen.writeObjectField("key-store", value.getSsl_key_store());
        jgen.writeObjectField("key-store-type", value.getSsl_key_store_type());
        jgen.writeObjectField("key-store-password", value.getSsl_key_store_password());
        jgen.writeObjectField("key-alias", value.getSsl_key_alias());
        jgen.writeObjectField("key-password", value.getSsl_key_password());
        jgen.writeEndObject(); //ssl

        jgen.writeEndObject(); //server

        jgen.writeObjectFieldStart("security");

        jgen.writeObjectFieldStart("basic");
        jgen.writeObjectField("enabled", value.getSec_basic_enabled());
        jgen.writeEndObject(); //basic

        jgen.writeObjectFieldStart("user");
        jgen.writeObjectField("name", value.getSec_user_name());
        jgen.writeObjectField("password", value.getSec_user_password());
        jgen.writeEndObject(); //user

        jgen.writeEndObject(); //security

        jgen.writeObjectFieldStart("rest");
        jgen.writeObjectField("internal-username", value.getRest_username());
        jgen.writeObjectField("internal-password", value.getRest_password());
        jgen.writeObjectField("internal-truststore-path", value.getRest_truststore());
        jgen.writeEndObject(); //rest

        jgen.writeEndObject(); //top
    }
}
