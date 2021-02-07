package pixyel.demo;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import compression.Compression;
import encryption.Encryption;

public class TestPanel {

    public static Layout getCompressionLayout() {
        GridLayout compressionLayout = new GridLayout(2, 3);
        compressionLayout.setSpacing(true);

        Label toBeCompressedLabel = new Label("1. Enter the String to be compressed: ");
        TextField toBeCompressedTextField = new TextField();
        Label compressButtonLabel = new Label("2. Compress it: ");
        Button compressButton = new Button("Compress!");
        Label resultLabel = new Label("3. Read the compressed Result: ");
        TextField resultTextField = new TextField();

        compressButton.addClickListener((listener) -> {
            try {
                resultTextField.setValue(Compression.compress(toBeCompressedTextField.getValue()));
            } catch (Compression.CompressionException ex) {
                resultTextField.setValue("COMPRESSION FAILED: " + ex);
            }
        });

        compressionLayout.setMargin(true);
        compressionLayout.addComponent(toBeCompressedLabel, 0, 0);
        compressionLayout.addComponent(toBeCompressedTextField, 1, 0);
        compressionLayout.addComponent(compressButtonLabel, 0, 1);
        compressionLayout.addComponent(compressButton, 1, 1);
        compressionLayout.addComponent(resultLabel, 0, 2);
        compressionLayout.addComponent(resultTextField, 1, 2);

        return compressionLayout;
    }

    public static Layout getDeompressionLayout() {
        GridLayout decompressionLayout = new GridLayout(2, 3);
        decompressionLayout.setSpacing(true);

        Label toBeDecompressedLabel = new Label("1. Enter the String to be decompressed: ");
        TextField toBeDecompressedTextField = new TextField();
        Label DecompressButtonLabel = new Label("2. Decompress it: ");
        Button decompressButton = new Button("Decompress!");
        Label resultLabel = new Label("3. Read the decompressed Result: ");
        TextField resultTextField = new TextField();

        decompressButton.addClickListener((listener) -> {
            try {
                resultTextField.setValue(Compression.decompress(toBeDecompressedTextField.getValue()));
            } catch (Compression.CompressionException ex) {
                resultTextField.setValue("DECOMPRESSION FAILED: " + ex);
            }
        });

        decompressionLayout.setMargin(true);
        decompressionLayout.addComponent(toBeDecompressedLabel, 0, 0);
        decompressionLayout.addComponent(toBeDecompressedTextField, 1, 0);
        decompressionLayout.addComponent(DecompressButtonLabel, 0, 1);
        decompressionLayout.addComponent(decompressButton, 1, 1);
        decompressionLayout.addComponent(resultLabel, 0, 2);
        decompressionLayout.addComponent(resultTextField, 1, 2);

        return decompressionLayout;
    }

    public static Layout getKeyPairLayout() {
        GridLayout keyPairLayout = new GridLayout(2, 3);
        keyPairLayout.setSpacing(true);

        Button generateKeyPairButton = new Button("Generate Key Pair!");
        Label privateKeyLabel = new Label("Private Key: ");
        TextField privateKeyTextField = new TextField();
        Label publicKeyLabel = new Label("Public Key: ");
        TextField publicKeyTextField = new TextField();

        generateKeyPairButton.addClickListener((listener) -> {
            try {
                String[] keyPair = Encryption.generateKeyPair();
                publicKeyTextField.setValue(keyPair[0]);
                privateKeyTextField.setValue(keyPair[1]);
            } catch (Encryption.EncryptionException ex) {
                publicKeyTextField.setValue("GENERATION FAILED: " + ex);
                privateKeyTextField.setValue("");
            }
        });

        keyPairLayout.setMargin(true);
        keyPairLayout.addComponent(generateKeyPairButton, 0, 0);
        keyPairLayout.addComponent(publicKeyLabel, 0, 1);
        keyPairLayout.addComponent(publicKeyTextField, 1, 1);
        keyPairLayout.addComponent(privateKeyLabel, 0, 2);
        keyPairLayout.addComponent(privateKeyTextField, 1, 2);

        return keyPairLayout;
    }

    public static Layout getEncryptionLayout() {
        GridLayout encryptionLayout = new GridLayout(3, 4);
        encryptionLayout.setSpacing(true);

        Label toBeEncryptedLabel = new Label("1. Enter the String to be encrypted: ");
        TextField toBeEncryptedTextField = new TextField();
        Label publicKeyLabel = new Label("2. Enter the Public Key to encrypt with: ");
        TextField publicKeyTextField = new TextField();
        CheckBox publicKeyCheckBox = new CheckBox("Use server public key");
        Label encryptButtonLabel = new Label("3. Encrypt it: ");
        Button encryptButton = new Button("Encrypt!");
        Label resultLabel = new Label("4. Read the encrypted Result: ");
        TextField resultTextField = new TextField();

        publicKeyCheckBox.addValueChangeListener(listener -> publicKeyTextField.setEnabled(!publicKeyCheckBox.getValue()));

        encryptButton.addClickListener((listener) -> {
            try {
                if (publicKeyCheckBox.getValue()) {
                    resultTextField.setValue(Encryption.encrypt(toBeEncryptedTextField.getValue(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmG8OfhJrkN9/rXLh7auyUPcq7UxmYModYswChY8hIMgZO4m+cxOWopxOptUAYedjA4ZAKGp/P1g6n6YaXvtPQqIbi7G5oCT4vbh0zYFgI3wNCJlKtUX1gb6uCQW3rPinANcPtlZoIyegAsn/OW0FMZtc1x8PN0H1MQTlcCctXdJdotuljeYriO1lkRfb3GsotLIYjciMqIMKGQRQ2Rhj81bnxP9FybdNuVIjlS6Rfx9fzaZ2BKIdm7O7/Dzn9TcSZEOZdOSS7CHMMKr14O26g+bR2HiGWx8AbOH2zP3DMpR9/Y8GUrjO6QPqA+GorICGYWxIlrcm4iYx8740FsDaQQIDAQAB"));
                } else {
                    resultTextField.setValue(Encryption.encrypt(toBeEncryptedTextField.getValue(), publicKeyTextField.getValue()));
                }
            } catch (Encryption.EncryptionException ex) {
                resultTextField.setValue("ENCRYPTION FAILED: " + ex);
            }
        });

        encryptionLayout.setMargin(true);
        encryptionLayout.addComponent(toBeEncryptedLabel, 0, 0);
        encryptionLayout.addComponent(toBeEncryptedTextField, 1, 0, 2, 0);
        encryptionLayout.addComponent(publicKeyLabel, 0, 1);
        encryptionLayout.addComponent(publicKeyTextField, 1, 1);
        encryptionLayout.addComponent(publicKeyCheckBox, 2, 1);
        encryptionLayout.addComponent(encryptButtonLabel, 0, 2);
        encryptionLayout.addComponent(encryptButton, 1, 2);
        encryptionLayout.addComponent(resultLabel, 0, 3);
        encryptionLayout.addComponent(resultTextField, 1, 3, 2, 3);

        return encryptionLayout;
    }

    public static Layout getDecryptionLayout() {
        GridLayout decryptionLayout = new GridLayout(3, 4);
        decryptionLayout.setSpacing(true);

        Label toBeDecryptedLabel = new Label("1. Enter the String to be decrypted: ");
        TextField toBeDecryptedTextField = new TextField();
        Label privateKeyLabel = new Label("2. Enter the Private Key to decrypt with: ");
        TextField privateKeyTextField = new TextField();
        CheckBox privateKeyCheckBox = new CheckBox("Use server private key");
        Label decryptButtonLabel = new Label("3. Decrypt it: ");
        Button decryptButton = new Button("Decrypt!");
        Label resultLabel = new Label("4. Read the decrypted Result: ");
        TextField resultTextField = new TextField();

        privateKeyCheckBox.addValueChangeListener(listener -> privateKeyTextField.setEnabled(!privateKeyCheckBox.getValue()));

        decryptButton.addClickListener((listener) -> {
            try {
                if (privateKeyCheckBox.getValue()) {
                    resultTextField.setValue(Encryption.decrypt(toBeDecryptedTextField.getValue(), "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCYbw5+EmuQ33+tcuHtq7JQ9yrtTGZgyh1izAKFjyEgyBk7ib5zE5ainE6m1QBh52MDhkAoan8/WDqfphpe+09CohuLsbmgJPi9uHTNgWAjfA0ImUq1RfWBvq4JBbes+KcA1w+2VmgjJ6ACyf85bQUxm1zXHw83QfUxBOVwJy1d0l2i26WN5iuI7WWRF9vcayi0shiNyIyogwoZBFDZGGPzVufE/0XJt025UiOVLpF/H1/NpnYEoh2bs7v8POf1NxJkQ5l05JLsIcwwqvXg7bqD5tHYeIZbHwBs4fbM/cMylH39jwZSuM7pA+oD4aisgIZhbEiWtybiJjHzvjQWwNpBAgMBAAECggEAa9DRPVamAlgypGnHZyW0ABRyplkNaJRMaJ7HgdQUqG0fe78Xl6lZODa6YsHxfU39+HYyVuPMkO9Et7rymA/Epeqm9Q2Fr8G2teoLo3dImpCZX2WdM84Bsf0+d1815QASj0ON93fyPDtAr/hTrzhvHgE3j2iGiJz8YemGpubu7ZZPVy89hwNhFcLZxEj8ZTI07nuy+uSiOaJQhrIl8jqH/Y099ex838zP4Bkqo321q5Rb0eYKB+cAB2oU6GQ411mARzkOwB6TI+PHB4l6MPryR24fO0JokiRafs178K3sRIEG2ZwC6VS/5cYDzoksEC/ztmmVV8UBmZXgTJqlQ9OjYQKBgQDTKXQw4pg7V9dV1ku+VrMc09EkI76SX+gZ8+qu4ogM4BFx7ifuwORRAv8pvvKXLN1N0o1br4JgEHnspAZdBOi/GqSnk93Mzdyu4eVeNJsWCW9uQG+x3JutsI4uTipGL3Ujf3RNVVBLz8Mn09iWKBq9x+k3DO6NS7ZsYIK7ueDyBQKBgQC4zTPeoGicyAiONGRwatS94gB7O0OB1OmR0ogjgHxKnZYji1fZZB3MtvspIThFbFM899+iRNwCm9DBSswzmAHCrUEdMVSapuLIcDnFFBD4bsGIi1WQSR0/sq9KXeDb7y2mXDOBdQKT+/YBYCIFSc0uMYuTKPJETXZbAZIqn1dQDQKBgFDmDNG9vjzeBm2oUBR3+t8Md4+08gn6HF31CPA/cYgdQBG6ACqEU/SFhWRk92PbmF18URPTRcuwBEYZAScZ/mFYv8RD7jHMnMOTX+Cbnt7udnZy0Vf/ANWmUpKC98cz31jeN5x5Fu40hrv3eX7tlnEm6b5hgfM4Eoeq4esx53BRAoGAfowWbucNe/8GzMpX1Rty3yx6A+kLS0bPQwoWK6l6c2YCQAjVeYozVdIfn4SeEfwh6+gZffcFacvlYdekidgXrIYTxrfXJagOOA+Rn8Ej9dtyL9yqFQncO5hSrOwoZLMeYYNVydqkj03EoiCAa3qkRHOtDnLFo7wfxiYHP6Spl6kCgYAyE7LN0YZFqHqsvYhLcJVfQtW2kEJp/1HnXIwMHLqDJVeQf3+JPGXZ9vAaonz1bKL9LNdkPXGjVYwxJ99HEdahwgD9TGs57MjfliF4tG9h38KCpSiGD1PXRq3/GJt2XsG5xK5gglBOauAVWRDZAZNqDCNiTIQtw3eXatyZPklrSw=="));
                } else {
                    resultTextField.setValue(Encryption.decrypt(toBeDecryptedTextField.getValue(), privateKeyTextField.getValue()));
                }
            } catch (Encryption.EncryptionException ex) {
                resultTextField.setValue("DECRYPTION FAILED: " + ex);
            }
        });

        decryptionLayout.setMargin(true);
        decryptionLayout.addComponent(toBeDecryptedLabel, 0, 0);
        decryptionLayout.addComponent(toBeDecryptedTextField, 1, 0, 2, 0);
        decryptionLayout.addComponent(privateKeyLabel, 0, 1);
        decryptionLayout.addComponent(privateKeyTextField, 1, 1);
        decryptionLayout.addComponent(privateKeyCheckBox, 2, 1);
        decryptionLayout.addComponent(decryptButtonLabel, 0, 2);
        decryptionLayout.addComponent(decryptButton, 1, 2);
        decryptionLayout.addComponent(resultLabel, 0, 3);
        decryptionLayout.addComponent(resultTextField, 1, 3, 2, 3);

        return decryptionLayout;
    }
}
