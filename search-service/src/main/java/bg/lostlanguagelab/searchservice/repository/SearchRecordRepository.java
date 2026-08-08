package bg.lostlanguagelab.searchservice.repository;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRecordRepository extends JpaRepository<SearchRecord, Long> {
}

