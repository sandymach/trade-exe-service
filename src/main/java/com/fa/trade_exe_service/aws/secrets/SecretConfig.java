package com.fa.trade_exe_service.aws.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

public class SecretConfig {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getDbSecret() throws Exception {
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.EU_NORTH_1)
                .build()) {

            String secretJson = client.getSecretValue(
                    GetSecretValueRequest.builder()
                            .secretId("fin-db-secrets")
                            .build()
            ).secretString();

            System.out.println(secretJson + "  :::secretJson");
            return mapper.readTree(secretJson);
        }
    }
}
