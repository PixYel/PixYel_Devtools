package pixyel.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class PixYel_UI extends UI {

    Window compression;
    Window decompression;
    Window keyPairGeneration;
    Window encryption;
    Window decryption;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        compression = new Window("Compression", TestPanel.getCompressionLayout());
        addWindow(compression);

        decompression = new Window("Decompression", TestPanel.getDeompressionLayout());
        addWindow(decompression);

        keyPairGeneration = new Window("Keypair Generation", TestPanel.getKeyPairLayout());
        addWindow(keyPairGeneration);

        encryption = new Window("Encryption", TestPanel.getEncryptionLayout());
        addWindow(encryption);

        decryption = new Window("Decryption", TestPanel.getDecryptionLayout());
        addWindow(decryption);

        //arrangeWindows(30);
    }

    public void addWindows(Window window) {
        window.addResizeListener(listener -> arrangeWindows(30));
        window.addWindowModeChangeListener(listener -> {
            if (listener.getWindowMode() == WindowMode.NORMAL) {
                arrangeWindows(30);
            }
        });
        window.setResizable(false);
        addWindow(window);
    }

    public void arrangeWindows(int padding) {
        int[] compressionSize = {(int) compression.getWidth(), (int) compression.getHeight()};
        int[] decompressionSize = {(int) decompression.getWidth(), (int) decompression.getHeight()};
        int[] keyPairGenerationSize = {(int) TestPanel.getKeyPairLayout().getWidth(), (int) TestPanel.getKeyPairLayout().getHeight()};
        int[] encryptionSize = {(int) encryption.getWidth(), (int) encryption.getHeight()};
        int[] decryptionSize = {(int) decryption.getWidth(), (int) decryption.getHeight()};

        int[] browserSize = {Page.getCurrent().getBrowserWindowWidth(), Page.getCurrent().getBrowserWindowHeight()};
        //System.out.println("Browser: " + browserSize[0] + ", " + browserSize[1]);

        int WIDTHX = keyPairGenerationSize[0] + encryptionSize[0] + decryptionSize[0] + (2 * padding);
        System.out.println("WIDTHX: " + WIDTHX);

        keyPairGeneration.setPositionX((browserSize[0] / 2) - (WIDTHX / 2));
        keyPairGeneration.setPositionY((browserSize[1] / 4) - (keyPairGenerationSize[1] / 2));

        encryption.setPositionY((browserSize[0] / 2) - (WIDTHX / 2) + keyPairGenerationSize[0] + padding);
        encryption.setPositionY((browserSize[1] / 4) - (encryptionSize[1] / 2));

        decryption.setPositionX((browserSize[0] / 2) - ((browserSize[0] / 2) - (WIDTHX / 2) + decryptionSize[0]) + (browserSize[0] / 2));
        decryption.setPositionY((browserSize[1] / 4) - (decryptionSize[1] / 2));
        decryption.center();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PixYel_UI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
