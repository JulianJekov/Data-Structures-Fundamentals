package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Loader implements Buffer {

    private List<Entity> entities;

    public Loader() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity entity = null;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                entity = entities.get(i);
                entities.remove(i);
                break;
            }
        }
        return entity;
    }

    @Override
    public Entity find(Entity entity) {
        int index = this.entities.indexOf(entity);

        if (index == -1 || this.entities.isEmpty()) {
            return null;
        }

        return this.entities.get(index);
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entities.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.entities.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        ensureEntity(oldEntity);
        int index = this.entities.indexOf(oldEntity);
        this.entities.set(index, newEntity);
    }



    @Override
    public void swap(Entity first, Entity second) {
        ensureEntity(first);
        ensureEntity(second);

        int firstIndex = this.entities.indexOf(first);
        int secondIndex = this.entities.indexOf(second);

        this.entities.set(firstIndex, second);
        this.entities.set(secondIndex, first);
    }

    @Override
    public void clear() {
        this.entities.clear();
    }

    @Override
    public Entity[] toArray() {
        return entities.toArray(new Entity[0]);
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return this.entities
                .stream()
                .filter(e -> e.getStatus().ordinal() >= lowerBound.ordinal() &&
                        e.getStatus().ordinal() <= upperBound.ordinal())
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> getAll() {
        return this.entities;
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        this.entities
                .stream()
                .filter(e -> e.getStatus().equals(oldStatus))
                .forEach(e -> e.setStatus(newStatus));
    }

    @Override
    public void removeSold() {
        this.entities = this.entities
                .stream()
                .filter(e -> !e.getStatus().equals(BaseEntity.Status.SOLD))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.entities.iterator();
    }

    private void ensureEntity(Entity oldEntity) {
        if (!this.contains(oldEntity)) {
            throw new IllegalStateException("Entity not found");
        }
    }
}
