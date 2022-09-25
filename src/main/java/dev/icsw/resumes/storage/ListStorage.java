package dev.icsw.resumes.storage;

import dev.icsw.resumes.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Resume doGet(Integer itemLocation) {
        return list.get(itemLocation);
    }

    @Override
    public List<Resume> doGetAll() {
        return list;
    }

    @Override
    protected void doSave(Resume resume, Integer itemLocation) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Integer itemLocation) {
        list.set(itemLocation, resume);
    }

    @Override
    protected void doDelete(Integer itemLocation) {
        /*
         * The type casting to int is necessary here because the List<E> interface has two remove() methods:
         *  1) E remove(int index),
         *  2) boolean remove(Object o).
         * If manual casting is omitted the compiler chooses the remove(Object o) version,
         * which is not what we want here.
         * We could, however, avoid manual type casting here by using itemLocation.intValue().
         */
        list.remove((int) itemLocation);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected Integer getItemLocation(String itemId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(itemId)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isItemLocated(Integer itemLocation) {
        return itemLocation != null;
    }
}
