package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.WarningChapter;
import com.anhtuan.bookapp.repository.base.WarningChapterRepository;
import com.anhtuan.bookapp.service.base.WarningChapterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WarningChapterServiceImpl implements WarningChapterService {

    private WarningChapterRepository warningChapterRepository;

    @Override
    public void insert(WarningChapter warningChapter) {
        warningChapterRepository.insert(warningChapter);
    }

    @Override
    public void deleteByChapter(String chapter) {
        warningChapterRepository.deleteByChapter(chapter);
    }

    @Override
    public List<WarningChapter> findAll() {
        return warningChapterRepository.findAll();
    }

    @Override
    public WarningChapter findWarningChapter(String chapter) {
        return warningChapterRepository.findWarningChapterByChapter(chapter);
    }
}
