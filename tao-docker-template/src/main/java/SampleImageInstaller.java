import org.apache.commons.lang3.SystemUtils;
import ro.cs.tao.component.ProcessingComponent;
import ro.cs.tao.component.SourceDescriptor;
import ro.cs.tao.component.TargetDescriptor;
import ro.cs.tao.component.enums.ProcessingComponentType;
import ro.cs.tao.docker.Container;
import ro.cs.tao.persistence.PersistenceManager;
import ro.cs.tao.security.SystemPrincipal;
import ro.cs.tao.topology.docker.BaseImageInstaller;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class SampleImageInstaller extends BaseImageInstaller {
    @Override
    protected String getContainerName() {
        return "sample-container";
    }

    @Override
    protected String getDescription() {
        return "Sample Docker container";
    }

    @Override
    protected String getPathInContainer() {
        return "/usr/bin";
    }

    @Override
    protected String getPathInSystem() {
        Path path = findInPath(SystemUtils.IS_OS_WINDOWS ? "binary.exe" : "binary");
        return path != null ? path.getParent().toString() : null;
    }

    @Override
    protected Container initializeContainer(Container container, String path) {
        PersistenceManager persistenceManager = getPersistenceManager();
        Container sampleContainer = null;
        try {
            sampleContainer = readContainerDescriptor("container.json");
            sampleContainer.setId(sampleContainer.getId());
            sampleContainer.setName(sampleContainer.getName());
            sampleContainer.setTag(sampleContainer.getTag());
            sampleContainer.setApplicationPath(path);
            sampleContainer.getApplications().forEach(a -> {
                if (a.getPath() == null) {
                    a.setPath("binary");
                }
                if (SystemUtils.IS_OS_WINDOWS && !a.getPath().endsWith(".exe")) {
                    a.setPath(a.getPath() + ".exe");
                }
            });
            sampleContainer = persistenceManager.saveContainer(sampleContainer);
            ProcessingComponent[] components = readComponentDescriptors("applications.json");
            for (ProcessingComponent component : components) {
                try {
                    component.setContainerId(sampleContainer.getId());
                    component.setComponentType(ProcessingComponentType.EXECUTABLE);
                    component.setOwner(SystemPrincipal.instance().getName());
                    component.getParameterDescriptors().forEach(p -> {
                        if (p.getName() == null) {
                            p.setName(p.getId());
                            p.setId(UUID.randomUUID().toString());
                        }
                        String[] valueSet = p.getValueSet();
                        if (valueSet != null && valueSet.length == 1 &&
                                ("null".equals(valueSet[0]) || valueSet[0].isEmpty())) {
                            p.setValueSet(null);
                        }
                        if (valueSet != null && valueSet.length > 0 &&
                                ("null".equals(valueSet[0]) || valueSet[0].isEmpty())) {
                            p.setDefaultValue(valueSet[0]);
                        }
                    });
                    List<SourceDescriptor> sources = component.getSources();
                    if (sources != null) {
                        sources.forEach(s -> {
                            if (s.getId() == null || s.getId().isEmpty()) {
                                s.setId(UUID.randomUUID().toString());
                            }
                        });
                    }
                    List<TargetDescriptor> targets = component.getTargets();
                    if (targets != null) {
                        targets.forEach(t -> {
                            if (t.getId() == null || t.getId().isEmpty()) {
                                t.setId(UUID.randomUUID().toString());
                            }
                        });
                    }
                    component.addTags(getOrCreateTag(sampleContainer.getName()).getText());
                    persistenceManager.saveProcessingComponent(component);
                } catch (Exception inner) {
                    logger.severe(String.format("Faulty component: %s. Error: %s",
                                                component != null ? component.getId() : "n/a",
                                                inner.getMessage()));
                }
            }
        } catch (Exception outer) {
            logger.severe(String.format("Error occured while registering sampleContainer applications: %s",
                                        outer.getMessage()));
        }
        return sampleContainer;
    }
}
