package eus.jarriaga.cycling.business.propertyeditors;

import eus.jarriaga.cycling.business.entities.Role;
import eus.jarriaga.cycling.business.repositories.RoleRepository;

import java.beans.PropertyEditorSupport;

public class RoleEditor extends PropertyEditorSupport {

    private RoleRepository roleRepository;

    public RoleEditor(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String getAsText() {
        Role exoticType = (Role) getValue();
        return exoticType == null ? "" : exoticType.getName();
    }

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        setValue(roleRepository.findById(Long.valueOf(id)));
    }


}
