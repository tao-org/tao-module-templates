package parameters;

import download.SampleDownloadStrategy;
import ro.cs.tao.configuration.ConfigurationManager;
import ro.cs.tao.datasource.ProductFetchStrategy;
import ro.cs.tao.datasource.param.DataSourceParameter;
import ro.cs.tao.datasource.param.ParameterName;
import ro.cs.tao.datasource.param.ParameterProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SampleParameterProvider implements ParameterProvider {

    public Map<String, Map<ParameterName, DataSourceParameter>> getSupportedParameters() {
        return Collections.unmodifiableMap(
                new HashMap<String, Map<ParameterName, DataSourceParameter>>() {{
                    put("SampleSensor", new LinkedHashMap<ParameterName, DataSourceParameter>() {{
                        put(ParameterName.create("sampleParameter", "sampleParameter"),
                            new DataSourceParameter("remoteParameter", String.class, "default_value"));
                    }});
                }});
    }

    public String[] getSupportedSensors() {
        return new String[] { "SampleSensor" };
    }

    public Map<String, ProductFetchStrategy> getRegisteredProductFetchStrategies() {
        return Collections.unmodifiableMap(
                new HashMap<String, ProductFetchStrategy>() {{
                    put("SampleSensor",
                        new SampleDownloadStrategy(ConfigurationManager.getInstance().getValue("product.location")));
                }});
    }
}
