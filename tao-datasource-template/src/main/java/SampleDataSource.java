import ro.cs.tao.datasource.remote.URLDataSource;

import java.net.URISyntaxException;

public class SampleDataSource extends URLDataSource<SampleQuey> {
    /**
     * Constructs a new URLDataSource with the given url.
     *
     * @param url The URL of the data source
     * @throws URISyntaxException If the given url is malformed.
     */
    public SampleDataSource(String url) throws URISyntaxException {
        super(url);
    }

    protected SampleQuey createQueryImpl(String code) {
        return null;
    }
}
