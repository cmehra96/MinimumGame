package com.example.chetan.minimumgame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.*;

public class PlayerList implements List<Player> {

    List<Player> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }


    @Override
    public Iterator<Player> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Player player) {

        list.add(player);
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Player> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Player> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Player get(int index) {
        return list.get(index);
    }

    @Override
    public Player set(int index, Player element) {
        return null;
    }

    @Override
    public void add(int index, Player element) {

    }

    @Override
    public Player remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }


    @Override
    public ListIterator<Player> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Player> listIterator(int index) {
        return null;
    }

    @Override
    public List<Player> subList(int fromIndex, int toIndex) {
        return null;
    }

    public void reset(DiscardedDeck discardedDeck) {
        for (Player player : list) {
            player.reset(discardedDeck);
        }
    }
}
