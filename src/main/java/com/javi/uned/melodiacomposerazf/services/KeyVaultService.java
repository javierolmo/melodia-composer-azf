package com.javi.uned.melodiacomposerazf.services;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

public class KeyVaultService {

    public static final String DATABASE_SERVER = "bd-secret";
    public static final String DATABASE_USER = "bd-user-secret";
    public static final String DATABASE_PASSWORD = "bd-password-secret";

    public static final String BLOB_STORAGE_CS = "blob-storage-cs";


    private SecretClient secretClient;

    public KeyVaultService() {

        String keyVaultName = System.getenv("KEY_VAULT_NAME");
        String keyVaultClientId = System.getenv("KEY_VAULT_CLIENT_ID");
        String keyVaultClientSecret = System.getenv("KEY_VAULT_CLIENT_SECRET");
        String keyVaultTenantId = System.getenv("KEY_VAULT_TENANT_ID");
        String keyVaultUri = String.format("https://%s.vault.azure.net", keyVaultName);

        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .tenantId(keyVaultTenantId)
                .clientId(keyVaultClientId)
                .clientSecret(keyVaultClientSecret)
                .build();

        this.secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(credential)
                .buildClient();

    }

    public String getSecret(String secretName) {
        return this.secretClient.getSecret(secretName).getValue();
    }

}
