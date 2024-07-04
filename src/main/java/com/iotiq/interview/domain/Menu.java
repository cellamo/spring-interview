package com.iotiq.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Menu extends AbstractPersistable<UUID> {

    String name;

    @OneToMany
    List<Category> categories = new ArrayList<>();

}
