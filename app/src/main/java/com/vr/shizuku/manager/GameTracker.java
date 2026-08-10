package com.vr.shizuku.manager;

import java.util.ArrayList;
import java.util.List;

public class GameTracker {
    private List<String> trackedGames = new ArrayList<>();

    public void addGame(String packageName) {
        if (!trackedGames.contains(packageName)) {
            trackedGames.add(packageName);
        }
    }

    public void removeGame(String packageName) {
        trackedGames.remove(packageName);
    }

    public List<String> getTrackedGames() {
        return trackedGames;
    }
}
