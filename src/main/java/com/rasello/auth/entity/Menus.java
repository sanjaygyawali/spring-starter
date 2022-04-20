package com.rasello.auth.entity;

import com.rasello.auth.core.annotation.IsMeta;
import com.rasello.auth.core.services.MetaListener;
import com.rasello.auth.core.services.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "menus")
@IsMeta
//@EqualsAndHashCode(callSuper = true)
@EntityListeners(MetaListener.class)
//@Getter
//@Setter
public class Menus extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Long id;

    private String name;
    private String url;
    private String icon;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Menus> schema = new ArrayList<Menus>();

    private String role;
    private Integer menuOrder;

}
