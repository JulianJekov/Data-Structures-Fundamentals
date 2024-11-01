package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class Data implements Repository {

    private Entity root;
    private Queue<Entity> entities;

    public Data() {
        this.entities = new PriorityQueue<>();
        this.root = null;
    }

    public Data(Data other) {
        this.root = other.root;
        this.entities = other.entities;

    }

    @Override
    public void add(Entity entity) {
        if (this.root == null) {
            this.root = entity;
        } else {
            this.getById(entity.getParentId()).getChildren().add(entity);
        }
        this.entities.offer(entity);
    }

    @Override
    public Entity getById(int id) {
        if (this.root == null) {
            return null;
        }
        Deque<Entity> queue = new ArrayDeque<>();
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            Entity entity = queue.poll();
            if (entity.getId() == id) {
                return entity;
            }
            for (Entity child : entity.getChildren()) {
                queue.offer(child);
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        Entity parent = getById(id);
        return parent == null ? new ArrayList<>() : parent.getChildren();
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        if (!type.equals("Invoice") && !type.equals("StoreClient") && !type.equals("User")) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }
        return this.entities
                .stream()
                .filter(e -> e.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Entity peekMostRecent() {
        ensureNonEmpty();
        return this.entities.peek();
    }


    @Override
    public Entity pollMostRecent() {
        ensureNonEmpty();
        return this.entities.poll();
    }

    @Override
    public int size() {
        return this.entities.size();
    }

    private void ensureNonEmpty() {
        if (this.entities.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
    }
}
