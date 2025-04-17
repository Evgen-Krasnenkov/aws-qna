package org.kras.aws.awsqna.repo;

import org.kras.aws.awsqna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
