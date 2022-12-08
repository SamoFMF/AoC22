package day07;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private final Directory parent;
    private final Map<String, Directory> children;
    private final Map<String, File> files;

    private long size = -1;

    public Directory(Directory parent) {
        this.parent = parent;

        children = new HashMap<>();
        files = new HashMap<>();
    }

    public Directory getParent() {
        return parent;
    }

    public Directory getChild(String name) {
        return children.get(name);
    }

    public void addChild(String name) {
        if (children.containsKey(name)) {
            return;
        }

        var dir = new Directory(this);
        children.put(name, dir);
    }

    public void addFile(String name, long size) {
        if (files.containsKey(name)) {
            return;
        }

        var file = new File(name, size);
        files.put(name, file);
    }

    public long getDirSize() {
        if (size < 0) {
            var sumFiles = files
                .values()
                .stream()
                .mapToLong(File::size)
                .sum();

            var sumDirs = children
                .values()
                .stream()
                .mapToLong(Directory::getDirSize)
                .sum();

            size = sumFiles + sumDirs;
        }

        return size;
    }

    public long getSumOfSmallDirs() {
        var sum = children
            .values()
            .stream()
            .mapToLong(Directory::getSumOfSmallDirs)
            .sum();

        if (size <= 100000) {
            sum += size;
        }

        return sum;
    }

    public long findSmallestOverSize(long size) {
        return findSmallestOverSize(size, this.size);
    }

    private long findSmallestOverSize(long size, long best) {
        if (this.size < size) {
            return best;
        }

        if (this.size < best) {
            best = this.size;
        }

        for (var child : children.values()) {
            best = child.findSmallestOverSize(size, best);
        }

        return best;
    }
}
