package br.com.simulacao.exception.advice;

import br.com.simulacao.dto.MessageDTO;
import br.com.simulacao.exception.SimulacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SimulacaoAdvice {

    @ResponseBody
    @ExceptionHandler(SimulacaoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MessageDTO simulacaoHandler(SimulacaoException e) {

        return new MessageDTO(e.toString());
    }
}
