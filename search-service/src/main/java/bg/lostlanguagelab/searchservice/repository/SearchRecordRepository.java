package bg.lostlanguagelab.searchservice.repository;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchRecordRepository extends JpaRepository<SearchRecord, UUID> {
}

