package br.ufma.lsdi.interscitydemo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.val;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Route("")
public class IndexView extends VerticalLayout {

    private TextArea responseCapabilityTextArea;
    private TextArea responseResourceTextArea;

    private final String baseUri = "http://cidadesinteligentes.lsdi.ufma.br/eq1/";

    public IndexView() {
        val hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.add(capabilityForm());
        hl.add(resourceForm());
        add(hl);
    }

    private VerticalLayout capabilityForm() {
        val capabilityLayout = new VerticalLayout();
        capabilityLayout.setSizeFull();
        val  capabilityLabel = new Label("New Capability");
        capabilityLayout.add(capabilityLabel);
        val nameTextField = new TextField("Name");
        nameTextField.setSizeFull();
        capabilityLayout.add(nameTextField);
        val descriptionTextField = new TextField("Description");
        descriptionTextField.setSizeFull();
        capabilityLayout.add(descriptionTextField);
        val typeTextField = new TextField("Type (sensor/actuator)");
        capabilityLayout.add(typeTextField);
        val addCapabilityButton = new Button("Add");
        addCapabilityButton.addClickListener(e ->
                addCapability(nameTextField.getValue(), descriptionTextField.getValue(),
                        typeTextField.getValue()));
        capabilityLayout.add(addCapabilityButton);
        responseCapabilityTextArea = new TextArea("Capability");
        responseCapabilityTextArea.setSizeFull();
        capabilityLayout.add(responseCapabilityTextArea);
        return capabilityLayout;
    }

    private void addCapability(String name, String description, String type) {
        try {
            val rt = new RestTemplate();
            val capability = new HashMap<>();
            capability.put("name", name);
            capability.put("description", description);
            capability.put("capability_type", type);
            val response = rt.postForObject(baseUri + "/catalog/capabilities", capability, Map.class);
            responseCapabilityTextArea.setValue(response.toString());
        }
        catch (Exception e) {
            responseCapabilityTextArea.setValue(e.getMessage());
        }
    }

    private VerticalLayout resourceForm() {
        val resourceLayout = new VerticalLayout();
        resourceLayout.setSizeFull();
        val  resourceLabel = new Label("New Resource");
        resourceLayout.add(resourceLabel);
        val descriptionTextField = new TextField("Description");
        descriptionTextField.setSizeFull();
        resourceLayout.add(descriptionTextField);
        val capabilitiesTextField = new TextField("Capabilities (comma separated list)");
        capabilitiesTextField.setSizeFull();
        resourceLayout.add(capabilitiesTextField);
        val statusTextField = new TextField("Status (active or inactive)");
        val latitudeTextField = new TextField("Latitude");
        val longitudeTextField = new TextField("Longitude");
        val hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.add(statusTextField);
        hl.add(latitudeTextField);
        hl.add(longitudeTextField);
        resourceLayout.add(hl);
        val addResourceButton = new Button("Add");
        addResourceButton.addClickListener(e -> addResource(
                descriptionTextField.getValue(),
                capabilitiesTextField.getValue().replace(" ", "").split(","),
                statusTextField.getValue(),
                Double.parseDouble(latitudeTextField.getValue()),
                Double.parseDouble(longitudeTextField.getValue())
        ));
        resourceLayout.add(addResourceButton);
        responseResourceTextArea = new TextArea("Resource");
        responseResourceTextArea.setSizeFull();
        resourceLayout.add(responseResourceTextArea);
        return resourceLayout;
    }

    private void addResource(String description, String[] capabilities, String status, double latitude, double longitude) {
        try {
            val rt = new RestTemplate();
            val data = new HashMap<>();
            val resource = new HashMap<>();
            resource.put("name", description);
            resource.put("capabilities", capabilities);
            resource.put("status", status);
            resource.put("lat", latitude);
            resource.put("lon", longitude);
            data.put("data", resource);
            val response = rt.postForObject(baseUri + "/adaptor/resources", data, Map.class);
            responseResourceTextArea.setValue(response.toString());
        }
        catch (Exception e) {
            responseResourceTextArea.setValue(e.getMessage());
        }
    }

}
