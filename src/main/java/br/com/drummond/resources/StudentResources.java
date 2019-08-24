package br.com.drummond.resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.drummond.domain.Student;
import br.com.drummond.repository.StudentRepository;

//@CrossOrigin
@RestController
public class StudentResources {
	
	@Autowired
	StudentRepository studentRepository;
	
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public String test() {

		return "Sistema Drummond API ativo - " + new SimpleDateFormat("yyyyMM-dd HH:mm:ss").format(new Date());

	}
	
	/**
	 * Retorna todos o estudantes cadastrados
	 * @return
	 */
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> obterAlunos() {
		
		List<Student> students; // Classe List -> define um array de objetos da classe Student
		
		students = studentRepository.findAll();
		if (students.isEmpty())
			return new ResponseEntity<List<Student>>(students, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}
	
	/**
	 * Obter Aluno pelo ra (chave primaria - PK)
	 * 
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = "/students/{ra}", method = RequestMethod.GET)
	public ResponseEntity<?> obterAlunoRa(@PathVariable Integer ra) {
		
		Optional<Student> student; // Optional: container objeto.
		
		student = studentRepository.findById(ra);
		
		if (student.isPresent())
			return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Student>(student.get(), HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Inserir um novo aluno
	 * @param json student
	 * @return
	 */
	@RequestMapping(value = "/students", method = RequestMethod.POST)
	public ResponseEntity<Student> inserirAluno(@RequestBody Student studentPost) {
		
		Optional<Student> studentRepo; 
		
		studentRepo = studentRepository.findById(studentPost.getRa());
		
		if (studentRepo.isPresent())
			return new ResponseEntity<Student>(studentRepo.get(), HttpStatus.CONFLICT);
						
		studentRepository.save(studentPost);
		
		return new ResponseEntity<Student>(studentPost, HttpStatus.CREATED);
	}
	
	/**
	 * Alterar um aluno existente
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Student> updateAlunoRa(@RequestBody Student studentPost, @PathVariable Integer id) {
		
		Optional<Student> studentRepo; 
		
		studentRepo = studentRepository.findById(id);
		
		if (!studentRepo.isPresent())
			return new ResponseEntity<Student>(studentPost, HttpStatus.NO_CONTENT);
		
		if (studentPost.getName() != null)
			studentRepo.get().setName(studentPost.getName());
		if (studentPost.getEmail() != null)
			studentRepo.get().setEmail(studentPost.getEmail());
						
		studentRepository.save(studentRepo.get());
		
		return new ResponseEntity<Student>(studentRepo.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteAlunoRa(@PathVariable Integer id) {
		
		Optional<Student> studentRepo; 
		
		studentRepo = studentRepository.findById(id);
		
		if (!studentRepo.isPresent())
			return new ResponseEntity<Student>(studentRepo.get(), HttpStatus.NO_CONTENT);
	
		studentRepository.deleteById(id);
		
		return new ResponseEntity<Student>(studentRepo.get(), HttpStatus.OK);
	}
}