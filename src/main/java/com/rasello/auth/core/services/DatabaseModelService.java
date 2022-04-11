package com.rasello.auth.core.services;

import com.rasello.auth.base.BaseDto;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

//@Service
public class DatabaseModelService<T extends  BaseEntity>{
    @Autowired
    private  EntityManager entityManager;
    @Autowired
    private ModelMapper modelMapper;
    private Class<?extends BaseEntity> entity;
    private Class<?extends BaseDto> dto;
    public void setEntity(Class<? extends BaseEntity> entity) {
        this.entity = entity;
    }
    public void setDto(Class<? extends BaseDto> dto){
        this.dto = dto;
    }
    @Transactional
    public BaseDto save(BaseDto dto){
        this.modelMapper.map(dto, this.entity);
        entityManager.persist(dto);
        return dto;
    }

    public List<? extends BaseEntity> findAll(){
        String JPQL = String.format("SELECT a from %s a", this.entity.getSimpleName());
        return entityManager.createQuery(JPQL, this.entity).getResultList();
    }
    private javax.persistence.TypedQuery<? extends BaseEntity> findOneById(Long id){
        String JPQL = String.format("SELECT d from %s d where d.id = %s", this.entity.getSimpleName(), id);
        return entityManager.createQuery(JPQL, this.entity);
    }

    public BaseEntity findById(Long id){
        try{
            var result = this.findOneById(id).getSingleResult();
            return result;
        }catch (NoResultException e){

        }
        return null;
    }

    public BaseEntity findByIdOrFail(Long id){
        try{
            var result = this.findOneById(id).getSingleResult();
            return result;
        }catch (NoResultException e){
            throw new RecordNotFoundException("Record not found");
        }
    }
    @Transactional
    public void deleteById(Long id){
//        TODO: never delete like this, referenced item should be also deleted in cascade ,
        String JPQL = String.format("DELETE from %s where id = %s", this.entity.getSimpleName(), id);
        entityManager.createQuery(JPQL).executeUpdate();
    }

    @Transactional
    public BaseEntity updateOneById(Long id, T dto) {
        var entity = entityManager.find(this.entity, id);
        dto.setId(id);
        this.modelMapper.map(dto,entity);
        return entityManager.merge(entity);
    }
}
