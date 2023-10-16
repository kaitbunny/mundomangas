package net.mundomangas.backend.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.mundomangas.backend.domain.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(),
				status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		if(body == null) {
			HttpStatus status = HttpStatus.resolve(statusCode.value());
			
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		}
		else if(body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(statusCode.value())
					.build();
		}
		
		return super.handleExceptionInternal(e, body, headers, statusCode, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
		
	}
	
}
