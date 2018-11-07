import ro.cs.tao.datasource.DataQuery;
import ro.cs.tao.eodata.EOProduct;

import java.util.List;

public class SampleQuey extends DataQuery {

    public SampleQuey(SampleDataSource source) {
        super(source, "SampleSensor");
    }

    protected List<EOProduct> executeImpl() {
        return null;
    }
}
