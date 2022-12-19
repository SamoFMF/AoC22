package day19.models;

public record Resources(
    int ore,
    int clay,
    int obsidian,
    int geode
) {
    public Resources(int[] robot) {
        this(
            robot[0],
            robot[1],
            robot[2],
            robot[3]
        );
    }

    public boolean dominates(Resources resources) {
        return ore >= resources.ore
            && clay >= resources.clay
            && obsidian >= resources.obsidian
            && geode >= resources.geode;
    }
}
