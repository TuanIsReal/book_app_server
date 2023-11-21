package com.anhtuan.bookapp.worker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkerManager {
    private final ChapterContainer chapterContainer;

    public void startWorkers() {
        chapterContainer.startRunners();
    }
}
