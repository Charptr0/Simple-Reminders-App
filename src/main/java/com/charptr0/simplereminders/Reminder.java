package com.charptr0.simplereminders;

public class Reminder
{
    private final String NAME;
    private final String PRIORITY_LEVEL;

    public Reminder() {
        this.NAME = "Untitled Reminder";
        this.PRIORITY_LEVEL = "Medium";
    }

    public Reminder(String name, String priority_level)
    {
        this.NAME = name;
        this.PRIORITY_LEVEL = priority_level;
    }

    public String getName() {
        return NAME;
    }

    public String getPriorityLevel() {
        return PRIORITY_LEVEL;
    }

    @Override
    public String toString() {
        return this.NAME + "\n" + this.PRIORITY_LEVEL;
    }
}
