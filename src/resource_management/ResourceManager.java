package resource_management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResourceManager implements Serializable {

    private List<Resource> resources = new ArrayList<Resource>();

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public List<Resource> getResources() {
        return resources;
    }

    public Resource getResource(String resourceName) {
        return resources.stream().filter(resource -> resource.name.equals(resourceName)).findFirst().orElse(null);
    }

}
