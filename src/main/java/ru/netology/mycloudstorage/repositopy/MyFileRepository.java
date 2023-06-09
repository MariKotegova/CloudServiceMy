package ru.netology.mycloudstorage.repositopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.mycloudstorage.modele.File;

import java.util.List;

@Repository
public interface MyFileRepository extends JpaRepository<File, Long> {
    File findByFilenameAndDeleted(String filename, int deleted);

    List<File> findAllByDeleted(int deleted);
}
