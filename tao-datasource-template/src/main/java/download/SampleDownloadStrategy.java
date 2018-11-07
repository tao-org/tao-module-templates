package download;

import ro.cs.tao.datasource.remote.DownloadStrategy;
import ro.cs.tao.eodata.EOProduct;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class SampleDownloadStrategy extends DownloadStrategy {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(SampleDownloadStrategy.class.getResourceAsStream("aws.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SampleDownloadStrategy(String targetFolder) {
        super(targetFolder, properties);
    }

    private SampleDownloadStrategy(SampleDownloadStrategy other) {
        super(other);
    }

    public SampleDownloadStrategy clone() {
        return new SampleDownloadStrategy(this);
    }

    protected Path fetchImpl(EOProduct product) throws IOException {
        return null;
    }

    protected String getMetadataUrl(EOProduct descriptor) {
        return null;
    }
}
