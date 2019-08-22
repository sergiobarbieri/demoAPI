package br.com.drummond.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.drummond.domain.Student; 



@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
