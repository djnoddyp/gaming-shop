package me.pnodder.gateway.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.javax.validation.JavaxValidationModule;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.net.URL;

public class InventoryRequestValidator implements RequestValidator {
    JavaxValidationModule validationModule = new JavaxValidationModule();
    SchemaGeneratorConfigBuilder configBuilder =
            new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON)
                    .with(validationModule);
    SchemaGeneratorConfig config = configBuilder.build();
    //    SchemaGenerator generator = new SchemaGenerator(config);
    //    JsonNode jsonSchema = generator.generateSchema(Item.class);
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ValidationResponse validate(RoutingContext request) {
        boolean isValid = false;
        try {
            isValid = validateAgainstSchema(request.getBodyAsJson());
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }
        return new ValidationResponse(isValid);
    }

    private boolean validateAgainstSchema(JsonObject bodyAsJson) throws IOException, ProcessingException {
        URL schemaUrl = InventoryRequestValidator.class.getClassLoader()
                .getResource("schemas/v1_inventory_item.json");
        JsonNode itemSchema = objectMapper.readTree(schemaUrl);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(itemSchema);

        JsonNode badJson = objectMapper.readTree(bodyAsJson.encode());
        ProcessingReport report = schema.validate(badJson);
        return report.isSuccess();
    }
}
